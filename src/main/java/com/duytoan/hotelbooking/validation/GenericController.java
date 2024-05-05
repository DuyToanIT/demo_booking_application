package com.duytoan.hotelbooking.validation;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GenericController extends ResponseEntityExceptionHandler {

    /**
     * override this function to extend map between message vs errorCode
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> handleCheckedException(BusinessException exception, WebRequest request) {
        return handleMicroServiceException(exception, request, HttpStatus.BAD_REQUEST, ErrorDetailMarshaller::toJsonNode);
    }


    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleBaseException(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = buildErrorDetail("01", null);
        log.error("Error code: {} due to ", errorDetail.getErrorCode(), exception);

        return handleExceptionInternal(exception, ErrorDetailMarshaller.toNode(errorDetail), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    public ResponseEntity<Object> handleMicroServiceException(ServiceException exception, WebRequest request,
                                                              HttpStatus badRequest,
                                                              Function<List<ErrorDetail>, JsonNode> buildError) {
        List<ErrorDetail> errorDetails = exception
                .getExceptionData().getExceptionDetails().stream()
                .filter(exceptionDetail -> StringUtils.isNotBlank(exceptionDetail.getErrorCode()))
                .map(errorCode -> buildErrorDetail(errorCode.getErrorCode(), errorCode.getErrorData()))
                .collect(Collectors.toList());

        if (errorDetails.isEmpty()) {
            return handleBaseException((Exception) exception, request);
        }

        log.error("Error codes: \n {} \n due to",
                errorDetails.stream()
                        .map(ErrorDetail::getErrorCode)
                        .collect(Collectors.joining("\n")),
                exception.getExceptionData());

        return handleExceptionInternal((Exception) exception, buildError.apply(errorDetails), new HttpHeaders(), badRequest, request);
    }

    protected ErrorDetail buildErrorDetail(String errorCode, String fields) {
        return ErrorDetail.builder()
                .errorCode(errorCode)
                .errorMessage(fields)
                .build();
    }

}

