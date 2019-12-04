package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class LoginController {
    @FXML private AnchorPane loginAnchor;
    @FXML private JFXButton login_SignupButton;
    @FXML private JFXTextField loginUsername;
    @FXML private JFXButton loginButton;
    @FXML private JFXPasswordField loginPass;
    @FXML private JFXButton button;

    @FXML
    void initialize() {

        String loginTest=loginUsername.getText().trim();
        String loginPwd=loginPass.getText().trim();

        login_SignupButton.setOnAction(event -> {
            login_SignupButton.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));
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

        button.setOnAction(event -> {
            if(loginUsername.getText().isEmpty()){
                JFXSnackbar jfxSnackbar = new JFXSnackbar(loginAnchor);
                jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Provide Username"));
                return;
            }
            if(loginPass.getText().isEmpty()){
                JFXSnackbar jfxSnackbar = new JFXSnackbar(loginAnchor);
                jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Provide Password"));
                return;
            }
            loginUser();
        });


    }

    private void loginUser() {
        String loginTest=loginUsername.getText().trim();
        String loginPwd=loginPass.getText().trim();

        String query = "select * from signup where username = ? and password = ?";
        try {
            PreparedStatement ps = Objects.requireNonNull(DBHandler.connect()).prepareStatement(query);
            ps.setString(1, loginTest);
            ps.setString(2, loginPwd);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                button.getScene().getWindow().hide();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/home.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root=loader.getRoot();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                JFXSnackbar jfxSnackbar = new JFXSnackbar(loginAnchor);
                jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("No user found"));


            }

            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        clear();
    }


    private void clear(){
        loginUsername.setText("");
        loginPass.setText("");
    }
}
