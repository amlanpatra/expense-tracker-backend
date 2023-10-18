package org.amlan.expensetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PaymentBlocks")
public class PaymentBlock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long paymentBlockId;
//    private User payeeUser;
//    private User receiverUser;
    private Double paymentBlockAmount;
}
