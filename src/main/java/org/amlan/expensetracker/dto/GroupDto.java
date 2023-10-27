package org.amlan.expensetracker.dto;

import org.amlan.expensetracker.entity.PaymentBlock;
import org.amlan.expensetracker.entity.User;

import java.util.List;

public class GroupDto {

    private Long groupId;
    private User admin;
    private List<User> users;
    private List<PaymentBlock> payments;
    private String groupName;
    private String groupDescription;
}
