package com.brandonmanson.controllers;

import com.brandonmanson.models.SlackRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by brandonmanson on 3/16/17.
 */
@RestController
@RequestMapping(value = "/swaggerSpecs")
public class SwaggerSpecGeneratorController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String generateSwaggerSpecAndReturnAck(SlackRequest request) {
        String[] swaggerValues = request.generateSwaggerValues();
        String ackString = "Looks good. We're generating a Swagger spec for: \npath: " + swaggerValues[0] + "\nmethod: " + swaggerValues[1] + "\nnumber of parameters: " + swaggerValues[2];
        return ackString;
    }



}
