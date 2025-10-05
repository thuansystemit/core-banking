package com.darkness.coreBanking.controller;

import com.darkness.coreBanking.dto.UserDto;
import com.darkness.coreBanking.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "pk,asc") String[] sort) {
        Sort sortSpec = Sort.by(Sort.Order.by(sort[0]));
        if (sort.length > 1 && sort[1].equalsIgnoreCase("desc")) {
            sortSpec = sortSpec.descending();
        }

        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit, sortSpec);
        return userService.getAllUsers(pageable);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto request) {
        return userService.createUser(request);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto request) {
        return userService.updateUser(request);
    }

    @PutMapping("/{pk}/suspended")
    public ResponseEntity<Optional<UserDto>> suspendedUser(@PathVariable Long pk) {
        userService.suspendedUser(pk);
        return ResponseEntity.ok(userService.getUserByPk(pk));
    }

    @PutMapping("/{pk}/active")
    public ResponseEntity<Optional<UserDto>> activeUser(@PathVariable Long pk) {
        userService.activeUser(pk);
        return ResponseEntity.ok(userService.getUserByPk(pk));
    }

    @PutMapping("/{pk}/lock")
    public ResponseEntity<Optional<UserDto>> lockUser(@PathVariable Long pk) {
        userService.lockUser(pk);
        return ResponseEntity.ok(userService.getUserByPk(pk));
    }

    @PutMapping("/{pk}/close")
    public ResponseEntity<Optional<UserDto>> closeUser(@PathVariable Long pk) {
        userService.closeUser(pk);
        return ResponseEntity.ok(userService.getUserByPk(pk));
    }
}
