package org.amlan.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.amlan.expensetracker.entity.PaymentBlock;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBlockDto {
    private Long paymentBlockId;
    private Long creatorUserId;
    private Long payeeUserId;
    private Long receiverUserId;
    private Double paymentBlockAmount;
    private LocalDateTime paymentTime;
    private String paymentDescription;

}
