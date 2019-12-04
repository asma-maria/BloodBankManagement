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
import javafx.stage.Stage;
import sample.Database.DBHandler;
import sample.model.Receiver;
import sample.model.Request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestList {
    @FXML private TableView<Request> tableView;
    @FXML private TableColumn<Request, Integer> tableId;
    @FXML private TableColumn<Request, String> tableName;
    @FXML private TableColumn<Request, String> tableAge;
    @FXML private TableColumn<Request, String> tableGender;
    @FXML private TableColumn<Request, String> tableBlood;
    @FXML private TableColumn<Request, String> tablePhone;
    @FXML private TableColumn<Request, String> tablePresentDate;
    @FXML private TableColumn<Request, String> tableQtyBlood;
    @FXML private TableColumn<Request, String> tableAddres;
    @FXML private TableColumn<Request, String> tableEmail;
    @FXML private JFXTextField search_text;
    @FXML private JFXButton delete_btn;
    @FXML private JFXButton insert_btn;
    @FXML private JFXButton update_btn;

    private ObservableList<Request> request = FXCollections.observableArrayList();
    FilteredList<Request> filteredData = new FilteredList<>(request, p -> true);


    @FXML
    void search(KeyEvent event) {
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(donor -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (donor.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (donor.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Request> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
 @FXML
    void initialize() {

        setTableCell();

        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableName.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setName(event.getNewValue());
            updateData(request);
        });

        tableAge.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAge.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setAge(event.getNewValue());
            updateData(request);
        });
        tableGender.setCellFactory(TextFieldTableCell.forTableColumn());
        tableGender.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setGender(event.getNewValue());
            updateData(request);
        });
        tableBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBlood.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setBlood(event.getNewValue());
            updateData(request);
        });

        tablePhone.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePhone.setOnEditCommit(event -> {
            Request request= tableView.getSelectionModel().getSelectedItem();
            request.setPhone(event.getNewValue());
            updateData(request);
        });

        tablePresentDate.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePresentDate.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setPresentDate(event.getNewValue());
            updateData(request);
        });

        tableQtyBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableQtyBlood.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setQtyBlood(event.getNewValue());
            updateData(request);
        });

        tableAddres.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAddres.setOnEditCommit(event -> {
            Request request = tableView.getSelectionModel().getSelectedItem();
            request.setAddress(event.getNewValue());
            updateData(request);
        });
        tableEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        tableEmail.setOnEditCommit(event -> {
            Request request= tableView.getSelectionModel().getSelectedItem();
            request.setEmail(event.getNewValue());
            updateData(request);
        });

        insert_btn.setOnAction(event -> {
            insert_btn.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/request.fxml"));
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
        getRequest();

    }

    private void getRequest() {
        String query = "select * from requests";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            request.clear();
            while (rs.next()) {
                request.add(new Request(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10)));
            }
            tableView.setItems(request);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void updateData(Request request) {

        String query="update requests set name=?,age=?,blood=?,gender=?,phone=?,presentdate=?,qtyblood=?,email=?,address=? where id=? ";
        try{

            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, request.getName());
            ps.setString(2, request.getAge());
            ps.setString(3, request.getBlood());
            ps.setString(4, request.getGender());
            ps.setString(5, request.getPhone());
            ps.setString(6,request.getPresentDate());
            ps.setString(7,request.getQtyBlood());
            ps.setString(8, request.getEmail());
            ps.setString(9, request.getAddress());
            ps.setInt(10, request.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        getRequest();
    }

    private void deleteData() {
        Request req = tableView.getSelectionModel().getSelectedItem();
        request.remove(req);
        String query = "delete from requests where id = " + req.getId();
        try{
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        tableView.setItems(request);
    }

    private void setTableCell() {

        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableBlood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tablePresentDate.setCellValueFactory(new PropertyValueFactory<>("presentDate"));
        tableQtyBlood.setCellValueFactory(new PropertyValueFactory<>("qtyBlood"));
        tableAddres.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

}
