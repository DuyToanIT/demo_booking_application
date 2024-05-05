package com.duytoan.hotelbooking.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressInfo {

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;
}
