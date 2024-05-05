package com.duytoan.hotelbooking.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.duytoan.hotelbooking.model.entity.User;
import com.duytoan.hotelbooking.repository.UserRepository;
import com.duytoan.hotelbooking.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Kích hoạt Mockito
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository; // Tạo mock object

    @InjectMocks
    private UserServiceImpl userService; // Inject mock vào UserServiceImpl

    @Test
    public void testGetUserById_UserExists() {
        // Tạo User giả
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUserName("usertest1");

        // Cài đặt hành vi của repository
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Gọi phương pháp được kiểm thử
        Optional<User> user = userService.getUserById(userId);

        // Kiểm tra kết quả
        assertNotNull(user);
        assertEquals("usertest1", user.get().getUserName());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Cài đặt hành vi của repository khi không tìm thấy
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Gọi phương pháp được kiểm thử
        Optional<User> user = userService.getUserById(userId);

        // Kiểm tra kết quả
        assertTrue(user.isEmpty()); // Khi không tìm thấy user, nên trả về null
    }
}
