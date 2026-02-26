package com.subra.payment.model;

import lombok.*;

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
}
