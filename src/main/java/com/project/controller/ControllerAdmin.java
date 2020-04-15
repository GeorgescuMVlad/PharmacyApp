package com.project.controller;

import com.project.SDProject.AlertBox;
import com.project.model.Drug;
import com.project.model.User;
import com.project.reports.ReportPdf;
import com.project.reports.ReportTxt;
import com.project.reports.Reports;
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
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Boolean.FALSE;

@Component
@FxmlView("admin.fxml")
public class ControllerAdmin {

    @Autowired
    private UserService userService;

    @Autowired
    private DrugService drugService;

    @FXML
    TableView usersTable;
    @FXML
    TableColumn idNameCol;
    @FXML
    TableColumn idPassCol;
    @FXML
    TableColumn idMailCol;
    @FXML
    TableColumn idMoneyCol;
    @FXML
    TextField userNameAdmin;
    @FXML
    TextField userMailAdmin;
    @FXML
    TextField userPassAdmin;
    @FXML
    TextField userMoneyAdmin;
    @FXML
    TableView drugsAdminTable;
    @FXML
    TableColumn idDrugName;
    @FXML
    TableColumn idDrugPrice;
    @FXML
    TableColumn idDrugType;
    @FXML
    TableColumn idDrugDesc;
    @FXML
    TextField drugNameId;
    @FXML
    TextField drugTypeAdmin;
    @FXML
    TextField drugPriceAdmin;
    @FXML
    TextField drugDescAdmin;
    @FXML
    TextField reportAdmin;


    List<User> users = new ArrayList<User>();
    public void showUsers(){
        users = userService.getAllUsers();
        usersTable.getItems().clear();
        ObservableList<User> usersObs = FXCollections.observableArrayList(users);

        for(int i=0; i<usersObs.size(); i++)
        {
            idNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            idPassCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            idMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
            idMoneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));
            usersTable.getItems().add(usersObs.get(i));
        }
    }

    public void saveUser()
    {
        String name = userNameAdmin.getText();
        String mail = userMailAdmin.getText();
        String pass = userPassAdmin.getText();
        String money = userMoneyAdmin.getText();
        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters.");
        }
        else if(Validators.validatePass(pass)==0){
            AlertBox.display("Wrong pass", "Pass must be at least 3 characters and at most 15");
        }
        else if(Validators.validateEmail(mail)==0){
            AlertBox.display("Wrong email", "Email must contain exactly 1 @ which is not first or last and must have at least 3 characters");
        }
        else if(Validators.validateNumberBox(money)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money");
        }
        else{
            User toSave = User.builder().mail(mail).password(pass).name(name).money(money).role(FALSE).build();
            userService.save(toSave);
            showUsers();
        }
        userNameAdmin.clear();
        userMailAdmin.clear();
        userPassAdmin.clear();
        userMoneyAdmin.clear();
    }

    public void editUser()
    {
        String name = userNameAdmin.getText();
        String mail = userMailAdmin.getText();
        String pass = userPassAdmin.getText();
        String money = userMoneyAdmin.getText();
        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validatePass(pass)==0){
            AlertBox.display("Wrong pass", "Pass must be at least 3 characters and at most 15");
        }
        else if(Validators.validateEmail(mail)==0){
            AlertBox.display("Wrong email", "Email must contain exactly 1 @ which is not first or last and must have at least 3 characters");
        }
        else if(Validators.validateNumberBox(money)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money");
        }
        else{
            User user = userService.edit(name, mail, pass, money);
            showUsers();
        }
        userNameAdmin.clear();
        userMailAdmin.clear();
        userPassAdmin.clear();
        userMoneyAdmin.clear();
    }

    public void deleteUser()
    {
        User user = (User) usersTable.getSelectionModel().getSelectedItem();
        userService.delete(user);
        showUsers();
    }

    //--------------------------------------------------------------------------------------------------------------

    List<Drug> drugs = new ArrayList<Drug>();

    public void showDrugs(){
        drugs = drugService.getAllDrugs();
        drugsAdminTable.getItems().clear();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(drugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            drugsAdminTable.getItems().add(drugsObs.get(i));
        }
    }

    public void saveDrug()
    {
        String name = drugNameId.getText();
        String type = drugTypeAdmin.getText();
        String price = drugPriceAdmin.getText();
        String desc = drugDescAdmin.getText();
        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validateName(type)==0){
            AlertBox.display("Wrong type", "Please insert a valid type name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validateNumberBox(price)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for drug price");
        }
        else if(Validators.validateName(desc)==0){
            AlertBox.display("Wrong description", "Please insert a valid description. Must have at least 3 characters, only letters");
        }
        else{
            Drug toSave = Drug.builder().name(name).type(type).price(price).description(desc).build();
            drugService.save(toSave);
            showDrugs();
        }
        drugNameId.clear();
        drugTypeAdmin.clear();
        drugPriceAdmin.clear();
        drugDescAdmin.clear();
    }

    public void editDrug()
    {
        String name = drugNameId.getText();
        String type = drugTypeAdmin.getText();
        String price = drugPriceAdmin.getText();
        String desc = drugDescAdmin.getText();
        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters.");
        }
        else if(Validators.validateName(type)==0){
            AlertBox.display("Wrong type", "Please insert a valid type name. Must have at least 3 characters.");
        }
        else if(Validators.validateNumberBox(price)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for drug price");
        }
        else if(Validators.validateName(desc)==0){
            AlertBox.display("Wrong description", "Please insert a valid description. Must have at least 3 characters");
        }
        else{
            Drug drug = drugService.edit(name, type, price, desc);
            showDrugs();
        }
        drugNameId.clear();
        drugTypeAdmin.clear();
        drugPriceAdmin.clear();
        drugDescAdmin.clear();
    }

    public void deleteDrug()
    {
        Drug drug = (Drug) drugsAdminTable.getSelectionModel().getSelectedItem();
        drugService.delete(drug);
        showDrugs();
    }

    public void generateReport(){
        users = userService.getAllUsers();
        Reports report = null;
        String rep = reportAdmin.getText();
        if(rep.equals("pdf")){
            report = new ReportPdf();
            report.createReport(users);
            AlertBox.display("Pdf", "Pdf generated successfully, check for it in the folder you saved it");
        }
        else if(rep.equals("txt")) {
            report = new ReportTxt();
            report.createReport(users);
            AlertBox.display("Txt", "Txt generated successfully, check for it in the folder you saved it");
        }
        else AlertBox.display("Reports", "Invalid format inserted. Try again!");
        reportAdmin.clear();
    }

}
