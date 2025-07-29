package com.reputeo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.verify-url}")
    private String verifyUrl;

    private final WebClient webClient;

    public RecaptchaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public boolean verifyToken(String token) {
        if (token == null || token.isEmpty()) return false;

        return webClient.post()
                .uri(verifyUrl + "?secret={secret}&response={token}", secret, token)
                .retrieve()
                .bodyToMono(RecaptchaResponse.class)
                .map(RecaptchaResponse::isSuccess)
                .onErrorReturn(false)
                .block();
    }

    private static class RecaptchaResponse {
        private boolean success;
        public boolean isSuccess() { return success; }
    }
}