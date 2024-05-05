package com.duytoan.hotelbooking.validation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BaseException extends Exception implements Serializable {

    @Getter
    @AllArgsConstructor
    public static class ExceptionDetail implements Serializable {

        /**
         * Internal Error Code
         */
        String errorCode;

        /**
         * Data/Fields to map "%s" in error message mapping
         */
        String errorData;
    }

    private final List<ExceptionDetail> exceptionDetails;

    public BaseException(List<ExceptionDetail> exceptionDetails) {
        if (CollectionUtils.isEmpty(exceptionDetails)) {
            this.exceptionDetails = new ArrayList<>();
            return;
        }
        this.exceptionDetails = exceptionDetails;
    }

}

