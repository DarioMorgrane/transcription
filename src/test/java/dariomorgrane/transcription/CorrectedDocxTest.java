package dariomorgrane.transcription;

import dariomorgrane.transcription.models.CorrectedDocx;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CorrectedDocxTest {

    private final static String contentType = "application/octet-stream";
    private CorrectedDocx correctedDocx;
    private XWPFDocument sourceDocx;
    private XWPFDocument expectedDocx;

    @BeforeEach
    public void setUpNewCorrectedDocx() {
        correctedDocx = new CorrectedDocx();
        correctedDocx.setInterviewerDesignation("Интервьюер: ");
        correctedDocx.setRespondentDesignation("Респондент: ");
        correctedDocx.setSpeakersDesignationsIsBold("true");
    }


    @Test
    public void firstCorrectedDocxTextShouldMatchExample() throws IOException {
        sourceDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/firstSource.docx")));
        expectedDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/firstExpectedResult.docx")));

        String name = "firstSource.docx";
        String originalFileName = "firstSource.docx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sourceDocx.write(outputStream);
        byte[] content = outputStream.toByteArray();
        MultipartFile sourceFile = new MockMultipartFile(name,
                originalFileName, contentType, content);


        correctedDocx.setSourceFile(sourceFile);
        correctedDocx.setFirstSpeaker("respondent");
        correctedDocx.setupContent();

        Assertions.assertEquals(new XWPFWordExtractor(expectedDocx).getText(), new XWPFWordExtractor(correctedDocx).getText());
    }

    @Test
    public void secondCorrectedDocxTextShouldMatchExample() throws IOException {
        sourceDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/secondSource.docx")));
        expectedDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/secondExpectedResult.docx")));

        String name = "secondSource.docx";
        String originalFileName = "secondSource.docx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        sourceDocx.write(outputStream);
        byte[] content = outputStream.toByteArray();
        MultipartFile sourceFile = new MockMultipartFile(name,
                originalFileName, contentType, content);


        correctedDocx.setSourceFile(sourceFile);
        correctedDocx.setFirstSpeaker("interviewer");
        correctedDocx.setupContent();

        Assertions.assertEquals(new XWPFWordExtractor(expectedDocx).getText(), new XWPFWordExtractor(correctedDocx).getText());
    }

    @AfterEach
    public void closeDocx() throws IOException {
        sourceDocx.close();
        expectedDocx.close();
        correctedDocx.close();
    }

}
