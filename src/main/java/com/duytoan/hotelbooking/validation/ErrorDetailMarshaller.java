package com.duytoan.hotelbooking.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class ErrorDetailMarshaller {


    public static final String ERRORS = "errors";

    public static final String ERROR_CODE = "errorCode";

    public static final String ERROR_MESSAGE = "errorMessage";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private ErrorDetailMarshaller() {

    }

    /**
     * Convert list of ErrorDetails to Array JsonNode with root is "errors"
     *
     * @return Array JsonNode with root is "errors"
     */
    public static JsonNode toJsonNode(List<ErrorDetail> errorDetails) {
        ArrayNode arrayNode = MAPPER.createArrayNode();

        for (ErrorDetail errorDetail : errorDetails) {
            arrayNode.add(toNode(errorDetail));
        }

        ObjectNode rootNode = MAPPER.createObjectNode();
        rootNode.set(ERRORS, arrayNode);
        return rootNode;
    }

    /**
     * Convert single ErrorDetail to JsonNode with no root
     *
     * @return JsonNode with root is "errors"
     */
    public static JsonNode toNode(ErrorDetail errorDetail) {
        ObjectNode rootNode = MAPPER.createObjectNode();
        rootNode.put(ERROR_CODE, errorDetail.getErrorCode());
        rootNode.put(ERROR_MESSAGE, errorDetail.getErrorMessage());
        return rootNode;
    }
}
