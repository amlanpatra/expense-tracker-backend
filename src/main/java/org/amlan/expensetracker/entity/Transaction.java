package org.amlan.expensetracker.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "Transactions")
//public class Transaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long transactionId;
//
//    //    @ElementCollection
//    @ManyToOne
////    @JoinColumn(name = "Group ID")
//    private Group paymentGroup;
//
//    @OneToMany(mappedBy = "transaction")
//    private List<PaymentBlock> singularPayments;
//
//    private String transactionDescription;
//    private LocalDate transactionDate;
//}
