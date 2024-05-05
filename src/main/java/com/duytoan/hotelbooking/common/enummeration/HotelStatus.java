package com.duytoan.hotelbooking.common.enummeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HotelStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    CLOSED("Closed");

    private final String label;


}
