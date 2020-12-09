package dariomorgrane.transcription.web;

import dariomorgrane.transcription.model.CorrectedDocx;
import dariomorgrane.transcription.model.RolesInformation;
import dariomorgrane.transcription.service.CorrectedDocxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/")
public class CorrectedDocxController {

    private final CorrectedDocxService service;

    @Autowired
    public CorrectedDocxController(CorrectedDocxService service) {
        this.service = service;
    }

    @GetMapping
    public String returnMainPage(@ModelAttribute("rolesInformation") RolesInformation rolesInformation) {
        return "main";
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Resource> handleFileUploading(@RequestParam("sourceFile") MultipartFile sourceFile,
                                                        @ModelAttribute("rolesInformation") RolesInformation rolesInformation) throws Exception {
        CorrectedDocx correctedDocx = service.generateCorrectedDocx(sourceFile.getBytes(), sourceFile.getOriginalFilename(), rolesInformation);
        InputStreamResource correctedDocxResource = new InputStreamResource(new ByteArrayInputStream(correctedDocx.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + correctedDocx.getFileName() + "\"");
        int contentLength = correctedDocx.getBytes().length;
        correctedDocx.close();
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(correctedDocxResource);
    }

}
