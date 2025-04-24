package org.fomabb.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccountByUserId(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: %s not found"
                        .formatted(id)));
    }
}
