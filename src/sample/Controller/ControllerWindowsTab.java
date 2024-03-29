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

    private PreparedStatement preparedStatementSearchOne() throws SQLException{
        PreparedStatement preparedStatement =conn.prepareStatement("SELECT count(a.id_auto),gl.date_look FROM gibdd_auto a " +
                "INNER JOIN gibdd_look gl on a.id_auto = gl.id_auto\n" +
                "INNER JOIN gibdd_employee ge on gl.id_employee = ge.id_employee\n" +
                "WHERE gl.date_look BETWEEN ? AND ? GROUP BY gl.date_look;");
        preparedStatement.setDate(1, Date.valueOf(dateOne.getValue()));
        preparedStatement.setDate(2, Date.valueOf(dateTwo.getValue()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementSearchTwo() throws SQLException{
        PreparedStatement preparedStatement =conn.prepareStatement("SELECT e.FIO,e.rank,autonumber FROM gibdd_employee e\n" +
                "INNER JOIN gibdd_look gl ON e.id_employee = gl.id_employee\n" +
                "INNER JOIN gibdd_auto ga ON gl.id_auto = ga.id_auto\n" +
                "WHERE gl.date_look=?");
        preparedStatement.setDate(1, Date.valueOf(dateThree.getValue()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementSearchThree() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT date_look,conlusion FROM gibdd_look\n" +
                "INNER JOIN gibdd_auto ga ON gibdd_look.id_auto = ga.id_auto\n" +
                "WHERE engine=?");
        preparedStatement.setString(1, engineNumber.getText());
        return preparedStatement;
    }

    public void searchTableOne(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableOne.getItems().clear();

        col1.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));

        try(PreparedStatement preparedStatement = preparedStatementSearchOne();
        ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                int id = rs.getInt(1);
                Date date = rs.getDate(2);
                LocalDate localD = date.toLocalDate();
                usersData.add(new Data(id, localD));
            }
            TableOne.setItems(usersData);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchTableTwo(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableTwo.getItems().clear();

        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));

        try(PreparedStatement preparedStatement = preparedStatementSearchTwo();
        ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                usersData.add(new Data(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            TableTwo.setItems(usersData);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void searchTableThree(ActionEvent actionEvent) throws SQLException {
        usersData.clear();
        TableThree.getItems().clear();

        col6.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));

        try(PreparedStatement preparedStatement = preparedStatementSearchThree();
        ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                Date date = rs.getDate(1);
                LocalDate localD = date.toLocalDate();
                usersData.add(new Data(localD, rs.getString(2)));
            }
            TableThree.setItems(usersData);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
