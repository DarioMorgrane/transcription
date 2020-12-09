package dariomorgrane.transcription;

import dariomorgrane.transcription.model.CorrectedDocxImplementation;
import dariomorgrane.transcription.model.RolesInformation;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CorrectedDocxTest {

    private CorrectedDocxImplementation correctedDocx;
    private static RolesInformation rolesInformation;
    private XWPFDocument sourceDocx;
    private XWPFDocument expectedDocx;

    @BeforeAll
    static void setUpNewCorrectedDocx() {
        rolesInformation = new RolesInformation();
        rolesInformation.setInterviewerDesignation("Интервьюер: ");
        rolesInformation.setRespondentDesignation("Респондент: ");
        rolesInformation.setSpeakersDesignationsIsBold("true");
    }

    @Test
    void correctedDocxTextShouldMatchExample() throws IOException {
        sourceDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/source.docx")));
        expectedDocx = new XWPFDocument(new FileInputStream(new File("src/test/resources/expectedResult.docx")));

        String originalFileName = "firstSource.docx";
        ByteArrayOutputStream sourceDocxOutputStream = new ByteArrayOutputStream();
        sourceDocx.write(sourceDocxOutputStream);
        byte[] sourceDocxBytes = sourceDocxOutputStream.toByteArray();
        rolesInformation.setFirstSpeaker("respondent");
        correctedDocx = new CorrectedDocxImplementation(sourceDocxBytes, originalFileName, rolesInformation);

        Assertions.assertEquals(new XWPFWordExtractor(expectedDocx).getText(), new XWPFWordExtractor(correctedDocx).getText());
    }

    @AfterEach
    public void closeDocx() throws IOException {
        sourceDocx.close();
        expectedDocx.close();
        correctedDocx.close();
    }

}
