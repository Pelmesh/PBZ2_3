package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.*;
import sample.Window.WindowAEDemployee;
import sample.Window.WindowAEDchecked;
import sample.Window.WindowAEDcar;
import sample.Window.WindowTab;

import java.sql.*;
import java.time.LocalDate;

public class Controller {

    @FXML
    private Button but;
    @FXML
    private Button but2;

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
    private TextField employee;


    @FXML
    private TextField rank;


    @FXML
    private DatePicker date;


    @FXML
    private  TextField position;

    @FXML
    private TableView<Data> table;

    @FXML
    private TableColumn<Data, String> col1,col2,col3,col4,col5,col6,col7,col8,col10,col11,col12,col13,col14,col15;

    @FXML
    private TableColumn<Data, Integer> col9;

    @FXML
    private ChoiceBox sex;

    public Connection conn= Main.returnCon();
    private ObservableList<Data> usersData = FXCollections.observableArrayList();


    @FXML
    public void onClickMethod() throws SQLException {
        int count=0;
        Statement stmt = conn.createStatement();
        String emp="SELECT count(employee) from gibdd_employee inner join gibdd_look ge on gibdd_employee.id = ge.id_look where employee='"+employee.getText()+"' and date_look='"+date.getValue()+"'";
        System.out.println(emp);
        ResultSet rs = stmt.executeQuery(emp);
        while (rs.next()) {
            count=rs.getInt(1);
        }
        if(autonumber.getText().equals("")||engine.getText().equals("")||color.getText().equals("")||model.getText().equals("")||passport.getText().equals("")||
                certificate.getText().equals("")||FIO.getText().equals("")||address.getText().equals("")|| sex.getItems().toString().equals("")||date.getValue()==null||
                employee.getText().equals("")|| rank.getText().equals("")||conclusion.getValue().toString().equals("")||Integer.parseInt(years.getText())<1000||position.getText().equals("")||count>10){
            return;
        }

        initData();
        createTable();
    }




    void initData() throws SQLException {
        int id = 0;
        String autonumberStr=autonumber.getText();
        String engineStr=engine.getText();
        String colorStr=color.getText();
        String modelStr=model.getText();
        String passportStr=passport.getText();
        String certificateStr=certificate.getText();
        String addressStr=address.getText();
        String FIOStr=FIO.getText();
        int yearsInt=Integer.parseInt(years.getText());
        String sexStr=sex.getValue().toString();
        LocalDate Date=date.getValue();
        String employeeStr=employee.getText();
        String positionSTR=position.getText();
        System.out.println(positionSTR);
        String rankStr=rank.getText();
        String conclusionStr=(String) conclusion.getValue();

        Statement stmt = conn.createStatement();


        ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM gibdd_info");
        while (rs.next()) {
            id = rs.getInt(1)+1;
        }

        String gibdd_info="INSERT INTO gibdd_info values("+id+",'"+autonumber.getText()+"','"+engine.getText()+"','"+color.getText()+"','"+model.getText()+"','"+passport.getText()+"','"+certificate.getText()+"','"+FIO.getText()+"','"+address.getText()+"',"
                +Integer.parseInt(years.getText())+",'"+(String)sex.getValue()+"')";
        System.out.println(gibdd_info);
        String gibdd_employee = "INSERT INTO gibdd_employee values("+id+",'"+employee.getText()+"','"+position.getText()+"','"+rank.getText()+"')";
        System.out.println(gibdd_employee);
        String gibdd_look = "INSERT INTO gibdd_look values("+id+",'"+date.getValue()+"','"+(String) conclusion.getValue()+"')";
        System.out.println(gibdd_look);

        stmt.execute(gibdd_info);
        stmt.execute(gibdd_employee);
        stmt.execute(gibdd_look);
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

    public void downloadTable(ActionEvent actionEvent) throws SQLException {
        createTable();
    }

    private void createTable() throws SQLException {
        table.getItems().clear();
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
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("employee"));
        col13.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));
        col14.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));
        col15.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));


        Statement stmt = conn.createStatement();
        String query="select gibdd_info.*,e.*,i.* from gibdd_info\n" +
                "        left    join gibdd_look i on gibdd_info.id = i.id_look\n" +
                "        left    jOIN gibdd_employee e on gibdd_info.id = e.id";
        System.out.println(query);
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            String autonumberStr=rs.getString(2);
            String engineStr=rs.getString(3);
            String colorStr=rs.getString(4);
            String modelStr=rs.getString(5);
            String passportStr=rs.getString(6);
            String certificateStr=rs.getString(7);
            String addressStr=rs.getString(8);
            String FIOStr=rs.getString(9);
            int yearsInt=rs.getInt(10);
            String sexStr=rs.getString(11);
            String employeeStr=rs.getString(13);
            String positionSTR=rs.getString(14);
            String rankStr=rs.getString(15);
            Date date=rs.getDate(17);
            LocalDate Ldate=date.toLocalDate();
            String conclusionStr=rs.getString(18);

            usersData.add(new Data(autonumberStr,engineStr	,	colorStr,modelStr,	passportStr
                    ,certificateStr	,addressStr,FIOStr ,yearsInt,sexStr, Ldate
                    ,employeeStr, positionSTR,rankStr,conclusionStr));
        }

        table.setItems(usersData);
    }
}