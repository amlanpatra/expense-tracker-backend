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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository users;

    @Autowired
    GroupRepository groups;

    @Autowired
    PaymentBlockRepository paymentBlocks;

    public User saveUser(User u) {
        User newUser = users.saveAndFlush(u);
        return newUser;
    }

    public String deleteUser(Long userId) {
        if (users.existsById(userId)) {
            users.deleteById(userId);
            return "User deleted with id : " + userId;
        }
        return "user doesn't exist";
    }

    public Group createGroup(Long userId, String groupName, String groupDescription) {
        Group newGroup = null;
        User admin;

        Optional<User> tempAdmin = users.findById(userId);
        if (tempAdmin.isPresent()) {
            admin = tempAdmin.get();

            newGroup = new Group();
            newGroup.setAdmin(admin);
            newGroup.setUsers(Arrays.asList(admin));

            newGroup.setGroupName(groupName);
            newGroup.setGroupDescription(groupDescription);


//            newGroup = groups.saveAndFlush(newGroup);

            admin.getGroups().add(newGroup);
            users.saveAndFlush(admin);

//            newGroup = admin.getGroups().get(adminGroups.size());   // since length will increase

//            groups.saveAndFlush(newGroup);
            return admin.getGroups().get(admin.getGroups().size() - 1);
        }

        return null;
    }

    public List<User> getAllUsers() {
        return users.findAll();
    }

    public List<Group> getGroupNames(Long userId) {

        if (users.existsById(userId)) {
            User currentUser = users.findById(userId).get();
            return currentUser.getGroups();
        }
        return null;
    }


    public List<PaymentBlock> calculateBalanceTransactionForUser(Long groupId, Long userId) {

        List<PaymentBlock> returnList = new ArrayList<>();
        try {
            Group currentGroup = groups.findById(groupId).get();
            User currentUser = users.findById(userId).get();
            List<User> groupUsers = currentGroup.getUsers();
            List<PaymentBlock> groupPayments = currentGroup.getPayments();
            Map<User, Double> returnBalance = new HashMap<>();

            if (groupUsers.contains(currentUser)) {
                for (PaymentBlock pb : groupPayments) {
                    if (pb.getReceiverUser().equals(currentUser)) {
                        returnBalance.put(pb.getPayeeUser(), returnBalance.getOrDefault(pb.getPayeeUser(), 0.0) + pb.getPaymentBlockAmount());
                    } else if (pb.getPayeeUser().equals(currentUser)) {
                        returnBalance.put(pb.getReceiverUser(), returnBalance.getOrDefault(pb.getReceiverUser(), 0.0) - pb.getPaymentBlockAmount());
                    }
                }

                for (Map.Entry<User, Double> it : returnBalance.entrySet()) {
                    if (Math.round(it.getValue() * 100) / (double) 100 == 0) continue;
                    PaymentBlock pb = null;
                    if (it.getValue() > 0) {
                        pb = new PaymentBlock(null, currentUser, it.getKey(), currentGroup, currentUser, it.getValue(), LocalDateTime.now(), "Balancing payment", CONSTANT.PAYMENT_TYPE.BALANCING.getValue());
                    } else {
                        pb = new PaymentBlock(null, it.getKey(), currentUser, currentGroup, currentUser, (-1 * it.getValue()), LocalDateTime.now(), "Balancing payment", CONSTANT.PAYMENT_TYPE.BALANCING.getValue());
                    }
                    returnList.add(pb);
                }

            } else {
                System.out.println("User not present in Group");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }


    public Object settleTransactionForUserInGroup(Long groupId, Long userId, List<Long> forUserIds) {
        List<PaymentBlock> balancedPayments = calculateBalanceTransactionForUser(groupId, userId);
        List<PaymentBlock> settlePayments = balancedPayments.stream().filter(x -> forUserIds.contains(x.getReceiverUser().getUserId()) || forUserIds.contains(x.getPayeeUser().getUserId())).collect(Collectors.toList());

        paymentBlocks.saveAllAndFlush(settlePayments);
        return groups.findById(groupId).get().getPayments();
    }
}
