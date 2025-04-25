package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.security.service.UserServiceSecurity;
import org.fomabb.demo.service.AccountService;
import org.fomabb.demo.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.fomabb.demo.util.Constant.ACCOUNT_WITH_ID_NOT_FOUND;
import static org.fomabb.demo.util.Constant.THERE_ARE_INSUFFICIENT_FUNDS_IN_THE_ACCOUNT;
import static org.fomabb.demo.util.Constant.USER_DOES_NOT_HAVE_PERMISSION;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final UserServiceSecurity userServiceSecurity;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void performTransfer(TransferDtoRequest dto) {
        User userSender = userService.findUserById(dto.getTransferFrom());
        User userRecipient = userService.findUserById(dto.getTransferTo());

        Long userIdValidate = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(userSender.getId(), userIdValidate)) {
            if (userSender.getAccount().getActualBalance().compareTo(dto.getTransferAmount()) > 0) {
                userSender.getAccount().setActualBalance(userSender.getAccount().getActualBalance().subtract(dto.getTransferAmount()));
                userRecipient.getAccount().setActualBalance(userRecipient.getAccount().getActualBalance().add(dto.getTransferAmount()));
            } else {
                log.warn("На аккаунте с id: {} недостаточно средств для перевода.", userSender.getAccount().getId());
                throw new BusinessException(THERE_ARE_INSUFFICIENT_FUNDS_IN_THE_ACCOUNT);
            }
        } else {
            throw new AccessDeniedException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }

    @Override
    public Account getAccountByUserId(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_WITH_ID_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void createAccountWithBalance(Account account) {
        accountRepository.save(account);
    }
}