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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {

    @FXML
    private ChoiceBox conclusion;
    @FXML
    private TextField autonumber;
    @FXML
    private TextField engine;
    @FXML
    private TextField color;
    @FXML
    private TextField model;
    @FXML
    private TextField passport;
    @FXML
    private TextField certificate;
    @FXML
    private TextField FIO;
    @FXML
    private TextField address;
    @FXML
    private TextField years;
    @FXML
    private DatePicker date;
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col6, col7, col8, col10, col11, col12, col13, col14, col15;
    @FXML
    private TableColumn<Data, Integer> col16, col9;
    @FXML
    private ChoiceBox gender;
    @FXML
    private ChoiceBox employee;
    public Connection conn = Main.returnCon();
    private ObservableList<String> langs = FXCollections.observableArrayList();
    private ObservableList<Data> usersData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT FIO FROM gibdd_employee");
            while (rs.next()) {
                langs.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employee.getItems().addAll(langs);

        try {
            createTable();
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
        col6.setCellValueFactory(new PropertyValueFactory<Data, String>("certificate"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col8.setCellValueFactory(new PropertyValueFactory<Data, String>("address"));
        col9.setCellValueFactory(new PropertyValueFactory<Data, Integer>("years"));
        col10.setCellValueFactory(new PropertyValueFactory<Data, String>("sex"));
        col11.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));
        col13.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col14.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col15.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));

        PreparedStatement preparedStatement = conn.prepareStatement("select   * from gibdd_auto a\n" +
                "left join gibdd_look l on a.id_auto = l.id_auto\n" +
                "left join gibdd_employee e on l.id_employee = e.id_employee\n" +
                "left join gibdd_owner go on l.id_owner = go.id_owner");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs);
            int id = rs.getInt(1);
            String autonumberStr = rs.getString(2);
            String engineStr = rs.getString(3);
            String colorStr = rs.getString(4);
            String modelStr = rs.getString(5);
            String passportStr = rs.getString(6);
            int yearsInt = rs.getInt(7);


            Date date = rs.getDate(10);
            LocalDate Ldate = null;
            if (date == null) {
            } else {
                Ldate = date.toLocalDate();
            }
            String conclusionStr = rs.getString(11);

            String certificateStr = rs.getString(15);
            String FIOStr = rs.getString(16);
            String addressStr = rs.getString(17);
            String genderSTR = rs.getString(18);

            String rankStr = rs.getString(20);
            String positionSTR = rs.getString(21);
            String employeeStr = rs.getString(22);

            usersData.add(new Data(id, autonumberStr, engineStr, colorStr, modelStr, passportStr, yearsInt
                    , certificateStr, FIOStr, addressStr, genderSTR, Ldate, conclusionStr
                    , employeeStr, positionSTR, rankStr));
        }
        table.setItems(usersData);
    }


    public void save(ActionEvent actionEvent) throws SQLException {
        int count = 0;
        /*PreparedStatement preparedStatement = conn.prepareStatement("SELECT count(e.id_employee) from gibdd_employee e " +
                "inner join gibdd_look l on e.id_employee = l.id_employee\n where e.FIO=? and l.date_look=?");
        preparedStatement.setString(1, employee.getValue().toString());
        preparedStatement.setDate(2, Date.valueOf(date.getValue()));
        System.out.println(preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count=rs.getInt(1);
        }
        if(count>10){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ошибка");
            alert.setHeaderText("Больше 10");
            alert.showAndWait();
            return;
        }
        if(autonumber.getText().equals("")||engine.getText().equals("")||color.getText().equals("")||model.getText().equals("")||passport.getText().equals("")||
                certificate.getText().equals("")||FIO.getText().equals("")||address.getText().equals("")|| sex.getItems().toString().equals("")||date.getValue()==null||
                employee.getText().equals("")|| rank.getText().equals("")||conclusion.getValue().toString().equals("")||Integer.parseInt(years.getText())<1000||position.getText().equals("")){
            return;
        }*/

        initData();
        createTable();
    }

    private void initData() throws SQLException {
        int idAuto = 0;
        int idEmployee = 0;
        String autonumberStr = autonumber.getText();
        String engineStr = engine.getText();
        String colorStr = color.getText();
        String modelStr = model.getText();
        String passportStr = passport.getText();
        String certificateStr = certificate.getText();
        String addressStr = address.getText();
        String FIOStr = FIO.getText();
        int yearsInt = Integer.parseInt(years.getText());
        String genderStr = gender.getValue().toString();
        LocalDate Date = date.getValue();
        String conclusionStr = (String) conclusion.getValue();
        String employeeStr = employee.getValue().toString();

        PreparedStatement preparedStatement = conn.prepareStatement("select id_employee from gibdd_employee where FIO=?");
        preparedStatement.setString(1, employeeStr);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idEmployee = rs.getInt(1);
        }

        preparedStatement = conn.prepareStatement("select id_auto from gibdd_auto where engine=?");
        preparedStatement.setString(1, engineStr);
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idAuto = rs.getInt(1);
        }
        if (idAuto > 0) {
            int idOwner = 0;
            preparedStatement = conn.prepareStatement("SELECT MAX(id_owner) FROM gibdd_owner;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idOwner = rs.getInt(1) + 1;
            }
            preparedStatement = conn.prepareStatement("insert into gibdd_owner(id_auto,id_owner, certificate,FIO, adress, gender) values\n" +
                    "(?,?,?,?,?,?);\n");
            preparedStatement.setInt(1, idAuto);
            preparedStatement.setInt(2, idOwner);
            preparedStatement.setString(3, certificateStr);
            preparedStatement.setString(4, FIOStr);
            preparedStatement.setString(5, addressStr);
            preparedStatement.setString(6, genderStr);
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement("insert into gibdd_look(id_auto,id_owner, date_look,conlusion, id_employee) values\n" +
                    "                    (?,?,?,?,?);");
            preparedStatement.setInt(1, idAuto);
            preparedStatement.setInt(2, idOwner);
            preparedStatement.setDate(3, java.sql.Date.valueOf(Date));
            preparedStatement.setString(4, conclusionStr);
            preparedStatement.setInt(5, idEmployee);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        if (idAuto == 0) {
            preparedStatement = conn.prepareStatement("SELECT MAX(id_auto) FROM gibdd_auto;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idAuto = rs.getInt(1) + 1;
            }

            preparedStatement = conn.prepareStatement("insert into gibdd_auto(id_auto, autonumber,engine, color, model, passport, yers) values\n" +
                    " (?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, idAuto);
            preparedStatement.setString(2, autonumberStr);
            preparedStatement.setString(3, engineStr);
            preparedStatement.setString(4, colorStr);
            preparedStatement.setString(5, modelStr);
            preparedStatement.setString(6, passportStr);
            preparedStatement.setInt(7, yearsInt);
            preparedStatement.executeUpdate();

            int idOwner = 0;
            preparedStatement = conn.prepareStatement("SELECT MAX(id_owner) FROM gibdd_owner;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idOwner = rs.getInt(1) + 1;
            }
            preparedStatement = conn.prepareStatement("insert into gibdd_owner(id_auto,id_owner, certificate,FIO, adress, gender) values\n" +
                    "(?,?,?,?,?,?);\n");
            preparedStatement.setInt(1, idAuto);
            preparedStatement.setInt(2, idOwner);
            preparedStatement.setString(3, certificateStr);
            preparedStatement.setString(4, FIOStr);
            preparedStatement.setString(5, addressStr);
            preparedStatement.setString(6, genderStr);
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement("insert into gibdd_look(id_auto,id_owner, date_look,conlusion, id_employee) values\n" +
                    "                    (?,?,?,?,?);");
            preparedStatement.setInt(1, idAuto);
            preparedStatement.setInt(2, idOwner);
            preparedStatement.setDate(3, java.sql.Date.valueOf(Date));
            preparedStatement.setString(4, conclusionStr);
            preparedStatement.setInt(5, idEmployee);
            preparedStatement.executeUpdate();
        }
        createTable();
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
}