package com.brandonmanson.controllers;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.services.SwaggerSpecGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by brandonmanson on 3/16/17.
 */
@RestController
@RequestMapping(value = "/swaggerSpecs")
public class SwaggerSpecGeneratorController {

    @Autowired
    SwaggerSpecGeneratorService service;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String generateSwaggerSpecAndReturnAck(SlackRequest request) {
        String[] swaggerValues = request.generateSwaggerValues();
        String spec = service.generateSwaggerSpec(swaggerValues);

        String ackString = "Looks good. We're generating a Swagger spec for: \npath: " + swaggerValues[0]
                + "\nmethod: " + swaggerValues[1]
                + "\nnumber of parameters: " + swaggerValues[2]
                + "\njson:"
                + "\n" + spec;
        return ackString;
    }



}
