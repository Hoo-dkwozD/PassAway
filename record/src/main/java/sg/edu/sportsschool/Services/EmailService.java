package sg.edu.sportsschool.Services;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.LoanRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PdfService pdfService;

    @Async
    public void sendConfirmationMail(String emailTo, String staffName, String attrName, String visitDate, String passId,
            String address, String membershipDescription, char passType) throws MessagingException {
        Context context = new Context();
        context.setVariable("staffName", staffName);
        context.setVariable("attrName", attrName);
        context.setVariable("visitDate", visitDate);
        context.setVariable("passId", passId);
        context.setVariable("passType", passType);
        String process = templateEngine.process("EmailTemplate.html", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Booking Confirmation for " + attrName + " on " + visitDate);
        helper.setText(process, true);
        helper.setTo(emailTo);

        // Generate the auth letter
        pdfService.generateAuthLetter(address, membershipDescription, visitDate, staffName, passId, passType);
        System.out.println("Auth letter generated");

        // Look for the auth letter pdf
        String storageFolder = "src/main/static/";
        String fileName = "AuthLetter.pdf";
        File authLetter = getAuthLetter(storageFolder, fileName);
        if (authLetter == null) {
            throw new InternalServerException(
                    "Server unable to get authentication letter at: " + storageFolder + fileName);
        }

        // Attach the auth letter pdf
        helper.addAttachment(authLetter.getName(), authLetter);
        System.out.println("auth letter attached");

        javaMailSender.send(mimeMessage);
        System.out.println("Email sent.");

    }

    @Async
    public void sendCollectionEmail(String emailTo, String staffName, String attrName) throws MessagingException {
        Context context = new Context();
        context.setVariable("staffName", staffName);
        context.setVariable("attrName", attrName);
        String process = templateEngine.process("PassCollectedEmailTemplate.html", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
        helper.setSubject("Pass Collected for " + attrName);
        helper.setText(process, true);
        helper.setTo(emailTo);

        javaMailSender.send(mimeMessage);
        System.out.println("Collection notification email sent.");
    }

    @Async
    // @Scheduled(fixedRate = 8000) // uncomment to test (cron = "0 0 9 * * *") 9am everyday
    public void sendCollectionReminderEmail() {
        // Send reminder email for loans whose visit date is tomorrow
        Date reminderDate = new Date(System.currentTimeMillis());
        reminderDate = Date.valueOf(reminderDate.toLocalDate().plusDays(1));
        List<Loan> reminderLoans = loanRepository.getReminderDateLoans(reminderDate);

        for (Loan loan : reminderLoans) {
            Context context = new Context();
            context.setVariable("staffName", loan.getStaff().getFirstName());
            context.setVariable("attrName", loan.getPass().getAttraction().getName());
            context.setVariable("visitDate", loan.getStartDate().toString());
            String process = templateEngine.process("PassCollectReminderEmailTemplate.html", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
                helper.setSubject("Reminder to collect pass(es)");
                helper.setText(process, true);
                helper.setTo(loan.getStaff().getEmail());

                javaMailSender.send(mimeMessage);
                System.out.println("Collection reminder email sent." + loan.getStaff().getEmail());

            } catch (MessagingException e) {
                // TODO Log the exception here
                System.out.println(
                        "Server unable to send reminder email for pass collection to: " + loan.getStaff().getEmail());
                continue;
            }
        }
    }

    @Async
    // @Scheduled(fixedRate = 8000) // uncomment to test (cron = "0 0 9 * * *") 9am everyday
    public void sendLoanOverdueReminderEmail() {
        // Send reminder email for loans whose visit date is tomorrow
        Date overdueDate = new Date(System.currentTimeMillis());
        List<Loan> overdueLoans = loanRepository.getOverdueLoans(overdueDate);

        for (Loan loan : overdueLoans) {
            Context context = new Context();
            context.setVariable("staffName", loan.getStaff().getFirstName());
            context.setVariable("attrName", loan.getPass().getAttraction().getName());
            context.setVariable("visitDate", loan.getStartDate().toString());
            String process = templateEngine.process("OverdueLoansReminderEmailTemplate.html", context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
                helper.setSubject("Overdue passes - Reminder to return pass(es)");
                helper.setText(process, true);
                helper.setTo(loan.getStaff().getEmail());

                javaMailSender.send(mimeMessage);
                System.out.println("Overdue loan email sent to: " + loan.getStaff().getEmail());

            } catch (MessagingException e) {
                // TODO Log the exception here
                System.out.println(
                        "Server unable to send overdue loan email to: " + loan.getStaff().getEmail());
                continue;
            }
        }
    }


    public File getAuthLetter(String storageFolder, String fileName) {
        File f = new File(storageFolder + fileName);
        if (f.exists()) {
            return f;
        } else {
            return null;
        }

    }
}
 