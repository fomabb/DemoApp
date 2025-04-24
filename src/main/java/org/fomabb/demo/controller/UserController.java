package org.fomabb.demo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.request.UserAddEmailRequest;
import org.fomabb.demo.dto.request.UserAddPhoneRequest;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "Управление пользователями", description = "`Интерфейс для управления пользователями`")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping("/add-email")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> addEmailToUserById(@RequestBody @Valid UserAddEmailRequest request) {
        userService.addEmailToUser(request.getUserId(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add-phone")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> addPhoneToUserById(@RequestBody @Valid UserAddPhoneRequest request) {
        userService.addPhoneToUser(request.getUserId(), request.getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/show-emails/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<EmailDataDtoResponse> getAllEmailsByUserId(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getAllEmailsByUserId(id));
    }
}
