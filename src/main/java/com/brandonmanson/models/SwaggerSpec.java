package com.brandonmanson.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

/**
 * Created by brandonmanson on 3/20/17.
 */
@Component
public class SwaggerSpec {

    private ObjectMapper mapper;
    private String path;
    private String method;
    private String paramType;


    public SwaggerSpec() {
        this.mapper = new ObjectMapper();
    }

    public SwaggerSpec(String[] swaggerValues) {
        this.mapper = new ObjectMapper();
        this.path = swaggerValues[0].toLowerCase();
        this.method = swaggerValues[1].toLowerCase();
        this.paramType = swaggerValues[2].toLowerCase();
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

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

}
