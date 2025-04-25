package org.fomabb.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fomabb.demo.dto.request.TransferDtoRequest;
import org.fomabb.demo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void performTransfer_shouldReturnAccepted_whenTransferIsValid() throws Exception {
        TransferDtoRequest dto = new TransferDtoRequest();
        dto.setTransferFrom(1L);
        dto.setTransferTo(2L);
        dto.setTransferAmount(new BigDecimal("30.00"));

        doNothing().when(accountService).performTransfer(any(TransferDtoRequest.class));

        mockMvc.perform(post("/api/v1/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        verify(accountService).performTransfer(any(TransferDtoRequest.class));
    }
}
