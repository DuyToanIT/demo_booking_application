package com.duytoan.hotelbooking.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.duytoan.hotelbooking.model.dto.response.HotelResponse;
import com.duytoan.hotelbooking.model.entity.Hotel;
import com.duytoan.hotelbooking.repository.HotelRepository;
import com.duytoan.hotelbooking.service.impl.HotelServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito trong JUnit
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    public void testSearchHotel() {
        // Mock hành vi của HotelRepository
        String keyword = "test";
        Pageable pageable = PageRequest.of(0, 10);
        Hotel hotel1 = new Hotel();
        hotel1.setName("Test Hotel 1");
        hotel1.setLocation("Test Location 1");
        Hotel hotel2 = new Hotel();
        hotel2.setName("Another Test Hotel");
        hotel2.setLocation("Another Location");

        Page<Hotel> hotelPage = new PageImpl<>(Arrays.asList(hotel1, hotel2), pageable, 2);

        when(hotelRepository.findByNameContainsIgnoreCaseOrLocationContainsIgnoreCase(keyword, keyword, pageable))
                .thenReturn(hotelPage);

        // Tạo mock response
        HotelResponse hotelResponse1 = new HotelResponse();
        hotelResponse1.setName("Test Hotel 1");
        HotelResponse hotelResponse2 = new HotelResponse();
        hotelResponse2.setName("Another Test Hotel");

        when(objectMapper.convertValue(hotel1, HotelResponse.class)).thenReturn(hotelResponse1);
        when(objectMapper.convertValue(hotel2, HotelResponse.class)).thenReturn(hotelResponse2);

        // Gọi phương pháp cần kiểm tra
        Page<HotelResponse> result = hotelService.searchHotel(keyword, pageable);

        // Kiểm tra kết quả
        assertNotNull(result, "Expected a valid Page of HotelResponse");
        assertEquals(2, result.getTotalElements(), "Expected 2 hotel results");
        assertEquals("Test Hotel 1", result.getContent().get(0).getName(), "Expected name 'Test Hotel 1'");
        assertEquals("Another Test Hotel", result.getContent().get(1).getName(), "Expected name 'Another Test Hotel'");

        // Kiểm tra hành vi của hotelRepository
        verify(hotelRepository, times(1))
                .findByNameContainsIgnoreCaseOrLocationContainsIgnoreCase(keyword, keyword, pageable);
    }
}

