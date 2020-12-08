package dariomorgrane.transcription.controllers;

import dariomorgrane.transcription.models.CorrectedDocx;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainPage(@ModelAttribute("correctedDocx") CorrectedDocx correctedDocx) {
        return "main";
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Resource> handleFileUploading(@ModelAttribute("correctedDocx") CorrectedDocx correctedDocx) throws IOException {
        correctedDocx.setupContent();
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
