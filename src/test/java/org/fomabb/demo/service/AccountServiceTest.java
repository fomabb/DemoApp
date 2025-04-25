package org.fomabb.demo.service;

import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserServiceSecurity userServiceSecurity;

    private User sender;
    private User recipient;
    private Account senderAccount;
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
        senderAccount = Account.builder().id(1L).actualBalance(BigDecimal.valueOf(500)).build();
        recipientAccount = Account.builder().id(2L).actualBalance(BigDecimal.valueOf(100)).build();

        sender = User.builder().id(10L).account(senderAccount).build();
        recipient = User.builder().id(20L).account(recipientAccount).build();
    }

    @Test
    void performTransfer_successfulTransfer() {
        TransferDtoRequest request = new TransferDtoRequest(10L, 20L, BigDecimal.valueOf(200));

        when(userService.findUserById(10L)).thenReturn(sender);
        when(userService.findUserById(20L)).thenReturn(recipient);
        when(userServiceSecurity.getCurrentUser()).thenReturn(sender);

        accountService.performTransfer(request);

        assertEquals(BigDecimal.valueOf(300), senderAccount.getActualBalance());
        assertEquals(BigDecimal.valueOf(300), recipientAccount.getActualBalance());
    }

    @Test
    void performTransfer_notEnoughFunds_throwsException() {
        TransferDtoRequest request = new TransferDtoRequest(10L, 20L, BigDecimal.valueOf(600));

        when(userService.findUserById(10L)).thenReturn(sender);
        when(userService.findUserById(20L)).thenReturn(recipient);
        when(userServiceSecurity.getCurrentUser()).thenReturn(sender);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> accountService.performTransfer(request)
        );

        assertEquals("There are insufficient funds in the account", exception.getMessage());
    }

    @Test
    void performTransfer_wrongUser_throwsAccessDenied() {
        TransferDtoRequest request = new TransferDtoRequest(10L, 20L, BigDecimal.valueOf(100));
        User anotherUser = User.builder().id(99L).build();

        when(userService.findUserById(10L)).thenReturn(sender);
        when(userService.findUserById(20L)).thenReturn(recipient);
        when(userServiceSecurity.getCurrentUser()).thenReturn(anotherUser);

        assertThrows(
                AccessDeniedException.class,
                () -> accountService.performTransfer(request)
        );
    }
}
