package org.amlan.expensetracker.controller;

import org.amlan.expensetracker.entity.Group;
import org.amlan.expensetracker.entity.Transaction;
import org.amlan.expensetracker.entity.User;
import org.amlan.expensetracker.repository.GroupRepository;
import org.amlan.expensetracker.repository.UserRepository;
import org.amlan.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class Home {
    @Autowired
    GroupRepository groups;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository users;

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @PostMapping("/save")
    public String save() {
        Group g = new Group();
        User amlan = new User();
        amlan.setUserName("Amlan");
        User abhay = new User();
        abhay.setUserName("Abhay");

        g.setUsers(Arrays.asList(amlan, abhay));

        Transaction t1 = new Transaction();
        t1.setTransactionDescription("transaction 1");

        Transaction t2 = new Transaction();
        t2.setTransactionDescription("transaction 2");

        g.setTransactions(Arrays.asList(t1, t2));
        g.setGroupId(1001L);
        groups.save(g);


        Group g2 = new Group();
        User akash = new User();
        amlan.setUserName("Akash");
        User anil = new User();
        amlan.setUserName("anil");
        g2.setUsers(Arrays.asList(akash, anil, amlan));


        Transaction t3 = new Transaction();
        t3.setTransactionDescription("akash to anil");

        Transaction t4 = new Transaction();
        t4.setTransactionDescription("amlan to akash");
        groups.flush();
        groups.save(g2);
        groups.flush();

        return groups.findById(g.getGroupId()) + groups.findById(g2.getGroupId()).toString();
    }

    @PostMapping("/saveNew")
    public User saveUser(User u) {
        return userService.saveUser(u);
    }

    @PostMapping("/deleteUser/{userId}")
    public String saveUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(Long.valueOf(userId));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users.findAll();
    }

    @PostMapping("/createGroup/{userId}")
    public Group createGroup(@PathVariable("userId") String userId) {
        Group newGroup = null;
        User admin;

        Optional<User> tempAdmin = users.findById(Long.valueOf(userId));
        if (tempAdmin.isPresent()) {
            admin = tempAdmin.get();

            newGroup = new Group();
            newGroup.setAdmin(admin);
            List<User> currentGroupUsers = newGroup.getUsers();
            if (currentGroupUsers == null) {
                currentGroupUsers = Arrays.asList(admin);
            } else {
                currentGroupUsers.add(admin);
            }

            newGroup.setUsers(currentGroupUsers);
            admin.getGroups().add(newGroup);

            users.saveAndFlush(admin);
            groups.saveAndFlush(newGroup);
        }

        return newGroup;
    }


    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        return groups.findAll();
    }

    @PostMapping("/addUserToGroup")
    public String addUserToGroup(@RequestParam String adminUserId, @RequestParam String addedUserId) {

        return "userLoaded " + adminUserId + " " + addedUserId;
    }
}
