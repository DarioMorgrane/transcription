package dariomorgrane.transcription.model;

import java.io.Closeable;

public interface CorrectedDocx extends Closeable {

    byte[] getBytes() throws Exception;

    String getFileName();

}
