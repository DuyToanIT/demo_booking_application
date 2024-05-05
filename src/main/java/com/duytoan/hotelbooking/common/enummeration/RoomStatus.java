package com.duytoan.hotelbooking.common.enummeration;

import lombok.Getter;

@Getter
public enum RoomStatus {

    AVAILABLE("Available"),
    BOOKED("Booked"),
    UNDER_MAINTENANCE("Under Maintenance");

    private final String label;

    RoomStatus(String label) {
        this.label = label;
    }

}
