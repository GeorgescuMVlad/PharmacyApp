package com.project.controller;

import com.project.SDProject.AlertBox;
import com.project.model.Drug;
import com.project.model.User;
import com.project.services.DrugService;
import com.project.services.UserService;
import com.project.validators.Validators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@FxmlView("user.fxml")
public class ControllerUser {

    @Autowired
    private DrugService drugService;

    @Autowired
    private UserService userService;

    @FXML
    TableView drugsUserTable;
    @FXML
    TableColumn idDrugName;
    @FXML
    TableColumn idDrugPrice;
    @FXML
    TableColumn idDrugType;
    @FXML
    TableColumn idDrugDesc;
    @FXML
    TextField searchDrug;
    @FXML
    TextField priceMin;
    @FXML
    TextField priceMax;

    List<Drug> drugs = new ArrayList<Drug>();

    public void showDrugs(){
        drugs = drugService.getAllDrugs();
        drugsUserTable.getItems().clear();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(drugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }

    List<User> users = new ArrayList<User>();
    static Set<User> userss = new HashSet<>();
    static Set<Drug> drugss = new HashSet<Drug>();
    public void buyItems()
    {
        Drug drug = (Drug) drugsUserTable.getSelectionModel().getSelectedItem();
        users = userService.getAllUsers();
        String userConnectedMail;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userConnectedMail = ControllerLogin.authReq.getPrincipal().toString();
        User user = new User();

        for(int i=0; i<users.size(); i++)
        {
            if(users.get(i).getMail().equals(userConnectedMail))
                user = users.get(i);
        }

        int userMoney = Integer.parseInt(user.getMoney());
        int drugPrice = Integer.parseInt(drug.getPrice());

        if(userMoney - drugPrice >= 0)
        {
           // drugss.addAll(user.getDrugs());
            //userss.addAll(drug.getUsers());
            drugss=user.getDrugs();  //cu astea imi scot userii si drugs din tabela de many to many
            userss=drug.getUsers();

            userMoney -= drugPrice;
            user.setMoney(Integer.toString(userMoney));
             userss.add(user);
             drugss.add(drug);
            user.setDrugs(drugss);
            drug.setUsers(userss);
            userService.save(user);
            drugService.save(drug);
            AlertBox.display("Command registered!", "User " + user.getName() + " successfully performed the command");
        }
        else {AlertBox.display("Cannot perform the command", "User " + user.getName() + " does not have enough money");}
    }

    public List<Drug> searchDrugs()
    {
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        String searchText = searchDrug.getText();
        int ok = 0;

        drugs = drugService.getAllDrugs();  //asta e lista cu toate medicamentele existente
        for(int i=0; i<drugs.size(); i++)
        {
            if(drugs.get(i).getName().equals(searchText) || drugs.get(i).getType().equals(searchText) || drugs.get(i).getPrice().equals(searchText)  || drugs.get(i).getDescription().equals(searchText)   )
            {
                searchedDrugs.add(drugs.get(i));
                ok = 1;
            }
        }
        if(ok == 0)
            AlertBox.display("Product not found", "Inexistent product/type/price/description! " );

        searchDrug.clear();
        return searchedDrugs;
    }

    public void showDrugsSearched()
    {
        drugsUserTable.getItems().clear();
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        searchedDrugs = searchDrugs();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(searchedDrugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }

    public List<Drug> filteredPriceDrugs()
    {
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        String priceMini = priceMin.getText();
        String priceMaxi = priceMax.getText();
        drugs = drugService.getAllDrugs();  //asta e lista cu toate medicamentele existente
        if(Validators.validateNumberBox(priceMini)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for the minimum sum");
        }
        else if(Validators.validateNumberBox(priceMaxi)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for the maximum sum");
        }
        else{
            int mini = Integer.parseInt(priceMini);
            int maxi = Integer.parseInt(priceMaxi);
            if(mini>maxi)
            {
                AlertBox.display("Wrong money", "Minimum sum can not be greater than maximum sum");
            }
            else{
                for(int i=0; i<drugs.size(); i++)
                {
                    int drugPrice = Integer.parseInt(drugs.get(i).getPrice());

                    if(drugPrice >= mini && drugPrice<=maxi )
                    {
                        searchedDrugs.add(drugs.get(i));
                    }
                }
            }
        }
        priceMin.clear();
        priceMax.clear();
        return searchedDrugs;
    }

    public void showFilteredDrugs()
    {
        drugsUserTable.getItems().clear();
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        searchedDrugs = filteredPriceDrugs();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(searchedDrugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }

}
