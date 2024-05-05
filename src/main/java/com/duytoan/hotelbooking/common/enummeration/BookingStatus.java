package com.duytoan.hotelbooking.common.enummeration;

import lombok.Getter;

import java.util.List;

@Getter
public enum BookingStatus {

    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed"),
    DELETED("Deleted");

    private final String label;

    BookingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label != null ? label : "Invalid";
    }

    public static List<BookingStatus> getListStatusInProgress(){
        return List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED);
    }


}
