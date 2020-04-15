package com.project.services;

import com.project.model.Drug;
import com.project.model.User;
import com.project.repository.DrugRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Override
    public User findById(int id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUser(String name){
        return userRepository.findByName(name);
    }

    @Override
    public User findByMail(String mail){
        return userRepository.findByMail(mail);
    }

    @Override
    public User save(User user){
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User edit(String name, String mail, String pass, String money){
        User user;
        user = userRepository.findByName(name);
        user.setMail(mail);
        user.setPassword(pass);
        user.setMoney(money);
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(User user)
    {
       // user.getDrugs().clear();

        List<Drug> drugs = new ArrayList<Drug>();
        drugs.addAll(user.getDrugs());

        for(int i=0; i<drugs.size(); i++)
        {
            Set<User> userss = new HashSet<User>();
            userss = drugs.get(i).getUsers();
            userss.remove(user);
            drugs.get(i).setUsers(userss);
            drugRepository.save(drugs.get(i));
        }

        userRepository.delete(user);

    }

}
