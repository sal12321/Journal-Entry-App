
package com.salAce.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class TextToVoiceService {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${elevenlabs.api}")
    private String api;

    @Value("${elevenlabs.api.key}")
    private String apiKey;





    public byte[] voiceMeKrde(String text) {
        // 1️⃣ Prepare headers (same as .header in Unirest)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("xi-api-key", apiKey);

        // 2️⃣ Build request body (same as .body in Unirest)
        String jsonBody = """
        {
          "text": "%s",
          "model_id": "eleven_multilingual_v2"
        }
        """.formatted(text);

        // 3️⃣ Combine headers + body
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // 4️⃣ Execute POST and get binary response (mp3 as byte[])
        ResponseEntity<byte[]> response = restTemplate.exchange(
                api,
                HttpMethod.POST,
                request,
                byte[].class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate voice. Status: " + response.getStatusCode());
        }
    }
}
