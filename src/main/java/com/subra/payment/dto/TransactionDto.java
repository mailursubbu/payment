package com.subra.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Integer transaction_id;
    private Integer account_id;
    private Integer operation_type_id;
    private Double amount;
    private LocalDateTime eventDate;
}
