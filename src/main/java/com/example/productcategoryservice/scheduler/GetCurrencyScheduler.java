package com.example.productcategoryservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class GetCurrencyScheduler {
    private final RestTemplate restTemplate;
    @Value("${cb.url}")
    private String cbUrl;

    @Scheduled(cron = "0 * * * * *")
    public void getCurrencyFromCb() {
        ResponseEntity<HashMap> currency = restTemplate.getForEntity(cbUrl + "?currency=USD", HashMap.class);
        HashMap<String, String> hashMap = currency.getBody();
        if (!hashMap.isEmpty()) {
            for (String s : hashMap.keySet()) {
                System.out.println(s + "->" + hashMap.get(s));
            }
        }
    }
}
