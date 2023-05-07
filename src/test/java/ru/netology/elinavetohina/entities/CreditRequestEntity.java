package ru.netology.elinavetohina.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class CreditRequestEntity {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}
