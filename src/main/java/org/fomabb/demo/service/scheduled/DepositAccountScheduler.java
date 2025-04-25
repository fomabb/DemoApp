package org.fomabb.demo.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.repository.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepositAccountScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(cron = "${recalculate.all.deposit.account.balance}")
    public void recalculateAllDepositAccountsBalance() {
        List<Account> allAccounts = accountRepository.findAll();

        allAccounts.forEach(account -> {
            BigDecimal initialDeposit = account.getBalance();
            BigDecimal currentBalance = account.getActualBalance();

            BigDecimal maxAllowedBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));

            BigDecimal newBalance = currentBalance.multiply(BigDecimal.valueOf(1.10));

            if (newBalance.compareTo(maxAllowedBalance) > 0) {
                newBalance = maxAllowedBalance;
            }

            account.setActualBalance(newBalance);
            accountRepository.save(account);
        });
    }
}
