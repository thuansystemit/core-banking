package com.darkness.coreBanking.service;

import com.darkness.coreBanking.domain.Country;
import com.darkness.coreBanking.domain.User;
import com.darkness.coreBanking.domain.UserStatus;
import com.darkness.coreBanking.dto.UserDto;
import com.darkness.coreBanking.exception.CountryCodeException;
import com.darkness.coreBanking.exception.UserExistException;
import com.darkness.coreBanking.exception.UserNotFoundException;
import com.darkness.coreBanking.repository.CountryRepository;
import com.darkness.coreBanking.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    public UserServiceImpl(final UserRepository userRepository,
                       final CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public UserDto createUser(UserDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistException(String.format(
                    "Email already exists email=[%s]", request.getEmail()));
        }
        Country country = countryRepository.findByCountryCode(request.getCountryCode())
                .orElseThrow(() -> new CountryCodeException(
                        String.format("Invalid country code = [%s]", request.getCountryCode())));

        User user = new User();
        LocalDateTime createdAt = LocalDateTime.now();
        user.setAddress(request.getAddress());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCountry(country);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(createdAt);

        return  toDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserDto> getUserByPk(Long pk) {
        return userRepository.findByPk(pk).map(this::toDTO);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toDTO);
    }

    @Override
    public UserDto updateUser(UserDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format(
                "Could not found user by email=[%s]", request.getEmail())));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());

        if (request.getCountryCode() != null) {
            Country country = countryRepository.findByCountryCode(request.getCountryCode())
                    .orElseThrow(() -> new CountryCodeException(
                            String.format("Invalid country code = [%s]", request.getCountryCode())));
            user.setCountry(country);
        }

        return toDTO(userRepository.save(user));
    }

    @Override
    public void suspendedUser(Long pk) {
        updateUserStatus(pk, UserStatus.SUSPENDED);
    }

    @Override
    public void activeUser(Long pk) {
        updateUserStatus(pk, UserStatus.ACTIVE);
    }

    @Override
    public void lockUser(Long pk) {
        updateUserStatus(pk, UserStatus.LOCKED);
    }

    @Override
    public void closeUser(Long pk) {
        updateUserStatus(pk, UserStatus.CLOSED);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDTO);
    }

    @Override
    public void updateUserStatus(Long pk, UserStatus newStatus) {
        User user = userRepository.findByPk(pk)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Could not found user by pk=[%s]", pk)));
        UserStatus currentUserStatus = UserStatus.fromCode(user.getStatus().getCode());

        if (currentUserStatus.equals(newStatus)) {
            throw new IllegalStateException(String.format("User is already in status = [%s]: ", newStatus));
        }
        user.setStatus(newStatus);
        userRepository.save(user);
    }

    private void activeUser(Long pk, String status) {
        User user = userRepository.findByPk(pk)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "Could not found user by pk=[%s]", pk)));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    private UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setPk(user.getPk());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setCountryCode(user.getCountry().getCode());
        dto.setStatus(user.getStatus().name());
        return dto;
    }
}
