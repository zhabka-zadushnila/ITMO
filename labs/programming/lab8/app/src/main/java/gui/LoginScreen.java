package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dragon Collection Manager - Login");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Add Login and Register buttons
        Button loginBtn = new Button("Log in");
        Button registerBtn = new Button("Register");
        HBox hbBtn = new HBox(10); // A horizontal box to hold the buttons
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(registerBtn, loginBtn);
        grid.add(hbBtn, 1, 4);

        // Add a text field to display action results (e.g., "Login failed")
        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6, 2, 1);

        // --- Event Handlers for Buttons ---

        loginBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            // ** THIS IS WHERE YOU CONNECT YOUR LOGIC **
            // For now, we'll just print to the console and update the text.
            System.out.println("Attempting login for user: " + username);

            // TODO: Implement actual authentication logic.
            // You would typically have a network client or auth manager here.
            // boolean success = authManager.login(username, password);
            // if (success) {
            //     actiontarget.setFill(Color.GREEN);
            //     actiontarget.setText("Login successful!");
            //     // TODO: Close login window and open the main application window.
            // } else {
            //     actiontarget.setFill(Color.FIREBRICK);
            //     actiontarget.setText("Login failed. Please try again.");
            // }
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Login functionality not yet implemented.");
        });

        registerBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            // ** THIS IS WHERE YOU CONNECT YOUR LOGIC **
            System.out.println("Attempting to register user: " + username);

            // TODO: Implement actual registration logic.
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Registration functionality not yet implemented.");
        });


        // Create the scene and set it on the stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show the window
        primaryStage.show();
    }
}