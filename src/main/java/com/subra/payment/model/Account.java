package com.subra.payment.model;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode( onlyExplicitlyIncluded = true)
public class Account implements Comparable<Account> {
    @Override
    public int compareTo(Account o) {
        return this.getId().compareTo(o.getId());
    }

    //Using only ID for Equals and Hashcode
    @EqualsAndHashCode.Include
    private Integer id;
    private String documentId;
    private Double balance;
    private Double accountLimit;

}
