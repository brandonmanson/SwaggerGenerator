package com.brandonmanson.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import com.brandonmanson.models.SwaggerSpec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by brandonmanson on 3/19/17.
 */

@Service
public class SwaggerSpecGeneratorService {

    public String generateSwaggerSpecFromSwaggerValues(String[] swaggerValues) throws JsonProcessingException {
        SwaggerSpec spec = new SwaggerSpec(swaggerValues);
        ObjectNode swaggerSpec = generateSwaggerSpec(spec);
        String formattedSpec = prettyPrintSpec(swaggerSpec, spec);
        System.out.println(formattedSpec);
        return formattedSpec;

    }

    private String prettyPrintSpec(ObjectNode swaggerSpec, SwaggerSpec spec) throws JsonProcessingException {
        return spec.getMapper().writer().withDefaultPrettyPrinter().writeValueAsString(swaggerSpec);
    }

    // These are always the same regardless of parameter type
    private void generateParameterObjectStandardValues(ObjectNode objectNode, SwaggerSpec spec) {
        objectNode.put("name", "subject");
        objectNode.put("in", spec.getParamType());
        objectNode.put("description", "describe this parameter");
        objectNode.put("required", "true or false. change this to a boolean");
    }

    private ObjectNode createSchemaObject(SwaggerSpec spec) {
        ObjectNode schemaObject = spec.getMapper().createObjectNode();
        schemaObject.put("type", "string");
        return schemaObject;
    }

    private ObjectNode createItemsObject(SwaggerSpec spec) {
        ObjectNode itemsObject = spec.getMapper().createObjectNode();
        itemsObject.put("type", "string");
        return itemsObject;
    }

    private ObjectNode createInfoObject(SwaggerSpec spec) {
        ObjectNode infoObject = spec.getMapper().createObjectNode();
        infoObject.put("title", "Your Awesome Swagger Spec");
        infoObject.put("version", "1.0.0");
        infoObject.put("description", "Describe Your API");
        return infoObject;
    }

    private ArrayNode createConsumesArray(SwaggerSpec spec) {
        ArrayNode consumesArray = spec.getMapper().createArrayNode();
        consumesArray.add("application/x-www-form-urlencoded");
        consumesArray.add("multipart/form-data");
        return consumesArray;
    }

    private ObjectNode createResponseObject(SwaggerSpec spec) {
        ObjectNode responseObject = spec.getMapper().createObjectNode();
        ObjectNode schemaInResponse = createSchemaObject(spec);
        responseObject.put("description", "describe this response");
        responseObject.putPOJO("schema", schemaInResponse);
        return responseObject;
    }

    private ObjectNode createResponsesObject(SwaggerSpec spec) {
        ObjectNode responsesObject = spec.getMapper().createObjectNode();
        ObjectNode responseObject = createResponseObject(spec);
        if (spec.getMethod().equals("get")) {
            responsesObject.putPOJO("200", responseObject);
        } else {
            responsesObject.putPOJO("201", responseObject);
        }
        return responsesObject;
    }

    private ObjectNode generateSchemaObjectWithItemObject(SwaggerSpec spec) {
        ObjectNode itemObject = createItemsObject(spec);
        ObjectNode schemaObjectWithItemObject = spec.getMapper().createObjectNode();
        schemaObjectWithItemObject.put("type", "array");
        schemaObjectWithItemObject.putPOJO("items", itemObject);
        return schemaObjectWithItemObject;
    }

    // Create a Parameter Object if parameter type is 'Body'
    private ObjectNode createBodyParameterObjectNode(SwaggerSpec spec) {
        ObjectNode bodyParameterObjectNode = spec.getMapper().createObjectNode();
        ObjectNode schemaObject = createSchemaObject(spec);
        generateParameterObjectStandardValues(bodyParameterObjectNode, spec);
        bodyParameterObjectNode.putPOJO("schema", schemaObject);
        return bodyParameterObjectNode;
    }

