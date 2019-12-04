package sample.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.model.Receiver;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiverController {
    @FXML private JFXTextField receiver_name;
    @FXML private JFXTextField receiver_age;
    @FXML private ComboBox<String> receiver_blood;
    @FXML private JFXTextField receiver_email;
    @FXML private JFXTextField rcvr_phone;
    @FXML private JFXDatePicker last_rcv;
    @FXML private JFXTextField qty_blood;
    @FXML private JFXTextField receiver_address;
    @FXML private JFXDatePicker present_rcv;
    @FXML private JFXButton submit_btns;
    @FXML private JFXComboBox<String> rcvr_gender;
    @FXML private AnchorPane ReceiverAnchor;
    @FXML private JFXButton gotoReceiver;
    @FXML private JFXButton goHome;


    private String ReceiverDate = "";
    private String ReceiverLastDate = "";
    @FXML
    void initialize() {
        gotoReceiver.setOnAction(event -> {
            gotoReceiver.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/receiverlist.fxml"));
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

        goHome.setOnAction(event -> {
            goHome.getScene().getWindow().hide();
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
        submit_btns.setOnAction(event -> {
            ReceiverUser();
        });

        last_rcv.setOnAction(event -> {
            setReceiverLastDate();
        });

        present_rcv.setOnAction(event -> {
            setReceiverDate();
        });
        rcvr_gender.getItems().add("Male");
        rcvr_gender.getItems().add("Female");
        receiver_blood.getItems().add("A+");
        receiver_blood.getItems().add("A-");
        receiver_blood.getItems().add("B+");
        receiver_blood.getItems().add("B-");
        receiver_blood.getItems().add("AB+");
        receiver_blood.getItems().add("AB-");
        receiver_blood.getItems().add("O+");
        receiver_blood.getItems().add("O-");


    }

    private void ReceiverUser() {

        String name = receiver_name.getText().trim();
        String age = receiver_age.getText().trim();
        String blood = receiver_blood.getSelectionModel().getSelectedItem();
        String gender = rcvr_gender.getSelectionModel().getSelectedItem();
        String phone = rcvr_phone.getText().trim();
        String receiverLastDate = ReceiverLastDate;
        String receiverDate = ReceiverDate;
        String qty = qty_blood.getText().trim();
        String email = receiver_email.getText().trim();
        String address = receiver_address.getText().trim();
        if (name.equals("") || age.equals("") || gender.equals("") || blood.equals("") || phone.equals("") ||
                receiverLastDate.equals("") || receiverDate.equals("") || qty.equals("") || email.equals("") || address.equals("")) {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(ReceiverAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Select all fields"));
            return;
        }
        String query = "insert into receivers(name,age,gender,blood,phone,last_received,present_date,qtyblood,address,email) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, blood);
            ps.setString(5, phone);
            ps.setString(6, receiverLastDate);
            ps.setString(7, receiverDate);
            ps.setString(8, qty);
            ps.setString(9, address);
            ps.setString(10, email);

            ps.executeUpdate();
            ps.close();
            JFXSnackbar jfxSnackbar = new JFXSnackbar(ReceiverAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Receiver Added"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }

    private void clear() {
            receiver_name.setText("");
            receiver_age.setText("");
            rcvr_gender.getSelectionModel().clearSelection();
            receiver_blood.getSelectionModel().clearSelection();
            rcvr_phone.setText("");
            last_rcv.getEditor().clear();
            present_rcv.getEditor().clear();
            qty_blood.setText("");
            receiver_email.setText("");
            receiver_address.setText("");
        }

    private void setReceiverLastDate() {
        ReceiverLastDate=last_rcv.getValue().toString();
    }

    private void setReceiverDate() {
        ReceiverDate=present_rcv.getValue().toString();
    }


}
