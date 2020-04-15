package com.project.services;


import com.project.model.Drug;
import com.project.model.User;
import com.project.repository.DrugRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Drug findById(int id){
        return drugRepository.findById(id).orElse(null);
    }

    @Override
    public Drug findByDrug(String name){
        return drugRepository.findByName(name);
    }

    @Override
    public Drug save(Drug drug){
        return drugRepository.save(drug);
    }
    @Override
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug edit(String name, String type, String price, String descr){
        Drug drug;
        drug = drugRepository.findByName(name);
        drug.setPrice(price);
        drug.setType(type);
        drug.setDescription(descr);
        drugRepository.save(drug);
        return drug;
    }

    @Override
    public void delete(Drug drug)
    {
        List<User> users = new ArrayList<User>();
        users.addAll(drug.getUsers());

        for(int i=0; i<users.size(); i++)
        {
            Set<Drug> drugss = new HashSet<Drug>();
            drugss = users.get(i).getDrugs();
            drugss.remove(drug);
            users.get(i).setDrugs(drugss);
            userRepository.save(users.get(i));
        }

        drugRepository.delete(drug);
    }
}
