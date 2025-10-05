package com.darkness.coreBanking.service;

import com.darkness.coreBanking.domain.UserStatus;
import com.darkness.coreBanking.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    /**
     * Create a new user
     */
    UserDto createUser(UserDto request);

    /**
     * Get user by id
     */
    Optional<UserDto> getUserByPk(Long pk);

    /**
     * Find user by email
     */
    Optional<UserDto> getUserByEmail(String email);

    /**
     * Update user info
     */
    UserDto updateUser(UserDto request);

    /**
     * Deactivate or mark user as inactive
     */
    void suspendedUser(Long pk);

    /**
     * activate or mark user as active again
     */
    void activeUser(Long pk);

    /**
     * lock or mark user as lock
     */
    void lockUser(Long pk);

    /**
     * close or mark user as close
     */
    void closeUser(Long pk);

    void updateUserStatus(Long pk, UserStatus newStatus);
    /**
     * Get paged list of users
     */
    Page<UserDto> getAllUsers(Pageable pageable);
}
