package com.duytoan.hotelbooking.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessErrorCode {

    NOT_FOUND_USER("10", "Not found User"),
    NOT_FOUND_BOOKING("11", "Not found booking"),
    ROOM_INVALID("12", "Room is invalid or unavailable"),
    BOOKING_NOT_ALLOW_UPDATE("13", "Can't update book"),

    BOOKING_NOT_ALLOW_CANCEL("14", "Can't update book");
    private String errorCode;

    private String errorMessage;

    public static String getErrorMessageByCode(String errorCode){
        for (BusinessErrorCode code : BusinessErrorCode.values()) {
            if (code.errorCode.equals(errorCode)) {
                return code.errorMessage;
            }
        }
        return "Unknown error code: " + errorCode;
    }
}
