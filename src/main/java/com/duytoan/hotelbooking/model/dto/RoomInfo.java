package com.duytoan.hotelbooking.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomInfo {

    private Long id;

    private BigDecimal price;

    private String status;
}
