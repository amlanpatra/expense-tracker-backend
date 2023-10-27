package org.amlan.expensetracker.entity;

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
@Table(name = "Groups")
public class Group implements Comparable<Group> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    @ManyToOne
    private User admin;

    @ManyToMany(mappedBy = "groups")//, cascade = CascadeType.ALL)//, fetch = FetchType.EAGER)
//    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "group")
    private List<PaymentBlock> payments;

    private String groupName;
    private String groupDescription;

    @Override
    public String toString() {
        return org.amlan.expensetracker.utilities.JsonMapper.asJson(this);
    }

    @Override
    public int compareTo(Group group) {
        return (int) (this.getGroupId() - group.getGroupId());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Group) {
            Group g = (Group) o;
            return Objects.equals(g.getGroupId(), this.getGroupId());
        }
        return false;
    }

}
