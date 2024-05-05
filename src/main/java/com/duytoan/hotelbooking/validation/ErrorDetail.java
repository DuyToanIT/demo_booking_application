package com.duytoan.hotelbooking.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetail {

    private String errorMessage;

    private String errorCode;
}
