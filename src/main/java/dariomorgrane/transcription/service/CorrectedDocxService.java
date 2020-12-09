package dariomorgrane.transcription.service;

import dariomorgrane.transcription.model.CorrectedDocx;
import dariomorgrane.transcription.model.RolesInformation;

import java.io.IOException;

public interface CorrectedDocxService {

    CorrectedDocx generateCorrectedDocx(byte[] sourceFileBytes,
                                        String originalFileName,
                                        RolesInformation rolesInformation) throws IOException;

}
