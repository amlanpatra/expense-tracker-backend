package org.amlan.expensetracker.service;

import org.amlan.expensetracker.entity.User;
import org.amlan.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository users;

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
}
