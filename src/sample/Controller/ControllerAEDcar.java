package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAEDcar implements Initializable {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table, tableOwners;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col7, col8, col9, col10,col12;
    @FXML
    private TableColumn<Data, Integer> colid, col6,col11;
    private ObservableList<Data> DataAuto = FXCollections.observableArrayList();
    private ObservableList<Data> DataOwners = FXCollections.observableArrayList();
    @FXML
    private TextField Id,autonumber,FIO,engine,model,color,passport,years;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("engine"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("color"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("model"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("password"));
        col6.setCellValueFactory(new PropertyValueFactory<Data, Integer>("years"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("id"));
        col8.setCellValueFactory(new PropertyValueFactory<Data, String>("gender"));
        col9.setCellValueFactory(new PropertyValueFactory<Data, String>("address"));
        col10.setCellValueFactory(new PropertyValueFactory<Data, String>("certificate"));
        col11.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idAuto"));
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String autonumberStr = rs.getString(2);
            String engineStr = rs.getString(3);
            String colorStr = rs.getString(4);
            String modelStr = rs.getString(5);
            String passportStr = rs.getString(6);
            int yearsInt = rs.getInt(7);

            DataAuto.add(new Data(id, autonumberStr, engineStr, colorStr, modelStr, passportStr, yearsInt));
        }
        table.setItems(DataAuto);


        preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            int idAuto = rs.getInt(2);
            String certificateSTR = rs.getString(3);
            String FIOStr = rs.getString(4);
            String address = rs.getString(5);
            String genderSTR = rs.getString(6);
            DataOwners.add(new Data(id,idAuto, certificateSTR, FIOStr, address, genderSTR));
        }
        tableOwners.setItems(DataOwners);
    }

    public void save(ActionEvent actionEvent) {
    }

    public void deleteOwners(ActionEvent actionEvent) {
    }

    public void searchOwners(ActionEvent actionEvent) {
    }

    public void search(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }
}

