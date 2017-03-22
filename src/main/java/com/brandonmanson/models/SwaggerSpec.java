package com.brandonmanson.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by brandonmanson on 3/20/17.
 */
public class SwaggerSpec {

    private ObjectMapper mapper;
    private String path;
    private String method;
    private int numParameters;


    public SwaggerSpec() {
        this.mapper = new ObjectMapper();
    }

    public SwaggerSpec(String[] swaggerValues) {
        this.mapper = new ObjectMapper();
        this.path = swaggerValues[0];
        this.method = swaggerValues[1];
        this.numParameters = Integer.parseInt(swaggerValues[2]);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public int getNumParameters() {
        return numParameters;
    }
}
