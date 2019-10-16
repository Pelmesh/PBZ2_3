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
    @FXML
    private TextField id,autonumber,color,engine,model,passport,year,idOwners, FIO,certificate,address,auto;
    @FXML
    private ChoiceBox gender;
    private ObservableList<Data> DataAuto = FXCollections.observableArrayList();
    private ObservableList<Data> DataOwners = FXCollections.observableArrayList();





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

    public void save(ActionEvent actionEvent) throws SQLException {
        System.out.println("fdskof");
        int count=0;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_auto) FROM gibdd_auto where id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_auto SET autonumber=?,engine=?,color=?,model=?,passport=?,yers=? where id_auto=?");
            preparedStatement.setString(1, autonumber.getText());
            preparedStatement.setString(2, engine.getText());
            preparedStatement.setString(3, color.getText());
            preparedStatement.setString(4, model.getText());
            preparedStatement.setString(5, passport.getText());
            preparedStatement.setInt(6, Integer.parseInt(year.getText()));
            preparedStatement.setInt(7, Integer.parseInt(id.getText()));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("insert into gibdd_auto(id_auto, autonumber,engine, color, model, passport, yers) values\n" +
                    "    (?,?, ?,?,?,?,?);");
            preparedStatement.setInt(1, Integer.parseInt(id.getText()));
            preparedStatement.setString(2, autonumber.getText());
            preparedStatement.setString(3, engine.getText());
            preparedStatement.setString(4, color.getText());
            preparedStatement.setString(5, model.getText());
            preparedStatement.setString(6, passport.getText());
            preparedStatement.setInt(7, Integer.parseInt(year.getText()));
            preparedStatement.executeUpdate();
        }
        createTable();
    }



    public void search(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto where id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
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
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_auto cascade WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        preparedStatement.executeQuery();
        createTable();
    }

    public void saveOwner(ActionEvent actionEvent) throws SQLException {
        int count=0;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_owner) FROM gibdd_owner where id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_owner SET id_auto=?,certificate=?,FIO=?,adress=?,gender=? where id_owner=?");
            preparedStatement.setInt(1, Integer.parseInt(auto.getText()));
            preparedStatement.setString(2, certificate.getText());
            preparedStatement.setString(3, FIO.getText());
            preparedStatement.setString(4, address.getText());
            preparedStatement.setString(5, gender.getValue().toString());
            preparedStatement.setInt(6, Integer.parseInt(idOwners.getText()));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("insert into gibdd_owner(id_auto,id_owner, certificate,FIO, adress, gender) values\n" +
                    "    (?,?, ?,?,?,?);");
            preparedStatement.setInt(1, Integer.parseInt(auto.getText()));
            preparedStatement.setInt(2, Integer.parseInt(idOwners.getText()));
            preparedStatement.setString(3, certificate.getText());
            preparedStatement.setString(4, FIO.getText());
            preparedStatement.setString(5, address.getText());
            preparedStatement.setString(6, gender.getValue().toString());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        createTable();
    }

    public void deleteOwners(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_owner cascade WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        preparedStatement.executeUpdate();
        createTable();
    }

    public void searchOwners(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner where id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            auto.setText(rs.getString(2));
            certificate.setText(rs.getString(3));
            FIO.setText(rs.getString(4));
            address.setText(rs.getString(5));
            gender.setValue(rs.getString(6));
        }
    }
}

