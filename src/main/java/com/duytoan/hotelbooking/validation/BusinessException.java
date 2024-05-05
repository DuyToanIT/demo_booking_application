package com.duytoan.hotelbooking.validation;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public final class BusinessException extends RuntimeException implements ServiceException {

    private final BaseException exceptionData;

    public BusinessException(List<BaseException.ExceptionDetail> exceptionDetails) {
        this.exceptionData = new BaseException(exceptionDetails);
    }

    public static BusinessException createException(String errorCode) {
        return new BusinessException(Collections.singletonList(new BaseException.ExceptionDetail(errorCode, BusinessErrorCode.getErrorMessageByCode(errorCode))));
    }

    public static BusinessException createException(String errorCode, String errorData) {
        return new BusinessException(Collections.singletonList(new BaseException.ExceptionDetail(errorCode, errorData)));
    }

    public static BusinessException createException(BusinessErrorCode businessErrorCode) {
        return new BusinessException(Collections.singletonList(new BaseException.ExceptionDetail(businessErrorCode.getErrorCode(), businessErrorCode.getErrorMessage())));
    }
}