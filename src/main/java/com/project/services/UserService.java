package com.project.services;

import com.project.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findById(int id);

    User findByUser(String name);

    User findByMail(String mail);

    User save(User user);

    List<User> getAllUsers();

    User edit(String name, String mail, String pass, String money);

    void delete(User user);
}
