package sample.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestController {

    @FXML private AnchorPane RequestAnchor;
    @FXML private JFXTextField request_name;
    @FXML private JFXTextField request_age;
    @FXML private JFXComboBox<String> request_gender;
    @FXML private JFXTextField request_phone;
    @FXML private JFXTextField request_email;
    @FXML private JFXTextField request_qty;
    @FXML private JFXTextField request_address;
    @FXML private JFXDatePicker request_date;
    @FXML private JFXComboBox<String> request_blood;
    @FXML private JFXButton button;
    @FXML private JFXButton golist_button;
    @FXML private JFXButton gohome_button;

    private String RequestDate="";
    @FXML
    void initialize() {
        golist_button.setOnAction(event -> {
            golist_button.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/requestbloodlist.fxml"));
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
    gohome_button.setOnAction(event -> {
        gohome_button.getScene().getWindow().hide();
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

    });
    button.setOnAction(event -> {

        RequestUser();
    });

    request_date.setOnAction(event -> {
        setRequestDate();
    });

        request_gender.getItems().add("Male");
        request_gender.getItems().add("Female");
        request_blood.getItems().add("A+");
        request_blood.getItems().add("A-");
        request_blood.getItems().add("B+");
        request_blood.getItems().add("B-");
        request_blood.getItems().add("AB+");
        request_blood.getItems().add("AB-");
        request_blood.getItems().add("O+");
        request_blood.getItems().add("O-");

    }

    private void setRequestDate() {

        RequestDate=request_date.getValue().toString();
    }

    private void RequestUser() {

        String name = request_name.getText().trim();
        String gender = request_gender.getSelectionModel().getSelectedItem();
        String age = request_age.getText().trim();
        String phone=request_phone.getText().trim();
        String email=request_email.getText().trim();
        String address=request_address.getText().trim();
        String blood = request_blood.getSelectionModel().getSelectedItem();
        String qty =request_qty.getText().trim();
        String PresentDate=RequestDate;
        if (name.equals("") || age.equals("") || gender.equals("") || blood.equals("") || phone.equals("") ||
                 PresentDate.equals("") || qty.equals("") || email.equals("") || address.equals(""))
        {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(RequestAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Select all fields"));
            return;
        }
        String query = "insert into requests(name,age,gender,blood,phone,presentdate,qtyblood,address,email) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, blood);
            ps.setString(5, phone);
            ps.setString(6, PresentDate);
            ps.setString(7, qty);
            ps.setString(8, address);
            ps.setString(9, email);
            ps.executeUpdate();
            ps.close();
            JFXSnackbar jfxSnackbar = new JFXSnackbar(RequestAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Request Added"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }

    private void clear() {

        request_name.setText("");
        request_age.setText("");
        request_gender.getSelectionModel().clearSelection();
        request_blood.getSelectionModel().clearSelection();
        request_phone.setText("");
        request_date.getEditor().clear();
        request_qty.setText("");
        request_email.setText("");
        request_address.setText("");
    }
}
