package dariomorgrane.transcription.models;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CorrectedDocx extends XWPFDocument {

    private MultipartFile sourceFile;
    private String interviewerDesignation = "Интервьюер: ";
    private String respondentDesignation = "Респондент: ";
    private String firstSpeaker;
    private Boolean speakersDesignationsIsBold;

    private String currentSpeakerDesignation;
    private String fileName;

    private XWPFDocument sourceFileInDocx;
    private StylingInformation stylingInformation;


    public CorrectedDocx() {
        super();
    }

    public MultipartFile getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(MultipartFile sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getInterviewerDesignation() {
        return interviewerDesignation;
    }

    public void setInterviewerDesignation(String interviewerDesignation) {
        this.interviewerDesignation = interviewerDesignation;
    }

    public String getRespondentDesignation() {
        return respondentDesignation;
    }

    public void setRespondentDesignation(String respondentDesignation) {
        this.respondentDesignation = respondentDesignation;
    }

    public String getFirstSpeaker() {
        return firstSpeaker;
    }

    public void setFirstSpeaker(String firstSpeaker) {
        this.firstSpeaker = firstSpeaker;
    }

    public String getSpeakersDesignationsIsBold() {
        return String.valueOf(speakersDesignationsIsBold);
    }

    public void setSpeakersDesignationsIsBold(String speakersDesignationsIsBold) {
        this.speakersDesignationsIsBold = Boolean.valueOf(speakersDesignationsIsBold);
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void setupContent() throws IOException {

        currentSpeakerDesignation = firstSpeaker.equals("interviewer")
                ? interviewerDesignation
                : respondentDesignation;

        sourceFileInDocx = new XWPFDocument(sourceFile.getInputStream());
        stylingInformation = new StylingInformation();
        List<XWPFParagraph> sourceDocxParagraphs = sourceFileInDocx.getParagraphs();
        XWPFParagraph correctedParagraph;
        XWPFRun runForSpeakerDesignation;
        XWPFRun runForParagraphText;

        for (XWPFParagraph sourceParagraph : sourceDocxParagraphs) {
            correctedParagraph = this.createParagraph();
            setupStyling(correctedParagraph);

            runForSpeakerDesignation = correctedParagraph.createRun();
            setupStyling(runForSpeakerDesignation);
            runForSpeakerDesignation.setBold(speakersDesignationsIsBold);
            runForSpeakerDesignation.setText(currentSpeakerDesignation);

            runForParagraphText = correctedParagraph.createRun();
            setupStyling(runForParagraphText);
            runForParagraphText.setText(sourceParagraph.getText());

            changeSpeaker();
        }

        defineFilename();
    }

    private void defineFilename() throws UnsupportedEncodingException {
        String unencodedFileName = sourceFile.getOriginalFilename();
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
        currentSpeakerDesignation = currentSpeakerDesignation.equals(interviewerDesignation)
                ? respondentDesignation
                : interviewerDesignation;
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

    private class StylingInformation {

        private final XWPFParagraph exampleParagraph;
        private final XWPFRun exampleRun;

        private final String fontFamily;
        private final int fontSize;
        private final ParagraphAlignment paragraphAlignment;
        private final int indentationLeft;
        private final int indentationRight;
        private final int indentationFirstLine;
        private final int spacingAfter;
        private final int spacingBefore;
        private final LineSpacingRule lineSpacingRule;

        StylingInformation() {
            exampleParagraph = sourceFileInDocx
                    .getParagraphs()
                    .get(0);
            exampleRun = exampleParagraph
                    .getRuns()
                    .get(0);
            fontFamily = exampleRun.getFontFamily();
            fontSize = exampleRun.getFontSize();
            paragraphAlignment = exampleParagraph.getAlignment();
            indentationLeft = exampleParagraph.getIndentationLeft();
            indentationRight = exampleParagraph.getIndentationRight();
            indentationFirstLine = exampleParagraph.getIndentationFirstLine();
            spacingAfter = exampleParagraph.getSpacingAfter();
            spacingBefore = exampleParagraph.getSpacingBefore();
            lineSpacingRule = exampleParagraph.getSpacingLineRule();
            stylingInformation = this;
        }

        public String getFontFamily() {
            return fontFamily;
        }

        public int getFontSize() {
            return fontSize;
        }

        public ParagraphAlignment getParagraphAlignment() {
            return paragraphAlignment;
        }

        public int getIndentationLeft() {
            return indentationLeft;
        }

        public int getIndentationRight() {
            return indentationRight;
        }

        public int getIndentationFirstLine() {
            return indentationFirstLine;
        }

        public int getSpacingAfter() {
            return spacingAfter;
        }

        public int getSpacingBefore() {
            return spacingBefore;
        }

        public LineSpacingRule getLineSpacingRule() {
            return lineSpacingRule;
        }
    }

}
