package ru.netology.elinavetohina.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentEntity {
    private String id;
    private int amount;
    private String created;
    private String status;
    private String transaction_id;
}
