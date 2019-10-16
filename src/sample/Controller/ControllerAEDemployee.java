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

public class ControllerAEDemployee implements Initializable{
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String> col4, col2, col3;
    @FXML
    private TableColumn<Data, Integer> colid;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    @FXML
    private TextField Id,FIO,position,rank;

    void createTable() throws SQLException {
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_employee");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(1);
            String employeeStr = rs.getString(2);
            String rankStr = rs.getString(3);
            String positionStr = rs.getString(4);

            usersData.add(new Data(id, employeeStr, rankStr, positionStr));
        }
        table.setItems(usersData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_employee cascade WHERE id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        preparedStatement.executeUpdate();
        createTable();
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_employee where id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            FIO.setText(rs.getString(2));
            rank.setText(rs.getString(3));
            position.setText(rs.getString(4));
        }
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        int count=0;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_employee) FROM gibdd_employee where id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_employee SET FIO=?,rank=?,position=? where id_employee=?");
            preparedStatement.setInt(4, Integer.parseInt(Id.getText()));
            preparedStatement.setString(1, FIO.getText());
            preparedStatement.setString(2, rank.getText());
            preparedStatement.setString(3, position.getText());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("insert into gibdd_employee(id_employee, FIO,rank,position) values\n" +
                    "    (?,?,?,?);");
            preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
            preparedStatement.setString(2, FIO.getText());
            preparedStatement.setString(3, rank.getText());
            preparedStatement.setString(4, position.getText());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        createTable();
    }
}
