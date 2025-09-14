//package com.salAce.journalApp.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class TextToVoiceService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final String api = "https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128";
//
//    public byte[] voiceMeKrde() {
//        // 1️⃣ Prepare Headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("xi-api-key", "2400447f4276f6a0bc3f5e79389b29b999ed50c69943b4b691bc86945d96bddc");
//        // 2️⃣ Prepare Body
//        String jsonBody = "{\n" +
//                "  \"text\": \"+ hello brother + \",\n" +
//                        "  \"model_id\": \"eleven_multilingual_v2\"\n" +
//                        "}" ;
//
//
//        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
//
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                api,
//                HttpMethod.POST,
//                request,
//                byte[].class
//        );
//
//
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            try {
//                java.nio.file.Files.write(java.nio.file.Paths.get("output.mp3"), response.getBody());
//                System.out.println("✅ MP3 saved as output.mp3");
//                return response.getBody();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("❌ Request failed: " + response.getStatusCode());
//        }
//        throw new RuntimeException("Failed to generate voice. Status: " + response.getStatusCode());
//    }
//}


package com.salAce.journalApp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class TextToVoiceService {

    @Autowired
    private RestTemplate restTemplate;

    private final String api =
            "https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128";

    public byte[] voiceMeKrde(String text) {
        // 1️⃣ Prepare headers (same as .header in Unirest)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("xi-api-key", "2400447f4276f6a0bc3f5e79389b29b999ed50c69943b4b691bc86945d96bddc");

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
