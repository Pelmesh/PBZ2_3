package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.Window.WindowAEDemployee;
import sample.Window.WindowAEDchecked;
import sample.Window.WindowAEDcar;
import sample.Window.WindowTab;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col9, col7, col8, col10, col11, col12, col13, col14, col15;
    @FXML
    private TableColumn<Data, Integer> col16, col6;
    @FXML
    private ChoiceBox conclusion,employee,auto,owners;
    private Connection conn = Main.returnCon();
    private ObservableList<String> langs = FXCollections.observableArrayList();
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    private PreparedStatement preparedStatement;
    private ResultSet rs;



    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable();
            createChoice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTable() throws SQLException {
        table.getItems().clear();
        col16.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("engine"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("color"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("model"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("password"));
        col6.setCellValueFactory(new PropertyValueFactory<Data, Integer>("years"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("certificate"));
        col8.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col9.setCellValueFactory(new PropertyValueFactory<Data, String>("address"));
        col10.setCellValueFactory(new PropertyValueFactory<Data, String>("gender"));
        col11.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col13.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));
        col14.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col15.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));

        preparedStatement = conn.prepareStatement("select * from gibdd_look l\n" +
                "left join gibdd_auto ga on l.id_auto = ga.id_auto\n" +
                "left join gibdd_employee ge on l.id_employee = ge.id_employee\n" +
                "left join gibdd_owner go on l.id_owner = go.id_owner");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            usersData.add(new Data(rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10),
                    rs.getString(11), rs.getString(12), rs.getInt(13),
                    rs.getString(19), rs.getString(20), rs.getString(21),rs.getString(22),
                    rs.getString(15), rs.getString(16),rs.getString(17),
                    rs.getDate(4).toLocalDate(), rs.getString(5)));
        }
        table.setItems(usersData);
    }


    public void save(ActionEvent actionEvent) throws SQLException {
        int idAuto = 0;
        int idEmployee = 0;
        int idOwner = 0;
        preparedStatement = conn.prepareStatement("SELECT id_auto FROM gibdd_auto WHERE engine=?");
        preparedStatement.setString(1, auto.getValue().toString());
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idAuto=rs.getInt(1);
        }

        preparedStatement = conn.prepareStatement("SELECT id_owner FROM gibdd_owner WHERE fio=?");
        preparedStatement.setString(1, owners.getValue().toString());
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idOwner=rs.getInt(1);
        }

        preparedStatement = conn.prepareStatement("SELECT id_employee FROM gibdd_employee WHERE fio=?");
        preparedStatement.setString(1, employee.getValue().toString());
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idEmployee=rs.getInt(1);
        }

        preparedStatement = conn.prepareStatement(" INSERT INTO gibdd_look(id_auto,id_owner, date_look,conlusion, id_employee) VALUES (?,?,?,?,?)");
        preparedStatement.setInt(1, idAuto);
        preparedStatement.setInt(2, idOwner);
        preparedStatement.setDate(3,  Date.valueOf(date.getValue()));
        preparedStatement.setString(4, conclusion.getValue().toString());
        preparedStatement.setInt(5, idEmployee);
        preparedStatement.executeUpdate();
    }

     public void createChoice() throws SQLException {
        employee.getItems().clear();
        auto.getItems().clear();
        owners.getItems().clear();
        langs.clear();
        preparedStatement = conn.prepareStatement("SELECT FIO FROM gibdd_employee");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            langs.add(rs.getString(1));
        }
        employee.getItems().addAll(langs);
        langs.clear();

        preparedStatement = conn.prepareStatement("SELECT engine FROM gibdd_auto");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            langs.add(rs.getString(1));
        }
        auto.getItems().addAll(langs);
        langs.clear();

        preparedStatement = conn.prepareStatement("SELECT FIO FROM gibdd_owner");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            langs.add(rs.getString(1));
        }
        owners.getItems().addAll(langs);
    }

    public void openWin2(ActionEvent actionEvent) throws Exception {
        WindowAEDcar windowAEDcar = new WindowAEDcar();
    }

    public void openWin3(ActionEvent actionEvent) throws Exception {
        WindowAEDemployee window3 = new WindowAEDemployee();
    }

    public void openWin4(ActionEvent actionEvent) throws Exception {
        WindowAEDchecked windowAEDchecked = new WindowAEDchecked();
    }

    public void openWinTab(ActionEvent actionEvent) throws Exception {
        WindowTab windowTAb = new WindowTab();
    }

    public void updateTable(ActionEvent actionEvent) throws SQLException {
        createTable();
        createChoice();
    }
}