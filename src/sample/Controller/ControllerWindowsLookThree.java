package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.sql.*;
import java.time.LocalDate;

public class ControllerWindowsLookThree {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> TableOne,TableTwo,TableThree;
    @FXML
    private DatePicker dateOne,dateTwo,dateThree;
    @FXML
    private TextField engineNumber;
    @FXML
    private TableColumn<Data, String>  col3, col2, col4, col5, col6, col7;
    @FXML
    private TableColumn<Data, Integer> col1;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();

    public ControllerWindowsLookThree(){

      /*
        */
    }




    public void searchTableOne(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableOne.getItems().clear();

        col1.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));

        Statement stmt = conn.createStatement();
        String query="select count(g.id),date_look from gibdd_info g INNER JOIN gibdd_look i on g.id = i.id_look " +
                "where i.date_look between'"+dateOne.getValue()+"' and '"+dateTwo.getValue()+"' GROUP BY date_look";
        System.out.println(query);
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt(1);
            Date date = rs.getDate(2);
            LocalDate localD = date.toLocalDate();
            usersData.add(new Data(id, localD));
        }
        TableOne.setItems(usersData);
    }

    public void searchTableTwo(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableTwo.getItems().clear();

        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));

        Statement stmt = conn.createStatement();
        String query="SELECT employee,rank,i.autonumber from gibdd_employee g INNER JOIN gibdd_look l on g.id = l.id_look inner join gibdd_info i on g.id " +
                "= i.id where date_look='"+dateThree.getValue()+"'";
        System.out.println(query);
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            usersData.add(new Data(rs.getString(1), rs.getString(2),rs.getString(3)));
        }
        TableTwo.setItems(usersData);
    }

    public void searchTableThree(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableThree.getItems().clear();

        col6.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));

        Statement stmt = conn.createStatement();
        String query="SELECT date_look,conclusion FROM gibdd_look\n" +
                "INNER JOIN gibdd_info gi on gibdd_look.id_look = gi.id\n" +
                "where gi.engine='"+engineNumber.getText()+"'";
        System.out.println(query);
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            Date date = rs.getDate(1);
            LocalDate localD = date.toLocalDate();
            usersData.add(new Data(localD, rs.getString(2)));
        }
        TableThree.setItems(usersData);
    }
}
