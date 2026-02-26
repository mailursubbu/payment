package com.subra.payment.model;

import com.subra.payment.error.ApplicationException;
import lombok.Getter;

/**
 * Amount multiplier associated with each operation type to store the amount with
 * corresponding -ve or +ve sign
 */
@Getter
public enum OperationType {
    NORMAL_PURCHASE (1, "Normal Purchase", -1),
    PURCHASE_WITH_INSTALLMENTS (2, "Purchase with installments", -1),
    WITHDRAWAL (3, "Withdrawal", -1),
    CREDIT_VOUCHER (4, "Credit Voucher", 1);

    private final int id;
    private final String description;
    private final int amountMultiplier;

    OperationType(int id, String description, int amountMultiplier) {
        this.id = id;
        this.description = description;
        this.amountMultiplier = amountMultiplier;
    }

    /**
     * Customer input is a numaric value for operation type. Hence it needs to be converted to ENUM
     * using this method
     * @param id
     * @return
     */
    public static OperationType fromId(int id) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.id == id) {
                return operationType;
            }
        }
        throw new ApplicationException("OperationType id " + id + " not found");
    }
}
