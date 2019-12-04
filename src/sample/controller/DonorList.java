package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import sample.model.Donor;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonorList {
    @FXML private AnchorPane donorAnchor;
    @FXML private TableView<Donor> tableView;
    @FXML private TableColumn<Donor, Integer> tableId;
    @FXML private TableColumn<Donor, String> tableName;
    @FXML private TableColumn<Donor, String> tableAge;
    @FXML private TableColumn<Donor, String> tableGender;
    @FXML private TableColumn<Donor, String> tableBlood;
    @FXML private TableColumn<Donor, String> tablePhone;
    @FXML private TableColumn<Donor, String> tableLastDonation;
    @FXML private TableColumn<Donor, String> tablePresentDonation;
    @FXML private TableColumn<Donor, String> tableQtyBlood;
    @FXML private TableColumn<Donor, String> tableAddres;
    @FXML private TableColumn<Donor, String> tableEmail;
    @FXML private JFXButton delete_btn;
    @FXML private JFXButton insert_btn;
    @FXML private JFXTextField donorname;
    @FXML private JFXTextField donorage;
    @FXML private JFXTextField donoremail;
    @FXML private JFXTextField donorphone;
    @FXML private JFXDatePicker donorlastdate;
    @FXML private JFXTextField donorid;
    @FXML private JFXTextField donorqty;
    @FXML private JFXTextField donoraddress;
    @FXML private JFXDatePicker donordate;
    @FXML private JFXComboBox<String> donotgender;
    @FXML private JFXComboBox<String> donorblood;
    @FXML private JFXButton update_btn;
    @FXML private JFXTextField search_text;
    private String donorDate = "";
    private String donorLastDate = "";

    private ObservableList<Donor> donors = FXCollections.observableArrayList();
    FilteredList<Donor> filteredData = new FilteredList<>(donors, p -> true);

    @FXML
    void search(KeyEvent event){
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
            SortedList<Donor> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        }

    @FXML
    void initialize() {

        setTableCell();


        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableName.setOnEditCommit(event -> {
            Donor donor = tableView.getSelectionModel().getSelectedItem();
            donor.setName(event.getNewValue());
            updateData(donor);
        });

        tableAge.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAge.setOnEditCommit(event -> {
            Donor donor = tableView.getSelectionModel().getSelectedItem();
            donor.setAge(event.getNewValue());
            updateData(donor);
        });
        tableGender.setCellFactory(TextFieldTableCell.forTableColumn());
        tableGender.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setGender(event.getNewValue());
            updateData(donor);
        });
        tableBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableBlood.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setBlood(event.getNewValue());
            updateData(donor);
        });

        tablePhone.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePhone.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setPhone(event.getNewValue());
            updateData(donor);
        });

        tableLastDonation.setCellFactory(TextFieldTableCell.forTableColumn());
        tableLastDonation.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setLastDonatione(event.getNewValue());

            updateData(donor);
        });

        tablePresentDonation.setCellFactory(TextFieldTableCell.forTableColumn());
        tableLastDonation.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setLastDonatione(event.getNewValue());
            updateData(donor);
        });

        tableQtyBlood.setCellFactory(TextFieldTableCell.forTableColumn());
        tableQtyBlood.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setQtyBlood(event.getNewValue());
            updateData(donor);
        });

        tableAddres.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAddres.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setAddress(event.getNewValue());
            updateData(donor);
        });
        tableEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        tableEmail.setOnEditCommit(event -> {
            Donor donor=tableView.getSelectionModel().getSelectedItem();
            donor.setEmail(event.getNewValue());
            updateData(donor);
        });

        insert_btn.setOnAction(event -> {
            insert_btn.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/donor.fxml"));
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
        getDonors();
    }

    private void setTableCell(){
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableBlood.setCellValueFactory(new PropertyValueFactory<>("blood"));
        tablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableLastDonation.setCellValueFactory(new PropertyValueFactory<>("lastDonatione"));
        tablePresentDonation.setCellValueFactory(new PropertyValueFactory<>("presentDonation"));
        tableQtyBlood.setCellValueFactory(new PropertyValueFactory<>("qtyBlood"));
        tableAddres.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void updateData(Donor donor) {

        String query="update donors set name=?,age=?,blood=?,gender=?,phone=?,last_donation=?,present_donation=?,qtyblood=?,email=?,address=? where id=? ";
        try{

            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, donor.getName());
            ps.setString(2, donor.getAge());
            ps.setString(3, donor.getBlood());
            ps.setString(4, donor.getGender());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getLastDonatione());
            ps.setString(7,donor.getPresentDonation());
            ps.setString(8,donor.getQtyBlood());
            ps.setString(9, donor.getEmail());
            ps.setString(10, donor.getAddress());
            ps.setInt(11, donor.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        getDonors();
    }

    private void deleteData() {

        Donor donor = tableView.getSelectionModel().getSelectedItem();
        donors.remove(donor);
        String query = "delete from donors where id = " + donor.getId();
        try{
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        tableView.setItems(donors);
    }

   /* private void insertData() {
         String id=donorid.getText().trim();
         String name=donorname.getText().trim();
        String age=donorage.getText().trim();
        String gender=donotgender.getSelectionModel().getSelectedItem();
        String blood=donorblood.getSelectionModel().getSelectedItem();
        String phone=donorphone.getText().trim();
        String lastDonatione=donorLastDate;
        String presentDonation=donorDate;
        String qtyBlood=donorqty.getText().trim();
        String address=donoraddress.getText().trim();
        String email=donoremail.getText().trim();
        String query = "insert into donors(id.name,age,gender,blood,phone,lastDonatione,presentDonation,qtyBlood,address,email) " +
                "values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, age);
            ps.setString(4, gender);
            ps.setString(5, blood);
            ps.setString(6, phone);
            ps.setString(7, lastDonatione);
            ps.setString(8, presentDonation);
            ps.setString(9, qtyBlood);
            ps.setString(10, address);
            ps.setString(11, email);


            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        getDonors();
    }*/

    private  void getDonors() {
        String query = "select * from donors";
        try {
            PreparedStatement ps = DBHandler.connect().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            donors.clear();
            while (rs.next()) {
                donors.add(new Donor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11)));
            }
            tableView.setItems(donors);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
