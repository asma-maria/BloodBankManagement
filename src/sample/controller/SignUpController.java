package sample.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController {
    @FXML private AnchorPane signUpAnchor;
    @FXML private JFXTextField signupFirstName;
    @FXML private JFXTextField signupLastName;
    @FXML private JFXTextField signUpUserName;
    @FXML private JFXComboBox<String> signupGender;
    @FXML private JFXTextField SignUpBlood;
    @FXML private JFXTextField signUpLocation;
    @FXML private JFXButton SignUpButton;
    @FXML private JFXPasswordField signUpPassword;

    @FXML private JFXButton prevbtn;

    @FXML
    void initialize() {
        signupGender.getItems().add("Male");
        signupGender.getItems().add("Female");

        prevbtn.setOnAction(event -> {
            prevbtn.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login3.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        SignUpButton.setOnAction(event -> {
            signUpUser();
        });

    }

    private void signUpUser() {
        String firstName = signupFirstName.getText().trim();
        String lastName = signupLastName.getText().trim();
        String userName = signUpUserName.getText().trim();
        String gender = signupGender.getSelectionModel().getSelectedItem();
        String bloodType = SignUpBlood.getText().trim();
        String location = signUpLocation.getText().trim();
        String password = signUpPassword.getText().trim();

        if(firstName.equals("") || lastName.equals("") || userName.equals("") || gender.equals("") || bloodType.equals("") || location.equals("") || password.equals("")){
            JFXSnackbar jfxSnackbar = new JFXSnackbar(signUpAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Select all fields"));
            return;
        }

        String query = "insert into signup(firstname, lastname, password, gender, bloodgroup, location, username) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, bloodType);
            ps.setString(6, location);
            ps.setString(7, userName);
            ps.executeUpdate();
            ps.close();


            JFXSnackbar jfxSnackbar = new JFXSnackbar(signUpAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("User Added"));

        }catch (SQLException e){
            e.printStackTrace();
        }

        clear();

    }

    private void clear(){
        signupFirstName.setText("");
        signupLastName.setText("");
        signUpLocation.setText("");
        signUpUserName.setText("");
        signUpPassword.setText("");
        SignUpBlood.setText("");
        signUpLocation.setText("");
        signupGender.getSelectionModel().clearSelection();
    }
}
