package org.amlan.expensetracker.service;

import org.amlan.expensetracker.entity.Group;
import org.amlan.expensetracker.entity.PaymentBlock;
import org.amlan.expensetracker.entity.User;
import org.amlan.expensetracker.repository.GroupRepository;
import org.amlan.expensetracker.repository.PaymentBlockRepository;
import org.amlan.expensetracker.repository.UserRepository;
import org.amlan.expensetracker.utilities.CONSTANT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    GroupRepository groups;

    @Autowired
    UserRepository users;

    @Autowired
    PaymentBlockRepository paymentBlocks;

    public List<Group> getAllGroups() {
        return groups.findAll();
    }

    public String deleteGroup(Long groupId) {
        Group group = groups.findById(groupId).get();
        List<User> groupUsers = group.getUsers();
        groupUsers.forEach(x -> {
            x.getGroups().remove(group);
        });

        users.saveAll(groupUsers);
        List<PaymentBlock> groupPaymentBlocks = group.getPayments().stream().collect(Collectors.toList());
        paymentBlocks.deleteAll(groupPaymentBlocks);
        groups.delete(group);

        return "Group with id : " + groupId + " successfully deleted";
    }

    public Group getGroup(Long groupId) {
        Optional<Group> g = groups.findById(groupId);
        return g.orElse(null);
    }

    public boolean addUsersToGroup(Long groupId, List<Long> userIds) {
        try {
            List<User> addedUsers = users.findAllById(userIds);
            Group currentGroup = groups.findById(groupId).get();

            addedUsers.forEach(x -> {
                if (x.getGroups() == null) {
                    x.setGroups(new ArrayList<>());
                }
                if (!x.getGroups().contains(currentGroup)) {
                    x.getGroups().add(currentGroup);
                }
            });

//        currentGroup.getUsers().addAll(addedUsers);

            users.saveAll(addedUsers);
//        groups.saveAndFlush(currentGroup);

            return true;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return false;
        }
    }

    public boolean removeUserFromGroup(Long groupId, List<Long> userIds) {
        return false;
    }

    public Object addTransaction(Long groupId, Long creatorUserId, List<PaymentBlock> payments) {

        try {
            Group currentGroup = groups.findById(groupId).get();
            User creatorUser = users.findById(creatorUserId).get();
            if (!currentGroup.getUsers().contains(creatorUser)) {
                System.out.println("Creator user outside group;");
                return null;
            }
            payments.forEach(x -> {
                if (currentGroup.getUsers().contains(x.getPayeeUser()) && currentGroup.getUsers().contains(x.getReceiverUser())) {
                    x.setCreatorUser(creatorUser);
                    x.setPaymentTime(LocalDateTime.now());
                    x.setPaymentType(CONSTANT.PAYMENT_TYPE.PAYING.getValue());
                } else {
                    System.out.println("\n\n\n\nUsers outside group\n\n\n\n");
                }
            });


            // prefilled from frontend
//            p.setPayeeUser(users.findById(13L).get());
//            p.setReceiverUser(users.findById(14L).get());
//            p.setPaymentBlockAmount(300.20);
//            p.setPaymentDescription("Test payment 1");


            addPaymentBlocksToGroup(groupId, payments);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return groups.findById(groupId);

    }


    public boolean addPaymentBlocksToGroup(Long groupId, List<PaymentBlock> payments) {
        try {
            Group currentGroup = groups.findById(groupId).get();
            payments.forEach(x -> x.setGroup(currentGroup));
            paymentBlocks.saveAll(payments);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
