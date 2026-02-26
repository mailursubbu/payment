package com.subra.payment.dto;


import jakarta.annotation.Nullable;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AccountDto {
    @Nullable
    private Integer account_id;
    @NonNull
    private String document_number;
}
