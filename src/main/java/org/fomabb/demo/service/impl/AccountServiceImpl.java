package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.dto.response.AccountBalanceDataDtoResponse;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.AccountService;
import org.fomabb.demo.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.fomabb.demo.util.Constant.ACCOUNT_WITH_ID_NOT_FOUND;
import static org.fomabb.demo.util.Constant.THERE_ARE_INSUFFICIENT_FUNDS_IN_THE_ACCOUNT;
import static org.fomabb.demo.util.Constant.USER_DOES_NOT_HAVE_PERMISSION;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserServiceSecurity userServiceSecurity;
    private final UserService userService;

    @Override
    @Transactional
    public void performTransfer(TransferDtoRequest dto) {
        Long currentUserId = userServiceSecurity.getCurrentUser().getId();

        if (!dto.getTransferFrom().equals(currentUserId)) {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }

        Long fromUserId = dto.getTransferFrom();
        Long toUserId = dto.getTransferTo();
        BigDecimal amount = dto.getTransferAmount();

        Long firstUserId = fromUserId.compareTo(toUserId) < 0 ? fromUserId : toUserId;
        Long secondUserId = fromUserId.compareTo(toUserId) < 0 ? toUserId : fromUserId;

        Account firstAccount = accountRepository.findByUserIdForUpdate(firstUserId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_WITH_ID_NOT_FOUND.formatted(firstUserId)));

        Account secondAccount = accountRepository.findByUserIdForUpdate(secondUserId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_WITH_ID_NOT_FOUND.formatted(secondUserId)));

        Account senderAccount = fromUserId.equals(firstAccount.getUser().getId()) ? firstAccount : secondAccount;
        Account recipientAccount = fromUserId.equals(firstAccount.getUser().getId()) ? secondAccount : firstAccount;


        if (senderAccount.getActualBalance().compareTo(amount) < 0) {
            log.warn("Недостаточно средств на счете отправителя ID: {}", senderAccount.getId());
            throw new BusinessException(THERE_ARE_INSUFFICIENT_FUNDS_IN_THE_ACCOUNT);
        }

        senderAccount.setActualBalance(senderAccount.getActualBalance().subtract(amount));
        recipientAccount.setActualBalance(recipientAccount.getActualBalance().add(amount));

        log.info("Перевод {}₽ от пользователя {} пользователю {} успешно выполнен.",
                amount, fromUserId, toUserId);
    }

    @Override
    @Transactional
    public void createAccountWithBalance(Account account) {
        accountRepository.save(account);
    }

    @Override
    public AccountBalanceDataDtoResponse getBalanceByUserId(Long id) {
        Long currentUserId = userServiceSecurity.getCurrentUser().getId();
        User userExist = userService.findUserById(id);
        if (!userExist.getId().equals(currentUserId)) {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
        return accountRepository.findAccountByUserId(id).map(account -> AccountBalanceDataDtoResponse.builder()
                .userId(account.getUser().getId())
                .accountId(account.getId())
                .deposit(account.getBalance())
                .actualBalance(account.getActualBalance())
                .build()).orElseThrow(() -> new EntityNotFoundException(
                ACCOUNT_WITH_ID_NOT_FOUND.formatted(id)
        ));
    }

}