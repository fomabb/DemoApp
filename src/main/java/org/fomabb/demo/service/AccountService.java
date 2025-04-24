package org.fomabb.demo.service;

import org.fomabb.demo.entity.Account;

public interface AccountService {
    Account getAccountByUserId(Long id);
}
