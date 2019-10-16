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

public class ControllerWindowsTab {
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





    public void searchTableOne(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableOne.getItems().clear();

        col1.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));

        PreparedStatement preparedStatement = conn.prepareStatement("select count(a.id_auto),gl.date_look from gibdd_auto a\n" +
                "inner join gibdd_look gl on a.id_auto = gl.id_auto\n" +
                "inner join gibdd_employee ge on gl.id_employee = ge.id_employee\n" +
                "where gl.date_look between ? and ? group by gl.date_look;");
        preparedStatement.setDate(1, Date.valueOf(dateOne.getValue()));
        preparedStatement.setDate(2, Date.valueOf(dateTwo.getValue()));
        ResultSet rs = preparedStatement.executeQuery();
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


        PreparedStatement preparedStatement = conn.prepareStatement("select e.FIO,e.rank,autonumber from gibdd_employee e\n" +
                "inner join gibdd_look gl on e.id_employee = gl.id_employee\n" +
                "inner join gibdd_auto ga on gl.id_auto = ga.id_auto\n" +
                "where gl.date_look=?");
        preparedStatement.setDate(1, Date.valueOf(dateThree.getValue()));
        ResultSet rs = preparedStatement.executeQuery();
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


        PreparedStatement preparedStatement = conn.prepareStatement("select date_look,conlusion from gibdd_look\n" +
                "inner join gibdd_auto ga on gibdd_look.id_auto = ga.id_auto\n" +
                "where engine=?");
        preparedStatement.setString(1, engineNumber.getText());
        System.out.println(preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Date date = rs.getDate(1);
            LocalDate localD = date.toLocalDate();
            usersData.add(new Data(localD, rs.getString(2)));
        }
        TableThree.setItems(usersData);
    }
}
