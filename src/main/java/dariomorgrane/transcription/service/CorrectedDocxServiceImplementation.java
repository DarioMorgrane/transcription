package dariomorgrane.transcription.service;

import dariomorgrane.transcription.model.CorrectedDocx;
import dariomorgrane.transcription.model.CorrectedDocxImplementation;
import dariomorgrane.transcription.model.RolesInformation;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CorrectedDocxServiceImplementation implements CorrectedDocxService {

    @Override
    public CorrectedDocx generateCorrectedDocx(byte[] sourceFileBytes,
                                               String originalFileName,
                                               RolesInformation rolesInformation) throws IOException {
        return new CorrectedDocxImplementation(sourceFileBytes, originalFileName, rolesInformation);
    }

}
