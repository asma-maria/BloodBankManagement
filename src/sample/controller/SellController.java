package sample.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.Sell;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SellController {
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane BloodAnchor;
    @FXML private JFXTextField sell_qty;
    @FXML private JFXDatePicker sell_date;
    @FXML private JFXComboBox<String> sell_blood;
    @FXML private JFXButton button;
    @FXML private JFXButton golist_button;
    @FXML private JFXButton gohome_button;
    @FXML private JFXTextField price_blood;
    @FXML private JFXTextField result;
    @FXML private JFXButton multiple_btn;
    private int num1;
    private int num2;
    private int total;
    private Sell sell = new Sell();
    private String SellDate="";

    @FXML
    void initialize() {

        gohome_button.setOnAction(event -> {
            gohome_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/home.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        golist_button.setOnAction(event -> {
            golist_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/sellbloodlist.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        sell_blood.getItems().add("A+ 800/- per bag");
        sell_blood.getItems().add("A- 1000/- per bag");
        sell_blood.getItems().add("B+ 700/- per bag");
        sell_blood.getItems().add("B- 1200/- per bag");
        sell_blood.getItems().add("AB+ 1000/- per bag");
        sell_blood.getItems().add("AB- 1400/- per bag");
        sell_blood.getItems().add("O+  800/- per bag");
        sell_blood.getItems().add("O- 1500/- per bag");

        sell_date.setOnAction(event -> {

            setSellDate();
        });


        multiple_btn.setOnAction(event -> {

            num1=Integer.parseInt(sell_qty.getText());
            num2=Integer.parseInt(price_blood.getText());
            total=sell.calculate(num1,num2,'*');
            result.setText(String.valueOf(total));

        });

        button.setOnAction(event -> {
            SellUser();
        });


    }

    private void SellUser() {

        String qty=sell_qty.getText().trim();
        String PresentDate=SellDate;
        String blood=sell_blood.getSelectionModel().getSelectedItem().trim();
        String price=price_blood.getText().trim();
        String multiplication=result.getText().trim();

        if (qty.equals("") || PresentDate.equals("") || blood.equals("") || price.equals("") || multiplication.equals(""))
        {
            JFXSnackbar jfxSnackbar = new JFXSnackbar(BloodAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Select all fields"));
            return;

        }

        String query = "insert into sell(present_date,bloodgrp,qtyblood,priceblood,result) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, PresentDate);
            ps.setString(2, blood);
            ps.setString(3, qty);
            ps.setString(4, price);
            ps.setString(5, multiplication);
            ps.executeUpdate();
            ps.close();
            JFXSnackbar jfxSnackbar = new JFXSnackbar(BloodAnchor);
            jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("New Sell Added"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
    }

    private void clear() {

        sell_blood.getSelectionModel().clearSelection();
        sell_qty.setText("");
        price_blood.setText("");
        result.setText("");
        sell_date.getEditor().clear();
    }

    private void setSellDate() {
        SellDate=sell_date.getValue().toString();
    }
}
