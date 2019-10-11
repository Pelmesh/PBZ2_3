package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.sql.*;
import java.time.LocalDate;

public class ControllerWindowsLookThree {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> TableOne;
    @FXML
    private DatePicker dateOne,dateTwo;
    @FXML
    private TableColumn<Data, String>  col3, col2, col4, col5, col6, col7;
    @FXML
    private TableColumn<Data, Integer> col1;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();

    public ControllerWindowsLookThree(){

        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));
        col6.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));
    }

    /**/


    public void searchTableOne(ActionEvent actionEvent) throws SQLException {
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
}
