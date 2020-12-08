package com.bank.engine.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HttpClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * get
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String url, Class<T> clazz) {
        try {
            long start = System.currentTimeMillis();
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, clazz);

            log.info("url={} \t status={} \t {}ms", url, responseEntity.getStatusCode(), (System.currentTimeMillis() - start));

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

}
