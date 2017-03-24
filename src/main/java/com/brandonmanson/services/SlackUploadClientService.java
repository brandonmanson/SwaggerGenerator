package com.brandonmanson.services;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.models.SlackResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Future;

/**
 * Created by brandonmanson on 3/21/17.
 */
@Service
public class SlackUploadClientService {

    private final RestTemplate restTemplate;

    public SlackUploadClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public void postSwaggerSpecToSlackChannel(SlackRequest request, String swaggerSpec) {

        // Get token from request
        String token = request.getToken();

        // Create rest template
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Add token to headers
        headers.set("token", token);

        // Construct the form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("filename", "your_awesome_swagger.js");
        formData.add("channels", request.getChannelId());
        formData.add("filetype", "javascript");
        formData.add("pretty", "1");
        formData.add("content", swaggerSpec);

        // Construct the request and assign response object to response from slack
        HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
        ResponseEntity<SlackResponse> response = restTemplate.postForEntity("https://slack.com/api/files.upload", postRequest, SlackResponse.class);
        if (response.getBody().getFile() != null)
        {
            System.out.println(response.getStatusCode() + "\n" + response.getBody().getFile());
        }

        System.out.println(response.getStatusCode() + "\n" + response.getBody().getError());
    }
}
