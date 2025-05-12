package org.fomabb.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.Optional;

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
    private UserServiceSecurity userServiceSecurity;

    @Test
    void performTransfer_Success() {
        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = new BigDecimal("100");

        User currentUser = User.builder().id(senderId).build();
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);

        User senderUser = User.builder().id(senderId).build();
        Account senderAccount = Account.builder()
                .id(1L)
                .user(senderUser)
                .actualBalance(new BigDecimal("500"))
                .build();

        User recipientUser = User.builder().id(recipientId).build();
        Account recipientAccount = Account.builder()
                .id(2L)
                .user(recipientUser)
                .actualBalance(new BigDecimal("200"))
                .build();

        when(accountRepository.findByUserIdForUpdate(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(recipientId)).thenReturn(Optional.of(recipientAccount));

        TransferDtoRequest dto = TransferDtoRequest.builder()
                .transferFrom(senderId)
                .transferTo(recipientId)
                .transferAmount(amount)
                .build();

        accountService.performTransfer(dto);

        assertEquals(new BigDecimal("400"), senderAccount.getActualBalance());
        assertEquals(new BigDecimal("300"), recipientAccount.getActualBalance());
    }

    @Test
    void performTransfer_InsufficientFunds() {
        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = new BigDecimal("1000");

        User currentUser = User.builder().id(senderId).build();
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);

        User senderUser = User.builder().id(senderId).build();
        Account senderAccount = Account.builder()
                .id(1L)
                .user(senderUser)
                .actualBalance(new BigDecimal("500"))
                .build();

        when(accountRepository.findByUserIdForUpdate(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(recipientId)).thenReturn(Optional.of(new Account()));

        TransferDtoRequest dto = TransferDtoRequest.builder()
                .transferFrom(senderId)
                .transferTo(recipientId)
                .transferAmount(amount)
                .build();

        assertThrows(BusinessException.class, () -> accountService.performTransfer(dto));
    }

    @Test
    void performTransfer_InvalidUser() {
        Long senderId = 1L;
        Long recipientId = 2L;

        User currentUser = User.builder().id(999L).build(); // НЕ тот пользователь
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);

        TransferDtoRequest dto = TransferDtoRequest.builder()
                .transferFrom(senderId)
                .transferTo(recipientId)
                .transferAmount(BigDecimal.TEN)
                .build();

        assertThrows(AccessDeniedException.class, () -> accountService.performTransfer(dto));
    }

    @Test
    void performTransfer_SenderNotFound() {
        Long senderId = 1L;
        Long recipientId = 2L;

        User currentUser = User.builder().id(senderId).build();
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);

        when(accountRepository.findByUserIdForUpdate(senderId)).thenReturn(Optional.empty());

        TransferDtoRequest dto = TransferDtoRequest.builder()
                .transferFrom(senderId)
                .transferTo(recipientId)
                .transferAmount(BigDecimal.TEN)
                .build();

        assertThrows(EntityNotFoundException.class, () -> accountService.performTransfer(dto));
    }

    @Test
    void performTransfer_RecipientNotFound() {
        Long senderId = 1L;
        Long recipientId = 2L;

        User currentUser = User.builder().id(senderId).build();
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);

        Account senderAccount = Account.builder()
                .id(1L)
                .actualBalance(BigDecimal.TEN)
                .build();

        when(accountRepository.findByUserIdForUpdate(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(recipientId)).thenReturn(Optional.empty());

        TransferDtoRequest dto = TransferDtoRequest.builder()
                .transferFrom(senderId)
                .transferTo(recipientId)
                .transferAmount(BigDecimal.ONE)
                .build();

        assertThrows(EntityNotFoundException.class, () -> accountService.performTransfer(dto));
    }
}
