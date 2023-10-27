package org.amlan.expensetracker.controller;

import org.amlan.expensetracker.entity.Group;
import org.amlan.expensetracker.entity.User;
import org.amlan.expensetracker.service.GroupService;
import org.amlan.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;


    @PostMapping("/new")
    public User create(User u) {
        return userService.saveUser(u);
    }

    @PostMapping("/delete/{userId}")
    public String delete(@PathVariable("userId") String userId) {
        return userService.deleteUser(Long.valueOf(userId));
    }


    @GetMapping("/all") // get all users
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Working till above
     */


//    @PostMapping("/joinUsersToGroup") // add other users to group
//    public String addUserToGroup(@RequestParam("adminUserId") String adminUserId, @RequestParam("userIds") List<Long> userIds) {
//        return userIds.stream().map(x -> x.toString()).reduce((a, b) -> a.toString() + " added\n" + b.toString()).orElse("");
//    }
    @RequestMapping("/balanceTransaction")   // calculates the amount of money to return
    public Object balanceTransaction(@RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
        // userId of the user who opens the app
        // groupId of every group he is part of
        // All the payments that he need to do in that group
        return userService.calculateBalanceTransactionForUser(groupId, userId);
    }


    // TODO: Add feature to mark transaction for a single receiver use as settled
    @RequestMapping("/markTransactionAsSettled") // when an user marks a transaction as settled
    public Object markTransactionAsSettled(@RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
        // userId of logged in user
        // groupId of any of selected group
        // receiverUserId of the person whom current user needs to send money and has settled
        return userService.settleTransactionForUserInGroup(groupId, userId);
    }

    @GetMapping("/getGroupNames/{userId}")
    public List<Group> getGroupNames(@PathVariable("userId") Long userId) {
        return userService.getGroupNames(userId);
    }

}
