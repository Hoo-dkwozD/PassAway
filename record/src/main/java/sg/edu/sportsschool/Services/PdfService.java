package sg.edu.sportsschool.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
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

    @Autowired
    private PassService pService;

    public void generateAuthLetter(String address, String membershipDescription, String visitDate, String staffName,
            String passId, char passType) {
        Document document = new Document();
        String storageFolder = "src/main/static/";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(storageFolder + "AuthLetter.pdf"), false));
            document.open();
            document.setMargins(2.54F, 2.54F, 2.54F, 2.54F);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 14);

            // Add barcode image for digital passes
            if (passType == 'd') {
                byte[] imgb = getBarcodeImage(passId);
                if (imgb == null) {
                    throw new InternalServerException("No barcode created yet for pass id: " + passId);
                }
                Image barcode = Image.getInstance(imgb);
                barcode.scaleAbsolute(400, 100);
                barcode.setAlignment(Image.ALIGN_CENTER);
                document.add(barcode);
            }

            // add text details
            Chunk chunk = new Chunk("Date:" + visitDate + "\n", font);
            document.add(chunk);

            Paragraph paragraph = new Paragraph(address + "\n");
            paragraph.setSpacingAfter(40);
            document.add(paragraph);

            paragraph = new Paragraph("Dear Sir/Madam");
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            chunk = new Chunk("AUTHORISATION LETTER - \n" + membershipDescription + "\n", bold);
            document.add(chunk);

            paragraph = new Paragraph(
                    "Singapore Sports School hereby authorise our employee identified below, to utilise our "
                            + membershipDescription + "on the date as indicated.",
                    font);

            paragraph.setSpacingAfter(30);
            document.add(paragraph);

            paragraph = new Paragraph("Date of visit:" + visitDate, bold);
            paragraph.setSpacingAfter(30);
            document.add(paragraph);

            paragraph = new Paragraph("Name of Employee:" + staffName, bold);
            paragraph.setSpacingAfter(30);
            document.add(paragraph);

            paragraph = new Paragraph("Thank you.");
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            paragraph = new Paragraph("Human Resource Department");
            document.add(paragraph);
            paragraph = new Paragraph("(This is a system generated letter.)");
            document.add(paragraph);

            // Add logo
            Image img = Image.getInstance("src/main/static/sportsSchLogo.jpg");
            img.scaleAbsolute(100, 100);
            img.setAlignment(Image.ALIGN_RIGHT);
            document.add(img);

            document.close();

        } catch (DocumentException e) {
            throw new InternalServerException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new InternalServerException(e.getMessage());
        } catch (IOException e) {
            throw new InternalServerException(
                    "Exception getting barcode. Barcode image may not be present for corporate pass: "
                            + membershipDescription + " " + e.getMessage());
        }

    }

    public byte[] getBarcodeImage(String passId) {
        return pService.returnBarcodeImage(passId);
    }

}
