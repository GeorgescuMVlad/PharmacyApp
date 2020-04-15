package com.project.SDProject;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.project.repository")
@EntityScan( basePackages = {"com.project.model"} )
@ComponentScan( basePackages = {"com.project"} )
public class SdProjectApplication {

	private Stage window;

	public static void main(String[] args) {

		System.setProperty("java.awt.headless", "false");
		Application.launch(JavaFXApplication.class, args);
	}

}
