package org.amlan.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "groupId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    @JsonIgnore
    private List<Group> groups;


    @Override
    public int compareTo(User user) {
        return (int) (this.getUserId() - user.getUserId());
    }

    @Override
    public String toString() {
        return org.amlan.expensetracker.utilities.JsonMapper.asJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User g = (User) o;
            return Objects.equals(g.getUserId(), this.getUserId());
        }
        return false;
    }
}
