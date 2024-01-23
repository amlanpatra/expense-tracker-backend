package org.amlan.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PaymentBlocks")
public class PaymentBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentBlockId;

    @ManyToOne
    private User payeeUser;

    @ManyToOne
    private User receiverUser;

    @ManyToOne
    @JsonIgnore
    private Group group;

    @ManyToOne
    private User creatorUser;

    //    @ManyToOne
//    private Transaction transaction;
    private Double paymentBlockAmount;
    private LocalDateTime paymentTime;
    private String paymentDescription;
    private String paymentType;

    public PaymentBlock(Long paymentBlockId, User payeeUser, User receiverUser, Group group, User creatorUser, Double paymentBlockAmount, LocalDateTime paymentTime, String paymentDescription, String paymentType) {
        this.paymentBlockId = paymentBlockId;
        this.payeeUser = payeeUser;
        this.receiverUser = receiverUser;
        this.group = group;
        this.creatorUser = creatorUser;
        this.paymentBlockAmount = ((Math.round(paymentBlockAmount * 100)) / (double) 100);
        this.paymentTime = paymentTime;
        this.paymentDescription = paymentDescription;
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return org.amlan.expensetracker.utilities.JsonMapper.asJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PaymentBlock) {
            PaymentBlock g = (PaymentBlock) o;
            return Objects.equals(g.getPaymentBlockId(), this.getPaymentBlockId());
        }
        return false;
    }
}
