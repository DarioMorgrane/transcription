package dariomorgrane.transcription.model;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CorrectedDocxImplementation extends XWPFDocument implements CorrectedDocx {

    private final RolesInformation rolesInformation;
    private String currentSpeakerDesignation;
    private String fileName;

    private final XWPFDocument sourceFileInDocx;
    private final StylingInformation stylingInformation;

    public CorrectedDocxImplementation(byte[] sourceFileBytes,
                                       String originalFileName,
                                       RolesInformation rolesInformation) throws IOException {
        super();
        this.rolesInformation = rolesInformation;
        defineCurrentSpeakerDesignation();
        sourceFileInDocx = new XWPFDocument(new ByteArrayInputStream(sourceFileBytes));
        stylingInformation = new StylingInformation(sourceFileInDocx);
        fileName = originalFileName;
        writeDownContent();
        defineFilename();
    }

    public RolesInformation getRolesInformation() {
        return rolesInformation;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void defineCurrentSpeakerDesignation() {
        currentSpeakerDesignation = rolesInformation.getFirstSpeaker().equals("interviewer")
                ? rolesInformation.getInterviewerDesignation()
                : rolesInformation.getRespondentDesignation();
    }

    private void writeDownContent() {
        List<XWPFParagraph> sourceDocxParagraphs = sourceFileInDocx.getParagraphs();
        XWPFParagraph correctedParagraph;
        XWPFRun runForSpeakerDesignation;
        XWPFRun runForParagraphText;

        for (XWPFParagraph sourceParagraph : sourceDocxParagraphs) {
            correctedParagraph = this.createParagraph();
            setupStyling(correctedParagraph);

            runForSpeakerDesignation = correctedParagraph.createRun();
            setupStyling(runForSpeakerDesignation);
            runForSpeakerDesignation.setBold(rolesInformation.getSpeakersDesignationsIsBold());
            runForSpeakerDesignation.setText(currentSpeakerDesignation);

            runForParagraphText = correctedParagraph.createRun();
            setupStyling(runForParagraphText);
            runForParagraphText.setText(sourceParagraph.getText());

            changeSpeaker();
        }
    }

    private void defineFilename() throws UnsupportedEncodingException {
        String unencodedFileName = fileName;
        String URLEncodedFileName = URLEncoder.encode(unencodedFileName, "UTF-8");
        String resultFileName = URLEncodedFileName.replace('+', ' ');
        if (unencodedFileName.contains("+")) {
            resultFileName = deleteExtraSpaces(resultFileName, getIndexesOfPlusSign(resultFileName));
        }
        fileName = resultFileName;
    }

    private List<Integer> getIndexesOfPlusSign(String string) {
        List<Integer> result = new ArrayList<>();
        char[] chars = string.toCharArray();
        for (int count = 0; count <= chars.length - 1; count++) {
            if (chars[count] == '+') {
                result.add(count);
            }
        }
        return result;
    }

    private String deleteExtraSpaces(String original, List<Integer> indexesOfPlusSign) {
        char[] chars = original.toCharArray();
        for (int index : indexesOfPlusSign) {
            chars[index] = '+';
        }
        return new String(chars);
    }

    private void changeSpeaker() {
        currentSpeakerDesignation = currentSpeakerDesignation.equals(rolesInformation.getInterviewerDesignation())
                ? rolesInformation.getRespondentDesignation()
                : rolesInformation.getInterviewerDesignation();
    }

    private void setupStyling(XWPFRun run) {
        run.setFontFamily(stylingInformation.getFontFamily());
        run.setFontSize(stylingInformation.getFontSize());
    }

    private void setupStyling(XWPFParagraph paragraph) {
        paragraph.setAlignment(stylingInformation.getParagraphAlignment());
        paragraph.setIndentationLeft(stylingInformation.getIndentationLeft());
        paragraph.setIndentationRight(stylingInformation.getIndentationRight());
        paragraph.setIndentationFirstLine(stylingInformation.getIndentationFirstLine());
        paragraph.setSpacingAfter(stylingInformation.getSpacingAfter());
        paragraph.setSpacingBefore(stylingInformation.getSpacingBefore());
        paragraph.setSpacingLineRule(stylingInformation.getLineSpacingRule());
    }

}
