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

public class DonorController {
    @FXML private AnchorPane donorAnchor;
    @FXML private JFXTextField donorname;
    @FXML private JFXTextField donorage;
    @FXML private JFXTextField donoremail;
    @FXML private JFXButton godonor_button;

    @FXML private JFXTextField donorphone;
    @FXML private JFXDatePicker donorlastdate;
    @FXML private JFXTextField donorqty;
    @FXML private JFXTextField donoraddress;
    @FXML private JFXDatePicker donordate;
    @FXML private JFXComboBox<String> donorgender;
    @FXML private JFXComboBox<String> donorblood;
    @FXML private JFXButton gohome_btn;
    @FXML private JFXButton button;

    private String donorDate = "";
    private String donorLastDate = "";

    @FXML
    void initialize() {
        gohome_btn.setOnAction(event -> {
            gohome_btn.getScene().getWindow().hide();
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

        godonor_button.setOnAction(event -> {
            godonor_button.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/donorlist.fxml"));
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
            donorUser();
        });


        donordate.setOnAction(event -> {
            setDonorDate();
        });

        donorlastdate.setOnAction(event -> {
            setDonorLastDate();
        });
        donorgender.getItems().add("Male");
        donorgender.getItems().add("Female");
        donorblood.getItems().add("A+");
        donorblood.getItems().add("A-");
        donorblood.getItems().add("AB+");
        donorblood.getItems().add("AB-");
        donorblood.getItems().add("B+");
        donorblood.getItems().add("B-");
        donorblood.getItems().add("O+");
        donorblood.getItems().add("O-");


    }

    private void setDonorLastDate() {
        donorLastDate = donorlastdate.getValue().toString();
    }

    private void setDonorDate() {
        donorDate = donordate.getValue().toString();
    }


    private void donorUser() {
        String name = donorname.getText().trim();
        String age = donorage.getText().trim();
        String blood = donorblood.getSelectionModel().getSelectedItem();
        String gender = donorgender.getSelectionModel().getSelectedItem();
        String phone = donorphone.getText().trim();
        String lastdonate = donorLastDate;
        String donationdate = donorDate;
        String qty = donorqty.getText().trim();
        String email = donoremail.getText().trim();
        String address = donoraddress.getText().trim();

        if (name.equals("") || age.equals("") || gender.equals("") || blood.equals("") || phone.equals("") ||
                lastdonate.equals("") || donationdate.equals("") || qty.equals("") || email.equals("") || address.equals("")) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(donorAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Select all fields"));
            return;
        }
        String query = "insert into donors(name,age,gender,blood,phone,last_donation,present_donation,qtyblood,email,address) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, blood);
            ps.setString(5, phone);
            ps.setString(6, lastdonate);
            ps.setString(7, donationdate);
            ps.setString(8, qty);
            ps.setString(9, email);
            ps.setString(10, address);
            ps.executeUpdate();
            ps.close();
            JFXSnackbar jfxSnackbar = new JFXSnackbar(donorAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Donor Added"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }

    private void clear(){
        donorname.setText("");
        donorage.setText("");
        donorgender.getSelectionModel().clearSelection();
        donorblood.getSelectionModel().clearSelection();
        donorphone.setText("");
        donorlastdate.getEditor().clear();
        donordate.getEditor().clear();
        donorqty.setText("");
        donoremail.setText("");
        donoraddress.setText("");
    }
}
