package com.subra.payment.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode( onlyExplicitlyIncluded = true)
public class Transaction implements Comparable<Transaction> {
    @Override
    public int compareTo(Transaction o) {
        return this.getId().compareTo(o.getId());
    }

    @EqualsAndHashCode.Include
    private Integer id;
    private Integer accountId;
    private OperationType operationType;
    private Double amount;
    private LocalDateTime eventDate;
}
