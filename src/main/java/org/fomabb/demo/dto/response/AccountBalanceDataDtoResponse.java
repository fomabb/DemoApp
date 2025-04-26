package org.fomabb.demo.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountBalanceDataDtoResponse {

    private Long accountId;

    private Long userId;

    private BigDecimal deposit;

    private BigDecimal actualBalance;
}
