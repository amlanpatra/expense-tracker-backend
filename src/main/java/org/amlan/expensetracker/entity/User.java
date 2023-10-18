package org.amlan.expensetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "groupId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<Group> groups;


    @Override
    public String toString() {
        String groupIds = groups.stream().map(x -> x.getGroupId().toString()).reduce((a, b) -> (a + " " + b)).orElse("Empty");
        return "User{" + userId +
                ", '" + userName + '\'' +
                ", groups=[" + groupIds +
                "]}";
    }
}
