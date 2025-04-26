package org.fomabb.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.entity.Account;
import org.fomabb.demo.entity.User;
import org.fomabb.demo.repository.AccountRepository;
import org.fomabb.demo.repository.UserRepository;
import org.fomabb.demo.security.enumeration.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "sender@example.com")
    void performTransfer_shouldReturnAccepted_whenTransferIsValid() throws Exception {

        LocalDate localDateSender = LocalDate.of(1985, 5, 10);
        Date dateOfBirthSender = Date.from(localDateSender.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User userSender = new User();
        userSender.setName("Sender");
        userSender.setPassword("pass123321312");
        userSender.setPrimaryEmail("sender@example.com");
        userSender.setRole(Role.ROLE_USER);
        userSender.setDateOfBirth(dateOfBirthSender);
        userSender.setEmailData(new HashSet<>());
        userSender.setPhoneData(new HashSet<>());

        LocalDate localDateRecipient = LocalDate.of(1990, 8, 25);
        Date dateOfBirthRecipient = Date.from(localDateRecipient.atStartOfDay(ZoneId.systemDefault()).toInstant());

        User userRecipient = new User();
        userRecipient.setName("Recipient");
        userRecipient.setPassword("pass3213123123");
        userRecipient.setPrimaryEmail("recipient@example.com");
        userRecipient.setRole(Role.ROLE_USER);
        userRecipient.setDateOfBirth(dateOfBirthRecipient);
        userRecipient.setEmailData(new HashSet<>());
        userRecipient.setPhoneData(new HashSet<>());

        Account senderAccount = new Account();
        senderAccount.setUser(userSender);
        senderAccount.setBalance(new BigDecimal("1000.00"));
        senderAccount.setActualBalance(new BigDecimal("1000.00"));

        Account recipientAccount = new Account();
        recipientAccount.setUser(userRecipient);
        recipientAccount.setBalance(new BigDecimal("500.00"));
        recipientAccount.setActualBalance(new BigDecimal("500.00"));

        userRepository.save(userSender);
        userRepository.save(userRecipient);
        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        TransferDtoRequest transferDtoRequest = new TransferDtoRequest();
        transferDtoRequest.setTransferFrom(userSender.getId());
        transferDtoRequest.setTransferTo(userRecipient.getId());
        transferDtoRequest.setTransferAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/api/v1/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferDtoRequest)))
                .andExpect(status().isAccepted());

        Account updatedSenderAccount = accountRepository.findById(senderAccount.getId()).orElseThrow();
        Account updatedRecipientAccount = accountRepository.findById(recipientAccount.getId()).orElseThrow();

        assertThat(updatedSenderAccount.getActualBalance()).isEqualByComparingTo("900.00");
        assertThat(updatedRecipientAccount.getActualBalance()).isEqualByComparingTo("600.00");
    }
}
