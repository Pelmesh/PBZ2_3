package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ControllerAEDchecked implements Initializable {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String>  col2, col3;
    @FXML
    private TableColumn<Data, Integer> colid,col1,col4,colidlook;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    @FXML
    private TextField Id,idAuto,idOwner,idEmp;
    @FXML
    private ChoiceBox conclusion;
    @FXML
    private DatePicker date;



    void createTable() throws SQLException {
        table.getItems().clear();

        colidlook.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idAuto"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idOwner"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idemp"));



        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_look");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(6);
            int idOwner = rs.getInt(2);
            String conclusion = rs.getString(4);
            Date date = rs.getDate(3);
            LocalDate localD = date.toLocalDate();
            int idAuto = rs.getInt(5);
            int idemp = rs.getInt(5);
            System.out.println(id+" "+idOwner+" "+localD+" "+conclusion+" "+idAuto+" "+idemp);
            usersData.add(new Data(id,idOwner, localD, conclusion,idAuto,idemp));
        }
        table.setItems(usersData);
    }


    public void save(ActionEvent actionEvent) throws SQLException {
        int count=0;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_look) FROM gibdd_employee where id_look=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_look SET id_auto=?,id_owner=?,date_look=?,id_employee=?,conlusion=? where id_look=?");
            preparedStatement.setInt(1, Integer.parseInt(idAuto.getText()));
            preparedStatement.setInt(2, Integer.parseInt(idOwner.getText()));
            preparedStatement.setDate(3, Date.valueOf(date.getValue()));
            preparedStatement.setInt(4, Integer.parseInt(idEmp.getText()));
            preparedStatement.setString(5, conclusion.getValue().toString());
            preparedStatement.setString(6, Id.getText());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }else if(count==0){
            int idLook = 0;
            preparedStatement = conn.prepareStatement("SELECT MAX(id_look) FROM gibdd_look;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                idLook = rs.getInt(1) + 1;
            }
            preparedStatement = conn.prepareStatement("insert into gibdd_look(id_auto,id_owner, date_look,conlusion, id_employee,id_look) values\n" +
                    "    (?,?,?,?,?,?);");
            preparedStatement.setInt(1, Integer.parseInt(idAuto.getText()));
            preparedStatement.setInt(2, Integer.parseInt(idOwner.getText()));
            preparedStatement.setDate(3, Date.valueOf(date.getValue()));
            preparedStatement.setInt(5, Integer.parseInt(idEmp.getText()));
            preparedStatement.setString(4, conclusion.getValue().toString());
            preparedStatement.setString(6, Id.getText());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        createTable();
    }


    public void delete(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_look cascade WHERE id_look=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        preparedStatement.executeUpdate();
        createTable();
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_look where id_look=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idAuto.setText(rs.getString(1));
            idOwner.setText(rs.getString(2));
            Date dates=rs.getDate(3);
            date.setValue(dates.toLocalDate());
            conclusion.setValue(rs.getString(4));
            idEmp.setText(rs.getString(5));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
