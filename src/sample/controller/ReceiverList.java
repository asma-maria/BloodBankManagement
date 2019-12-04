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
import sample.model.Receiver;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiverList {

    @FXML
    private TableView<Receiver> tableView;
    @FXML
    private TableColumn<Receiver, Integer> tableId;
    @FXML
    private TableColumn<Receiver, String> tableName;
    @FXML
    private TableColumn<Receiver, String> tableAge;
    @FXML
    private TableColumn<Receiver, String> tableGender;
    @FXML
    private TableColumn<Receiver, String> tableBlood;
    @FXML
    private TableColumn<Receiver, String> tablePhone;
    @FXML
    private TableColumn<Receiver, String> tableLastDonation;
    @FXML
    private TableColumn<Receiver, String> tablePresentDonation;
    @FXML
    private TableColumn<Receiver, String> tableQtyBlood;
    @FXML
    private TableColumn<Receiver, String> tableAddres;
    @FXML
    private TableColumn<Receiver, String> tableEmail;
    @FXML
    private JFXTextField search_text;
    @FXML
    private JFXButton delete_btn;
    @FXML
    private JFXButton insert_btn;


    private ObservableList<Receiver> receivers = FXCollections.observableArrayList();
    FilteredList<Receiver> filteredData = new FilteredList<>(receivers, p -> true);


    @FXML
    void search(KeyEvent event) {
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(receiver -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (receiver.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (receiver.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Receiver> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

    }
    @FXML
    void initialize() {

        setTableCell();

        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableName.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setName(event.getNewValue());
            updateData(receiver);
        });

        tableAge.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAge.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setAge(event.getNewValue());
            updateData(receiver);
        });
        tableGender.setCellFactory(TextFieldTableCell.forTableColumn());
        tableGender.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setGender(event.getNewValue());
            updateData(receiver);
        });
        tableBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBlood.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setBlood(event.getNewValue());
            updateData(receiver);
        });

        tablePhone.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePhone.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setPhone(event.getNewValue());
            updateData(receiver);
        });

        tableLastDonation.setCellFactory(TextFieldTableCell.forTableColumn());
        tableLastDonation.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setLastReceived(event.getNewValue());
            updateData(receiver);
        });

        tablePresentDonation.setCellFactory(TextFieldTableCell.forTableColumn());
        tableLastDonation.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setLastReceived(event.getNewValue());
            updateData(receiver);
        });

        tableQtyBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableQtyBlood.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setQtyBlood(event.getNewValue());
            updateData(receiver);
        });

        tableAddres.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAddres.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setAddress(event.getNewValue());
            updateData(receiver);
        });
        tableEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        tableEmail.setOnEditCommit(event -> {
            Receiver receiver = tableView.getSelectionModel().getSelectedItem();
            receiver.setEmail(event.getNewValue());
            updateData(receiver);
        });

        insert_btn.setOnAction(event -> {
            insert_btn.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/receiver.fxml"));
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

        delete_btn.setOnAction(event -> {
            deleteData();
        });
        getReceiver();

    }

    private void getReceiver() {
        String query = "select * from receivers";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            receivers.clear();
            while (rs.next()) {
                receivers.add(new Receiver(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11)));
            }
            tableView.setItems(receivers);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        private void updateData(Receiver receiver) {

            String query="update receivers set name=?,age=?,blood=?,gender=?,phone=?,last_received=?,present_date=?,qtyblood=?,email=?,address=? where id=? ";
            try{

                PreparedStatement ps = DBHandler.connect().prepareStatement(query);
                ps.setString(1, receiver.getName());
                ps.setString(2, receiver.getAge());
                ps.setString(3, receiver.getBlood());
                ps.setString(4, receiver.getGender());
                ps.setString(5, receiver.getPhone());
                ps.setString(6, receiver.getLastReceived());
                ps.setString(7,receiver.getPresentDate());
                ps.setString(8,receiver.getQtyBlood());
                ps.setString(9, receiver.getEmail());
                ps.setString(10, receiver.getAddress());
                ps.setInt(11, receiver.getId());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
            getReceiver();
        }

    private void deleteData() {
        Receiver receiver = tableView.getSelectionModel().getSelectedItem();
        receivers.remove(receiver);
        String query = "delete from receivers where id = " + receiver.getId();
        try{
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        tableView.setItems(receivers);
    }

    private void setTableCell() {

        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableBlood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableLastDonation.setCellValueFactory(new PropertyValueFactory<>("lastReceived"));
        tablePresentDonation.setCellValueFactory(new PropertyValueFactory<>("presentDate"));
        tableQtyBlood.setCellValueFactory(new PropertyValueFactory<>("qtyBlood"));
        tableAddres.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
}

