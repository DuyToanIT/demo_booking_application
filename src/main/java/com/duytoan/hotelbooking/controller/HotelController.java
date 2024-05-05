package com.duytoan.hotelbooking.controller;

import com.duytoan.hotelbooking.common.constant.ApiConstant;
import com.duytoan.hotelbooking.model.dto.response.HotelResponse;
import com.duytoan.hotelbooking.model.entity.Hotel;
import com.duytoan.hotelbooking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.version + ApiConstant.HOTEL)
public class HotelController {

    @Autowired
    private HotelService hotelService;

    // Search hotel
    @Operation(summary = "Search hotel base on address or name", description = "Return hotel info include name, address, phone, room available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class, subTypes = {Hotel.class}))
            )
    })

    @GetMapping("/")
    public ResponseEntity<Page<HotelResponse>> searchHotel(@RequestParam(required = false, defaultValue = "") String keyword,
                                                           @ParameterObject
                                                           @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable) {
        Page<HotelResponse> hotels = hotelService.searchHotel(keyword, pageable);
        return ResponseEntity.ok(hotels);
    }
}
