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

    public String generateSwaggerSpec(String[] swaggerValues) throws JsonProcessingException {
        SwaggerSpec spec = new SwaggerSpec(swaggerValues);
        ObjectNode swaggerSpec = generateSwaggerSpec(spec);
        String formattedSpec = prettyPrintSpec(swaggerSpec, spec);
        return formattedSpec;

    }

    private String prettyPrintSpec(ObjectNode swaggerSpec, SwaggerSpec spec) throws JsonProcessingException {
        return spec.getMapper().writer().withDefaultPrettyPrinter().writeValueAsString(swaggerSpec);
    }

    private ObjectNode createParameterObjectNode(int parameterNumber, SwaggerSpec spec) {
        ObjectNode parameterObjectNode = spec.getMapper().createObjectNode();
        String paramName = "param" + parameterNumber;
        parameterObjectNode.put(paramName, "subject");
        parameterObjectNode.put("description", "something descriptive");
        parameterObjectNode.put("required", "true or false. change this to a bool.");
        parameterObjectNode.put("type", "Up to you. I'm not a mind reader.");
        parameterObjectNode.put("paramType", "Path, Query, Header, Body or Form");
        return parameterObjectNode;
    }

    private ArrayNode createParameterObjectArray(SwaggerSpec spec) {
        ArrayNode parameterObjectArray = spec.getMapper().createArrayNode();
        int i = 1;
        while (i <= spec.getNumParameters())
        {
            ObjectNode parameterObjectNode = createParameterObjectNode(i, spec);
            ++i;
            parameterObjectArray.add(parameterObjectNode);
        }
        return parameterObjectArray;
    }

    private ObjectNode createOperationsPropertiesNode(SwaggerSpec spec, ArrayNode parameterObjectArray) {
        ObjectNode operationsPropertiesNode = spec.getMapper().createObjectNode();
        operationsPropertiesNode.put("method", spec.getMethod());
        operationsPropertiesNode.put("summary", "Make this a nice summary");
        operationsPropertiesNode.put("type", "string");
        operationsPropertiesNode.putPOJO("parameters", parameterObjectArray);
        return operationsPropertiesNode;
    }

    private ArrayNode createOperationsArrayNode(ObjectNode operationsPropertiesNode, SwaggerSpec spec) {
        ArrayNode operationsArrayNode = spec.getMapper().createArrayNode();
        operationsArrayNode.add(operationsPropertiesNode);
        return operationsArrayNode;
    }

    private ObjectNode createApisNode(ArrayNode operationsArrayNode, SwaggerSpec spec) {
        ObjectNode apisNode = spec.getMapper().createObjectNode();
        apisNode.put("path", spec.getPath());
        apisNode.putPOJO("operations", operationsArrayNode);
        return apisNode;
    }

    private ArrayNode createApisArrayNode(ObjectNode apisNode, SwaggerSpec spec) {
        ArrayNode apisArrayNode = spec.getMapper().createArrayNode();
        apisArrayNode.add(apisNode);
        return apisArrayNode;
    }

    // Generates the swaggerVersion and basePath info
    private ObjectNode createInfoNode(ArrayNode apisArrayNode, SwaggerSpec spec) {
        ObjectNode infoNode = spec.getMapper().createObjectNode();
        infoNode.put("swaggerVersion", "1.2");
        infoNode.put("basePath", "http://your_host_name/base_path");
        infoNode.putPOJO("apis", apisArrayNode);
        return infoNode;
    }

    private ObjectNode generateSwaggerSpec(SwaggerSpec spec) {
        // Generate params array
        ArrayNode parameterArray = createParameterObjectArray(spec);

        // Add params array to Operations Property node
        ObjectNode operationsPropertyNode = createOperationsPropertiesNode(spec, parameterArray);

        // Create Operations Properties Array node
        ArrayNode operationsPropertyArrayNode = createOperationsArrayNode(operationsPropertyNode, spec);

        // Add Operations Properties Array node to API node
        ObjectNode apisNode = createApisNode(operationsPropertyArrayNode, spec);

        // Create APIs Array Node
        ArrayNode apisArrayNode = createApisArrayNode(apisNode, spec);

        // Create Info Node
        ObjectNode infoNode = createInfoNode(apisArrayNode, spec);

        return infoNode;
    }

}
