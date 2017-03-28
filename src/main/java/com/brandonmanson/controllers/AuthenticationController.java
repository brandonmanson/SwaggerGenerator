package com.brandonmanson.controllers;

import com.brandonmanson.models.User;
import com.brandonmanson.repositories.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

/**
 * Created by brandonmanson on 3/26/17.
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String getApiToken(@RequestParam String code) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Construct the form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("code", code);
        formData.add("redirect_uri", "http://localhost:8080/authenticate/redirect");

        HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<MultiValueMap<String, String>>(formData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://slack.com/api/oauth.access", postRequest, String.class);
        JSONObject responseToJson = new JSONObject(response.getBody());


        User user = new User();
        user.setAccessToken(responseToJson.getString("access_token"));
        user.setScope(responseToJson.getString("scope"));
        user.setTeamName(responseToJson.getString("team_name"));
        user.setTeamId(responseToJson.getString("team_id"));
        userRepository.save(user);

        return "Authenticated!";
    }


}
