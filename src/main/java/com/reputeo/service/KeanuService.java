package com.reputeo.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class KeanuService {
    private static final String API_URL = "https://whoa.onrender.com/whoas/random";
    private final WebClient webClient;

    public KeanuService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getRandomWhoaMoment() {
        try {
            JsonNode response = webClient.get()
                    .uri(API_URL)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response != null && response.isArray() && response.size() > 0) {
                JsonNode first = response.get(0);
                JsonNode video = first.path("video");
                String url720p = video.path("720p").asText(null);

                if (url720p != null) {
                    return url720p;
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching Keanu whoa video: " + e.getMessage());
        }

        return "https://default.whoa/video.mp4";
    }
}