    // Create a Parameter Object if parameter type is anything other than 'Body'
    private ObjectNode createDefaultParameterObjectNode(SwaggerSpec spec) {
        ObjectNode parameterObjectNode = spec.getMapper().createObjectNode();
        generateParameterObjectStandardValues(parameterObjectNode, spec);
        parameterObjectNode.put("type", "'string', 'number', 'integer', 'boolean', 'array' or 'file'. If 'file', then you need to set 'consumes' to either 'multipart/form-data' or 'application/x-www-form-urlencoded'.");
        return parameterObjectNode;
    }


    private ArrayNode createParameterObjectArray(SwaggerSpec spec) {
        ArrayNode parameterObjectArray = spec.getMapper().createArrayNode();
        if (spec.getParamType().equals("body"))
        {
            ObjectNode bodyParameterObjectNode = createBodyParameterObjectNode(spec);
            parameterObjectArray.add(bodyParameterObjectNode);
        } else {
            ObjectNode defaultParameterNode = createDefaultParameterObjectNode(spec);
            parameterObjectArray.add(defaultParameterNode);
        }

        return parameterObjectArray;
    }

    private ObjectNode createOperationObject(SwaggerSpec spec, ArrayNode parameterObjectArray, ObjectNode responsesObject) {
        ObjectNode operationObject = spec.getMapper().createObjectNode();
        operationObject.put("description", "Describe what this operation does");
        operationObject.put("summary", "Make this a nice summary");
        operationObject.putPOJO("parameters", parameterObjectArray);
        operationObject.putPOJO("responses", responsesObject);
        return operationObject;
    }

    private ObjectNode createOperationObjectForParamTypeFile(SwaggerSpec spec, ArrayNode parameterObjectArray, ObjectNode responsesObject) {
        ObjectNode operationObject = spec.getMapper().createObjectNode();
        ArrayNode consumesArray = createConsumesArray(spec);
        operationObject.put("description", "Describe what this operation does");
        operationObject.put("summary", "Make this a nice summary");
        operationObject.putPOJO("consumes", consumesArray);
        operationObject.putPOJO("parameters", parameterObjectArray);
        operationObject.putPOJO("responses", responsesObject);
        return operationObject;
    }

    private ObjectNode createPathItemObject(ObjectNode operationsPropertiesNode, SwaggerSpec spec) {
        ObjectNode pathItemNode = spec.getMapper().createObjectNode();
        pathItemNode.putPOJO(spec.getMethod(), operationsPropertiesNode);
        return pathItemNode;
    }

    private ObjectNode createPathObject(ObjectNode pathItemNode, SwaggerSpec spec) {
        ObjectNode pathObjectNode = spec.getMapper().createObjectNode();
        if (spec.getParamType().equals("path"))
        {
            String path = spec.getPath() + "/{your_param}";
            pathObjectNode.putPOJO(path, pathItemNode);
        } else {
            pathObjectNode.putPOJO(spec.getPath(), pathItemNode);
        }
        return pathObjectNode;
    }

    // Generates the swaggerVersion and basePath info
    private ObjectNode createSwaggerObject(ObjectNode pathObjectNode, SwaggerSpec spec) {
        ObjectNode swaggerObject = spec.getMapper().createObjectNode();
        ObjectNode infoObject = createInfoObject(spec);
        swaggerObject.put("swagger", "2.0");
        swaggerObject.putPOJO("info", infoObject);
        swaggerObject.put("basePath", "http://your_host_name/base_path");
        swaggerObject.putPOJO("paths", pathObjectNode);
        return swaggerObject;
    }

    private ObjectNode generateSwaggerSpec(SwaggerSpec spec) {
        // Generate responses object
        ObjectNode responsesObject = createResponsesObject(spec);

        // Generate params array
        ArrayNode parameterArray = createParameterObjectArray(spec);

        ObjectNode operationObject;

        // Create Operation Object
        if (spec.getParamType().equals("file"))
        {
            operationObject = createOperationObjectForParamTypeFile(spec, parameterArray, responsesObject);
        } else {
            operationObject = createOperationObject(spec, parameterArray, responsesObject);
        }

        // Create Path Item Object
        ObjectNode pathItemObject = createPathItemObject(operationObject, spec);

        // Create Path Object
        ObjectNode pathObject = createPathObject(pathItemObject, spec);

        // Create Swagger Object
        ObjectNode swaggerObject = createSwaggerObject(pathObject, spec);

        return swaggerObject;
    }

}
