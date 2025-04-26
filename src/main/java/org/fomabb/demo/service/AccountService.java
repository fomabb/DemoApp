package org.fomabb.demo.service;

import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.dto.response.AccountBalanceDataDtoResponse;
import org.fomabb.demo.entity.Account;

public interface AccountService {

    void performTransfer(TransferDtoRequest dto);

    Account getAccountByUserId(Long id);

    void createAccountWithBalance(Account account);

    AccountBalanceDataDtoResponse getBalanceByUserId(Long id);
}