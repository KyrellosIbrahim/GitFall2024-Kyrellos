import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DeckReport {
    static DeckReader reader = new DeckReader();
    /**
     * generates a report for the deck
     * will call other methods depending on if deck is valid or not
     * @param filename the name of the file to be written to
     * @throws IOException if error occurs while writing
     */
    public static void generateReport(String filename) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        contentStream.newLineAtOffset(margin, yStart);

        String outputFileName;
        try {
            Deck deck = reader.readDeck(filename);
            outputFileName = "SpireDeck_" + deck.getDeckId() + ".pdf";
            validReport(deck, document, contentStream, reader.getInvalidCardsList());
        }
        catch (InvalidDeckException e) {
            outputFileName = "SpireDeck_" + e.getDeckId() + "(VOID).pdf";
            generateVoidReport(contentStream);
        }
        contentStream.endText();
        contentStream.close();

        document.save(outputFileName);
        document.close();

    }

    /**
     * Generates a valid report for the deck
     * @param deck the deck to be validated
     * @param document the document to be written to
     * @param contentStream for the pdf to be written into
     * @throws IOException if error occurs while writing
     */
    private static void validReport(Deck deck, PDDocument document, PDPageContentStream contentStream, ArrayList<String> invalidCardList) throws IOException {
        contentStream.showText("Deck Report");
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

        float leading = 15;
        contentStream.newLineAtOffset(0, -leading * 2);

        String[] content = {
                "Deck ID: " + deck.getDeckId(),
                "Total Energy Cost: " + deck.getTotalEnergyCost(),
        };

        for (String text : content) {
            contentStream.showText(text);
            contentStream.newLineAtOffset(0, -leading);
        }

        contentStream.showText("Invalid Cards:");
        contentStream.newLineAtOffset(0, -leading);
        for(String invalidCard : invalidCardList) {
            contentStream.showText(invalidCard);
            contentStream.newLineAtOffset(0, -leading);
        }

        // Add histogram
        JFreeChart chart = DeckReader.generateHistogram(deck.getCostCount());
        BufferedImage chartImage = chart.createBufferedImage(400, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(baos, chartImage);
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "chart");

        contentStream.endText();
        contentStream.drawImage(pdImage, 50, 50, 400, 300);
        contentStream.beginText();
    }

    /**
     * Generates a void report for the deck
     * @param contentStream for the pdf to be written into
     * @throws IOException if error occurs while writing
     */
    private static void generateVoidReport(PDPageContentStream contentStream) throws IOException {
        contentStream.showText("VOID");
    }


    public static void main(String[] args) {
        try {
            generateReport("deck1.txt"); // can change to deck2 to test invalid file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

