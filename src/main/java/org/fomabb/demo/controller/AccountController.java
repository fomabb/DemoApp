package org.fomabb.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.exception.CommonExceptionResponse;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.service.AccountService;
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
@RequestMapping("/api/v1/accounts")
@Validated
@RequiredArgsConstructor
@Tag(name = "Управление аккаунтами", description = "`Интерфейс для управления аккаунтами`")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> performTransfer(@RequestBody @Valid TransferDtoRequest request) {
        accountService.performTransfer(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Получить аккаунт по ID пользователя.",
            description = """
                    `
                    Возвращает информацию об аккаунте, связанном с указанным пользователем по его идентификатору.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Аккаунт успешно найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Account.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Аккаунт не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable("userId") @Valid Long id) {
        return ResponseEntity.ok(accountService.getAccountByUserId(id));
    }
}
