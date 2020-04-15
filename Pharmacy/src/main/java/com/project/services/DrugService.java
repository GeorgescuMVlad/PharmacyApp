package com.project.services;


import com.project.model.Drug;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DrugService {

    Drug findById(int id);

    Drug findByDrug(String name);

    Drug save(Drug user);

    List<Drug> getAllDrugs();

    Drug edit(String name, String type, String price, String descr);

    void delete(Drug drug);
}
