package com.salAce.journalApp.controller;

//import com.salAce.journalApp.entity.TextReader;
import com.salAce.journalApp.entity.VoiceRequest;
import com.salAce.journalApp.service.TextToVoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Voice APIs" , description = "Text to voice")
@RestController
@RequestMapping("/TextToVoice")

public class VoiceController {

@Autowired
private TextToVoiceService textToVoiceService ;



@PostMapping()
public ResponseEntity<byte[]> voiceMeKrde(@RequestBody VoiceRequest voiceRequest ) {

    String text = voiceRequest.getText();
    byte[] mp3Bytes = textToVoiceService.voiceMeKrde(text);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"voice.mp3\"")
            .contentType(MediaType.valueOf("audio/mpeg"))
            .body(mp3Bytes);
}



}
