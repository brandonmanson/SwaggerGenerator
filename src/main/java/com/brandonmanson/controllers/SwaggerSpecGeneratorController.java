package com.brandonmanson.controllers;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.services.SlackUploadClientService;
import com.brandonmanson.services.SwaggerSpecGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brandonmanson on 3/16/17.
 */
@RestController
@RequestMapping(value = "/swaggerSpecs")
public class SwaggerSpecGeneratorController {

    @Autowired
    SwaggerSpecGeneratorService generatorService;

    @Autowired
    SlackUploadClientService uploadClientService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String generateSwaggerSpecAndReturnAck(SlackRequest request) throws UnsupportedEncodingException, JsonProcessingException {
        String[] swaggerValues = request.generateSwaggerValues();
        String spec = generatorService.generateSwaggerSpec(swaggerValues);
        String ackString;

        if (swaggerValues.length < 3 || swaggerValues.length > 3)
        {
            ackString = "Wrong number of arguments. Your command should look like this:\n/swagger [path] [method] [number of params]";
        }

        ackString = "Looks good. We're generating a Swagger spec for: \npath: " + swaggerValues[0]
                + "\nmethod: " + swaggerValues[1]
                + "\nnumber of parameters: " + swaggerValues[2];


        uploadClientService.postSwaggerSpecToSlackChannel(request, spec);


        return ackString;
    }



}
