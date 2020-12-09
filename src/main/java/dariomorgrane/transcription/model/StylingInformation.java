package dariomorgrane.transcription.model;

import org.apache.poi.xwpf.usermodel.*;

public class StylingInformation {

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

    StylingInformation(XWPFDocument sourceFile) {
        exampleParagraph = sourceFile
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
