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
import java.util.ResourceBundle;

public class ControllerAEDcar implements Initializable {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table, tableOwners;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col7, col9, col10,col12;
    @FXML
    private TableColumn<Data, Integer> colid, col6,col11;
    @FXML
    private TextField id,autonumber,color,engine,model,passport,year,idOwners, FIO,certificate,address,auto;
    @FXML
    private ChoiceBox gender;
    private ObservableList<Data> DataAuto = FXCollections.observableArrayList();
    private ObservableList<Data> DataOwners = FXCollections.observableArrayList();
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        DataAuto.clear();
        DataOwners.clear();
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("engine"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("color"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("model"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("password"));
        col6.setCellValueFactory(new PropertyValueFactory<Data, Integer>("years"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("id"));
        col9.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col10.setCellValueFactory(new PropertyValueFactory<Data, String>("gender"));
        col11.setCellValueFactory(new PropertyValueFactory<Data, Integer>("address"));
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("certificate"));

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            DataAuto.add(new Data(rs.getInt(1), rs.getString(2),  rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getInt(7)));
        }
        table.setItems(DataAuto);

        preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            DataOwners.add(new Data(rs.getInt(1), rs.getString(4),
                    rs.getString(2), rs.getString(5), rs.getString(3)));
        }
        tableOwners.setItems(DataOwners);
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        int count=0;
        if(!id.getText().equals("")) {
            preparedStatement = conn.prepareStatement("SELECT COUNT(id_auto) FROM gibdd_auto WHERE id_auto=?");
            preparedStatement.setInt(1, Integer.parseInt(id.getText()));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }
        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_auto SET autonumber=?,engine=?,color=?,model=?,passport=?,yers=? WHERE id_auto=?");
            preparedStatement.setString(1, autonumber.getText());
            preparedStatement.setString(2, engine.getText());
            preparedStatement.setString(3, color.getText());
            preparedStatement.setString(4, model.getText());
            preparedStatement.setString(5, passport.getText());
            preparedStatement.setInt(6, Integer.parseInt(year.getText()));
            preparedStatement.setInt(7, Integer.parseInt(id.getText()));
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("INSERT INTO gibdd_auto(autonumber,engine, color, model, passport, yers) VALUES \n" +
                    "    (?, ?,?,?,?,?);");
            preparedStatement.setString(1, autonumber.getText());
            preparedStatement.setString(2, engine.getText());
            preparedStatement.setString(3, color.getText());
            preparedStatement.setString(4, model.getText());
            preparedStatement.setString(5, passport.getText());
            preparedStatement.setInt(6, Integer.parseInt(year.getText()));
            preparedStatement.executeUpdate();
        }
        createTable();
        ControllerMain cm= new ControllerMain();
        cm.createChoice();
        id.clear();
    }



    public void search(ActionEvent actionEvent) throws SQLException {
        preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            autonumber.setText(rs.getString(2));
            engine.setText(rs.getString(3));
            color.setText(rs.getString(4));
            model.setText(rs.getString(5));
            passport.setText(rs.getString(6));
            year.setText(rs.getString(7));
        }
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        preparedStatement = conn.prepareStatement("DELETE FROM gibdd_auto CASCADE WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        preparedStatement.executeUpdate();
        createTable();
    }

    public void saveOwner(ActionEvent actionEvent) throws SQLException {
        int count=0;
        if(!idOwners.getText().equals("")) {
             preparedStatement = conn.prepareStatement("SELECT COUNT(id_owner) FROM gibdd_owner WHERE id_owner=?");
            preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }
        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_owner SET certificate=?,FIO=?,adress=?,gender=? WHERE id_owner=?");
            preparedStatement.setString(1, certificate.getText());
            preparedStatement.setString(2, FIO.getText());
            preparedStatement.setString(3, address.getText());
            preparedStatement.setString(4, gender.getValue().toString());
            preparedStatement.setInt(5, Integer.parseInt(idOwners.getText()));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("INSERT INTO gibdd_owner( certificate,FIO, adress, gender) VALUES \n" +
                    "    (?,?,?,?);");
            preparedStatement.setString(1, certificate.getText());
            preparedStatement.setString(2, FIO.getText());
            preparedStatement.setString(3, address.getText());
            preparedStatement.setString(4, gender.getValue().toString());
            preparedStatement.executeUpdate();
        }
        createTable();
    }

    public void deleteOwners(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_owner CASCADE WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        preparedStatement.executeUpdate();
        createTable();
    }

    public void searchOwners(ActionEvent actionEvent) throws SQLException {
        preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            auto.setText(rs.getString(2));
            certificate.setText(rs.getString(3));
            FIO.setText(rs.getString(4));
            address.setText(rs.getString(5));
            gender.setValue(rs.getString(6));
        }
    }
}

