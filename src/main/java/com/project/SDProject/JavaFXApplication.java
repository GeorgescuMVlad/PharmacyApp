package com.project.SDProject;

import com.project.controller.ControllerAdmin;
import com.project.controller.ControllerLogin;
import com.project.controller.ControllerUser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;

public class JavaFXApplication extends Application {

    private static ConfigurableApplicationContext applicationContext;
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(SdProjectApplication.class)
                .run(args);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(ControllerLogin.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {

        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        if(fxml.equals("user.fxml"))
        {
            Parent root = fxWeaver.loadView(ControllerUser.class);
            Stage stage = new Stage();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            stage.show();
        }
        else if(fxml.equals("admin.fxml"))
        {
            Parent root = fxWeaver.loadView(ControllerAdmin.class);
            Stage stage = new Stage();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            stage.show();
        }

    }
}
