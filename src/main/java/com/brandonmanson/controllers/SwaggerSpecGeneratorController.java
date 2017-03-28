package com.brandonmanson.controllers;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.models.Team;
import com.brandonmanson.repositories.TeamRepository;
import com.brandonmanson.services.RetrieveTeamService;
import com.brandonmanson.services.SlackUploadClientService;
import com.brandonmanson.services.SwaggerSpecGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by brandonmanson on 3/16/17.
 */
@RestController
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/swaggerSpecs")
public class SwaggerSpecGeneratorController {

    @Autowired
    SwaggerSpecGeneratorService generatorService;

    @Autowired
    SlackUploadClientService uploadClientService;

    @Autowired
    RetrieveTeamService retrieveTeamService;

    @Autowired
    TeamRepository teamRepository;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String generateSwaggerSpecAndReturnAck(WebRequest request) throws UnsupportedEncodingException, JsonProcessingException, JSONException {

        SlackRequest slackRequest = new SlackRequest(request);
        String[] swaggerValues = slackRequest.generateSwaggerValues();
        String spec = generatorService.generateSwaggerSpec(swaggerValues);
        String ackString;

        if (swaggerValues.length < 3 || swaggerValues.length > 3)
        {
            ackString = "Wrong number of arguments. Your command should look like this:\n/swagger [path] [method] [number of params]";
        }

        ackString = "Looks good. We're generating a Swagger spec for: \npath: " + swaggerValues[0]
                + "\nmethod: " + swaggerValues[1]
                + "\nnumber of parameters: " + swaggerValues[2];


        List<Team> userList = retrieveTeamService.getUserFromSlackRequest(slackRequest);
        String token = userList.get(0).getAccessToken();


        uploadClientService.postSwaggerSpecToSlackChannel(slackRequest, spec, token);


        return ackString;
    }



}
