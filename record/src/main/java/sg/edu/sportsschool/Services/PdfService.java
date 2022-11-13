package sg.edu.sportsschool.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import sg.edu.sportsschool.Exceptions.InternalServerException;

@Service
public class PdfService {

    public ByteArrayOutputStream generateCorpLetter(String address, String ballotDate, String attrName, byte[] barcodeImage,
            String expiryDate, String membershipId, String staffName, String visitDate, String benefits,
            String termsConditions, String membershipDescription) {
        Document document = new Document();

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, os);
            document.open();
            document.setMargins(2, 2, 2, 2);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // add company letterhead (address)
            Paragraph paragraph = new Paragraph(address, font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add date of ballot
            paragraph = new Paragraph("Date: " + ballotDate, font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add header
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            paragraph = new Paragraph("CORPORATE LETTER \n " + attrName.toUpperCase(), bold);
            document.add(paragraph);

            // add barcodeImage
            Image barcode = Image.getInstance(barcodeImage);
            barcode.scaleAbsolute(300, 100);
            barcode.setAlignment(Image.ALIGN_RIGHT);
            document.add(barcode);

            // add membership id
            paragraph = new Paragraph(String.format("""
                    This is to certify that the following employee is authorised to visit %s under
                    your Company's Corporate Membership number %s
                    """, attrName, membershipId), font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add staff name
            paragraph = new Paragraph(String.format("Employee: %s", staffName), bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add visit date
            paragraph = new Paragraph(String.format("Date of visit: %s", visitDate), bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add benefits
            paragraph = new Paragraph(benefits, font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add terms conditions
            paragraph = new Paragraph("Terms and Conditions", bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);
            paragraph = new Paragraph(termsConditions, font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // add membership description
            paragraph = new Paragraph("Best regards,", font);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);
            paragraph = new Paragraph("Manager, Human Resource,", font);
            document.add(paragraph);
            paragraph = new Paragraph(membershipDescription, font);
            document.add(paragraph);

            document.close();

            return os;

        } catch (IOException e) {
            throw new InternalServerException("Server unable to deserialise barcode image from database");
        } catch (DocumentException e) {
            throw new InternalServerException("Server unable to create corporate letter pdf document");
        }

    }

    public ByteArrayOutputStream generateAuthLetter(String address, String ballotDate, String membershipDescription, String visitDate, String staffName, String logoFilePath) {
        Document document = new Document();

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, os);
            document.open();
            document.setMargins(2, 2, 2, 2);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Add logo
            Image img = Image.getInstance(logoFilePath);
            img.scaleAbsolute(100, 100);
            img.setAlignment(Image.ALIGN_CENTER);
            document.add(img);
            
            // write date
            Paragraph paragraph = new Paragraph(ballotDate, font);
            paragraph.setSpacingAfter(40);
            document.add(paragraph);

            // write address
            paragraph = new Paragraph(address, font);
            paragraph.setSpacingAfter(40);
            document.add(paragraph);
            
            paragraph = new Paragraph("Dear Sir/Madam \n\n", font);
            document.add(paragraph);
            
            // write membershipDescription
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            paragraph = new Paragraph("AUTHORISATION LETTER - \n" + membershipDescription + "\n", bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // body text
            paragraph = new Paragraph(String.format("""
                    Singapore Sports School hereby authorise our employee identified below, to utilise our %s on the date as indicated.
                    """, membershipDescription), font);
            paragraph.setSpacingAfter(30);
            document.add(paragraph);

            // write visitDate
            paragraph = new Paragraph(String.format("Date of visit: %s", visitDate), bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            // write staff name
            paragraph = new Paragraph(String.format("Name of Employee: %s", staffName), bold);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            paragraph = new Paragraph("Thank you.");
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            paragraph = new Paragraph("Human Resource Department");
            document.add(paragraph);
            paragraph = new Paragraph("(This is a system generated letter.)");
            document.add(paragraph);

            document.close();

            return os;

        } catch (DocumentException e) {
            throw new InternalServerException(e.getMessage());
        } catch (IOException e) {
            throw new InternalServerException("Logo not found.");
        }

    }

}
