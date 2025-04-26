package org.fomabb.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.dto.UserDataDto;
import org.fomabb.demo.dto.exception.CommonExceptionResponse;
import org.fomabb.demo.dto.request.UpdateEmailRequest;
import org.fomabb.demo.dto.request.UpdatePhoneRequest;
import org.fomabb.demo.dto.request.UserAddEmailRequest;
import org.fomabb.demo.dto.request.UserAddPhoneRequest;
import org.fomabb.demo.dto.response.EmailDataDtoResponse;
import org.fomabb.demo.dto.response.PageableResponse;
import org.fomabb.demo.dto.response.PhoneDataDtoResponse;
import org.fomabb.demo.dto.response.UserdataDtoResponse;
import org.fomabb.demo.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "Управление пользователями", description = "`Интерфейс для управления пользователями`")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Добавить email пользователю.",
            description = """
                    `
                    Добавляет новый email для указанного пользователя.
                    Принимает данные для обновления, включая идентификатор пользователя и email.
                    Возвращает статус 201 Created при успешном добавлении.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAddEmailRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Email успешно добавлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PostMapping("/add-email")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> addEmailToUserById(@RequestBody @Valid UserAddEmailRequest request) {
        userService.addEmailToUser(request.getUserId(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Обновить электронную почту пользователя.",
            description = """
                    `
                    Обновляет электронную почту для указанного пользователя.
                    Принимает данные для обновления, включая идентификатор пользователя и новый адрес электронной почты.
                    Возвращает статус 202 Accepted при успешном обновлении.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateEmailRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "`Электронная почта успешно обновлена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "`Нет доступа к этому ресурсу`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PatchMapping("/update-email")
    public ResponseEntity<Void> updateEmail(@RequestBody @Valid UpdateEmailRequest request) {
        userService.updateEmail(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Удалить электронную почту пользователя.",
            description = """
                    `
                    Удаляет электронную почту для указанного пользователя по его идентификатору.
                    Возвращает статус 204 No Content при успешном удалении.
                    `
                    """,
            parameters = {
                    @Parameter(name = "userId", required = true, description = "Идентификатор пользователя."),
                    @Parameter(name = "emailId", required = true, description = "Идентификатор электронной почты для удаления.")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "`Электронная почта успешно удалена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь или электронная почта не найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "`Нет доступа к этому ресурсу`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @DeleteMapping("/{userId}/delete-email/{emailId}")
    public ResponseEntity<Void> deleteEByIdByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("emailId") Long emailId
    ) {
        userService.removePhoneByUserIdEmailId(userId, emailId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить все номера телефона пользователя.",
            description = """
                    `
                    Возвращает все номера телефонов, связанные с указанным пользователем по его идентификатору.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список телефонных номеров успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmailDataDtoResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/show-phones/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<PhoneDataDtoResponse> getAllPhonesByUserId(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getAllPhonesByUserId(id));
    }

    @Operation(
            summary = "Добавить телефон пользователю.",
            description = """
                    `
                    Добавляет новый телефон для указанного пользователя.
                    Принимает данные для обновления, включая идентификатор пользователя и телефон.
                    Возвращает статус 201 Created при успешном добавлении.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAddPhoneRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Телефон успешно добавлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PostMapping("/add-phone")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> addPhoneToUserById(@RequestBody @Valid UserAddPhoneRequest request) {
        userService.addPhoneToUser(request.getUserId(), request.getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Обновить телефон пользователя.",
            description = """
                    `
                    Обновляет телефон для указанного пользователя.
                    Принимает данные для обновления, включая идентификатор пользователя и новый телефон.
                    Возвращает статус 202 Accepted при успешном обновлении.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePhoneRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "`Телефон успешно обновлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "`Нет доступа к этому ресурсу`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PatchMapping("/update-phone")
    public ResponseEntity<Void> updatePhone(@RequestBody @Valid UpdatePhoneRequest request) {
        userService.updatePhone(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Удалить номер телефона пользователя.",
            description = """
                    `
                    Удаляет номер телефона для указанного пользователя по его идентификатору.
                    Возвращает статус 204 No Content при успешном удалении.
                    `
                    """,
            parameters = {
                    @Parameter(name = "userId", required = true, description = "Идентификатор пользователя."),
                    @Parameter(name = "phoneId", required = true, description = "Идентификатор номера телефона для удаления.")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "`Номер телефона успешно удален`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema())
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь или номер телефона не найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "`Нет доступа к этому ресурсу`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @DeleteMapping("/{userId}/delete-phone/{phoneId}")
    public ResponseEntity<Void> deletePhoneByIdByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("phoneId") Long phoneId
    ) {
        userService.removePhoneByUserIdPhoneId(userId, phoneId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить все email пользователя.",
            description = """
                    `
                    Возвращает все email, связанные с указанным пользователем по его идентификатору.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список email успешно возвращен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmailDataDtoResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/show-emails/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<EmailDataDtoResponse> getAllEmailsByUserId(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getAllEmailsByUserId(id));
    }

    @Operation(
            summary = "Поиск пользователей.",
            description = """
                    `
                    Выполняет поиск пользователей по заданному запросу и возвращает результаты с возможностью пагинации.
                    Принимает параметры для поиска, включая дату рождения. Если заданная дата рождения больше, чем у найденных пользователей,
                    возвращается пустой список. Если дата не указана, поиск осуществляется по другим критериям.
                    `
                    """,
            parameters = {
                    @Parameter(name = "q", description = "Критерий поиска", required = true),
                    @Parameter(name = "page", description = "Номер страницы (начиная с 1)", example = "1"),
                    @Parameter(name = "size", description = "Количество элементов на странице", example = "10"),
                    @Parameter(name = "dateOfBirth", description = "Дата рождения для фильтрации", example = "01.01.1990")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Пользователи успешно найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<PageableResponse<UserdataDtoResponse>> search(
            @RequestParam("q") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(name = "dateOfBirth", required = false) @Valid @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateOfBirth
    ) {
        return ResponseEntity.ok(userService.search(query, PageRequest.of(page - 1, size), dateOfBirth));
    }

    @Operation(
            summary = "Получить пользователя по ID.",
            description = """
                    `
                    Возвращает информацию о пользователе по его идентификатору.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Пользователь успешно найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDataDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
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
    public ResponseEntity<UserDataDto> getUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            summary = "Получить всех пользователей.",
            description = """
                    `
                    Возвращает список всех пользователей с возможностью пагинации.
                    Можно указать номер страницы и количество пользователей на странице.
                    `
                    """,
            parameters = {
                    @Parameter(name = "page", description = "Номер страницы (начиная с 1)", example = "1"),
                    @Parameter(name = "size", description = "Количество пользователей на странице", example = "5")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Пользователи успешно найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/show-all")
    public ResponseEntity<PageableResponse<UserDataDto>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(userService.getAllUsers(PageRequest.of(page - 1, size)));
    }
}
