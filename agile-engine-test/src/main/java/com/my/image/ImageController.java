package com.my.image;

import static com.my.HttpUtils.fillHeaders;
import static com.my.HttpUtils.getPagesCount;
import static com.my.HttpUtils.getTokenFromAuthorizationRequest;

import com.my.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import javax.annotation.PostConstruct;

@Controller
public class ImageController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init() throws IOException {
        HttpHeaders headers = fillHeaders();
        String authResponse = obtainAuthorizationToken(headers);
        headers.set("Authorization", getTokenFromAuthorizationRequest(authResponse));
        String response = handleGetImages(headers);

        int pageCount = getPagesCount(response);
        int currentPage;
        /* Way to save payload to local storage
        do {
            imageService.save(getOnlyImages(response));
            response = handleGetImages(headers);
            currentPage = getCurrentPage(response);
        } while (currentPage != pageCount);
        */
    }

    @GetMapping(value = "/images", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String images(@RequestParam @Nullable String page) throws IOException {
        HttpHeaders headers = fillHeaders();
        String authResponse = obtainAuthorizationToken(headers);
        headers.set("Authorization", getTokenFromAuthorizationRequest(authResponse));
        return page != null ? handlePagination(page, headers) : handleGetImages(headers);
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String imageById(@PathVariable @Nullable String id) throws IOException {
        HttpHeaders headers = fillHeaders();
        String authResponse = obtainAuthorizationToken(headers);
        headers.set("Authorization", getTokenFromAuthorizationRequest(authResponse));
        return handleGetById(id, headers);
    }

    private String handlePagination(String page, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange("http://interview.agileengine.com/images".concat("?page=").concat(page), HttpMethod.GET, entity, String.class)
                .getBody();
    }

    private String handleGetById(String id, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange("http://interview.agileengine.com/images".concat("/").concat(id), HttpMethod.GET, entity, String.class)
                .getBody();
    }

    private String handleGetImages(HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange("http://interview.agileengine.com/images", HttpMethod.GET, entity, String.class).getBody();
    }

    private String obtainAuthorizationToken(HttpHeaders headers) {
        HttpEntity<String> request = new HttpEntity<>(HttpUtils.getApiKeyJson(), headers);
        return restTemplate.postForObject("http://interview.agileengine.com/auth", request, String.class);
    }
}
