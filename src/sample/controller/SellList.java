package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.Sell;
import sample.model.BloodSell;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellList {

    @FXML
    private AnchorPane SellAnchor;
    @FXML
    private TableView<BloodSell> tableView;
    @FXML
    private TableColumn<BloodSell, Integer> tableId;
    @FXML
    private TableColumn<BloodSell, String> tablePresentDate;
    @FXML
    private TableColumn<BloodSell, String> tableBlood;
    @FXML
    private TableColumn<BloodSell, String> tableQtyBlood;
    @FXML
    private TableColumn<BloodSell, String> tableBag;
    @FXML
    private TableColumn<BloodSell, String> tableTotalAmount;
    @FXML
    private JFXTextField search_text;
    @FXML
    private JFXButton insert_btn;
    @FXML
    private JFXButton delete_btn;

    private ObservableList<BloodSell> sellBlood = FXCollections.observableArrayList();
    private FilteredList<BloodSell> filteredData = new FilteredList<>(sellBlood, p -> true);

    @FXML
    void search() {
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getBlood().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getPresentDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<BloodSell> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    @FXML
    void initialize() {
        setTableCell();
        /*tablePresentDate.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePresentDate.setOnEditCommit(event -> {
            BloodSell sellBlood = tableView.getSelectionModel().getSelectedItem();
            sellBlood.setPresentDate(event.getNewValue());
            updateData(sellBlood);

        });
            tableBlood.setCellFactory(TextFieldTableCell.forTableColumn());
            tableBlood.setOnEditCommit(event -> {
                BloodSell sellBlood = tableView.getSelectionModel().getSelectedItem();
                sellBlood.setBlood(event.getNewValue());
                updateData(sellBlood);

            });

        tableQtyBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableQtyBlood.setOnEditCommit(event -> {
            BloodSell sellBlood = tableView.getSelectionModel().getSelectedItem();
            sellBlood.setQtyBlood(event.getNewValue());
            updateData(sellBlood);

        });
        tableBag.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBag.setOnEditCommit(event -> {
            BloodSell sellBlood = tableView.getSelectionModel().getSelectedItem();
            sellBlood.setPrice(event.getNewValue());
            updateData(sellBlood);

        });
        tableTotalAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        tableTotalAmount.setOnEditCommit(event -> {
            BloodSell sellBlood = tableView.getSelectionModel().getSelectedItem();
            sellBlood.setResult(event.getNewValue());
            updateData(sellBlood);

        });*/
        insert_btn.setOnAction(event -> {
            insert_btn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/sellblood.fxml"));
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
        delete_btn.setOnAction(event -> {
            deleteData();
        });
        getSell();

    }

    private void deleteData() {
        BloodSell req = tableView.getSelectionModel().getSelectedItem();
        sellBlood.remove(req);
        String query = "delete from sell where id = " + req.getId();
        try{
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        tableView.setItems(sellBlood);

    }

    private void updateData(BloodSell sellBlood) {

        String query="update sell set present_date=?,bloodgrp=?,qtyblood=?,priceblood=?,result=? where id=? ";
        try{

            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, sellBlood.getPresentDate());
            ps.setString(2, sellBlood.getBlood());
            ps.setString(3, sellBlood.getQtyBlood());
            ps.setString(4, sellBlood.getPrice());
            ps.setString(5, sellBlood.getResult());
            ps.setInt(6, sellBlood.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        getSell();
    }

    private void setTableCell() {

        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePresentDate.setCellValueFactory(new PropertyValueFactory<>("presentDate"));
        tableBlood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        tableQtyBlood.setCellValueFactory(new PropertyValueFactory<>("qtyBlood"));
        tableBag.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableTotalAmount.setCellValueFactory(new PropertyValueFactory<>("result"));

    }

    private void getSell() {
        String query = "select * from sell";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            sellBlood.clear();
            while (rs.next()) {
                sellBlood.add(new BloodSell(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }
            tableView.setItems(sellBlood);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}