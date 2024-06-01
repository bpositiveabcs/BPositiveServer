package bpos.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ChatbotController {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotController.class);

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    @PostMapping("/chatbot")
    public ResponseEntity<?> chatbot(@RequestBody Map<String, String> request) {
        logger.info("Received request: " + request);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(flaskServerUrl, HttpMethod.POST, entity, String.class);

        logger.info("Received response from Flask: " + response.getBody());

        return ResponseEntity.ok(response.getBody());
    }
}
