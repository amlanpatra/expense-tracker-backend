package org.amlan.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    @ManyToOne
    private User admin;

    @ManyToMany(mappedBy = "groups")//, cascade = CascadeType.ALL)
//    @JsonManagedReference
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "paymentGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
    @JsonIgnore
    private List<Transaction> transactions;

    private String groupName;
    private String groupDescription;


//    @Override
//    public String toString() {
//        String transactionIds = transactions.stream().map(x -> x.getTransactionId().toString()).reduce((a, b) -> (a + " " + b)).orElse("Empty");
//        String userIds = users.stream().map(x -> x.getUserId().toString()).reduce((a, b) -> (a + " " + b)).orElse("Empty");
//
//        return "Group{" +
//                "groupId=" + groupId +
//                ", admin=" + admin +
//                ", users=[" + userIds +
//                "], transactions=[" + transactionIds +
//                "], groupName='" + groupName + '\'' +
//                ", groupDescription='" + groupDescription + '\'' +
//                '}';
//    }
}
