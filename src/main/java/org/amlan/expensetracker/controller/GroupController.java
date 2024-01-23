package org.amlan.expensetracker.controller;

import org.amlan.expensetracker.entity.Group;
import org.amlan.expensetracker.entity.PaymentBlock;
import org.amlan.expensetracker.repository.GroupRepository;
import org.amlan.expensetracker.repository.UserRepository;
import org.amlan.expensetracker.service.GroupService;
import org.amlan.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/group")
public class GroupController {
    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groups;

    @Autowired
    UserRepository users;

    @GetMapping("/all")
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }


    @PostMapping("/new")
    public Group createGroup(@RequestParam("userId") Long creatorUserId, @RequestParam("name") String groupName, @RequestParam("description") String groupDescription) {
        return userService.createGroup(creatorUserId, groupName, groupDescription);
    }

    @DeleteMapping("/delete/{groupId}")
    public String deleteGroup(@PathVariable("groupId") Long groupId) {
        if (groups.existsById(groupId)) {
            return groupService.deleteGroup(groupId);
        } else return "Group with id " + groupId + " does not exist";
    }

    /**
     * @param groupId : Long
     * @return params :
     * groupName, groupDescription, userIds of users in group, list of transactions
     */
    @GetMapping("/get")
    public Group getGroup(@RequestParam("groupId") Long groupId) {
        return groupService.getGroup(groupId);
    }


    @RequestMapping("/addTransaction")   // add a single transaction to group
    public Object addTransaction(@RequestParam("groupId") Long groupId, @RequestParam("creatorUserId") Long creatorUserId, @RequestBody List<PaymentBlock> payments) {
        // the userID is of the user who makes the request, so any user can make any other users request
        // the group id in which the transaction is being done
        // list of all paymentBlocks
//        TODO : If it returns null, it means user is not in group or group does not exist
//        In that case return a bad request
        return groupService.addTransaction(groupId, creatorUserId, payments);
//        String s = payments.stream().map(x -> x.toString()).reduce("", (a, b) -> a + " " + b);
//        return "working " + s;
    }

    @PostMapping("/addUsers")
    public boolean addUserToGroup(@RequestParam("groupId") Long groupId, @RequestParam("userIds") List<Long> userIds) {
        if (groups.existsById(groupId)) {
            return groupService.addUsersToGroup(groupId, userIds);
        }
        return false;
//        return "Group with id " + groupId + " does not exists";
    }
}
