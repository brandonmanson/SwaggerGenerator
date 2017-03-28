package com.brandonmanson.services;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.models.SlackResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

/**
 * Created by brandonmanson on 3/21/17.
 */
@Service
public class SlackUploadClientService {

    @Async
    public void postSwaggerSpecToSlackChannel(SlackRequest request, String swaggerSpec, String token) throws JSONException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());

        // Create rest template
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Construct the form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("token", token);
        formData.add("filename", "your_awesome_swagger.js");
        formData.add("channels", request.getChannelId());
        formData.add("filetype", "javascript");
        formData.add("pretty", "1");
        formData.add("content", swaggerSpec);

        // Construct the request and assign response object to response from slack
        HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://slack.com/api/files.upload", postRequest, String.class);
        JSONObject responseToJson = new JSONObject(response.getBody());
    }
}
