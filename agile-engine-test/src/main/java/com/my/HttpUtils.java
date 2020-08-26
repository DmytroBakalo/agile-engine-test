package com.my;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Collections;

public class HttpUtils {

    private HttpUtils() {
    }

    public static String getApiKeyJson() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        ((ObjectNode) rootNode).put("apiKey", "23567b218376f79d9415");

        String jsonString = null;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static int getPagesCount(String rawImagesResponse) throws IOException {
        ObjectNode node = new ObjectMapper().readValue(rawImagesResponse, ObjectNode.class);
        if (node.has("pageCount")) {
            return node.get("pageCount").asInt();
        }
        return 0;
    }

    public static int getCurrentPage(String rawImagesResponse) throws IOException {
        ObjectNode node = new ObjectMapper().readValue(rawImagesResponse, ObjectNode.class);
        if (node.has("page")) {
            return node.get("page").asInt();
        }
        return 0;
    }

    public static String getOnlyImages(String rawImagesResponse) throws IOException {
        ObjectNode node = new ObjectMapper().readValue(rawImagesResponse, ObjectNode.class);
        if (node.has("pictures")) {
            return node.get("pictures").asText();
        }
        return "";
    }

    public static HttpHeaders fillHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        headers.setConnection("keep-alive");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static String getTokenFromAuthorizationRequest(String rawTokenResponse) throws IOException {
        ObjectNode node = new ObjectMapper().readValue(rawTokenResponse, ObjectNode.class);
        if (node.has("token")) {
            return node.get("token").asText();
        }
        return "";
    }


}
