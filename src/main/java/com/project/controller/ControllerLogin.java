package com.project.controller;

import com.project.SDProject.AlertBox;
import com.project.SDProject.JavaFXApplication;
import com.project.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
@FxmlView("login.fxml")
public class ControllerLogin {

    @FXML
    public Button loginButton;

    @FXML
    public TextField mail;

    @FXML
    public TextField password;

    @FXML
    public TextField role;

    @Autowired
    private UserService userService;

    public static UsernamePasswordAuthenticationToken authReq;
    public void login() throws IOException {

        if (mail.getText().equals("") || password.getText().equals("") || role.getText().equals(""))
            AlertBox.display("No input", "You forgot to write your mail/password/role");
        else if (!role.getText().equals("admin") && !role.getText().equals("user")) {
            AlertBox.display("Wrong role", "Insert admin for admin role or user for user/player role");
        } else {
            if (role.getText().equals("user")) {
                authReq = new UsernamePasswordAuthenticationToken(mail.getText(), password.getText());
                JavaFXApplication.changeScene("user.fxml");
            } else {
                JavaFXApplication.changeScene("admin.fxml");
            }
        }
    }

}
