package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;

public class ControllerAEDchecked {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String>  col2, col3;
    @FXML
    private TableColumn<Data, Integer> colid;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    @FXML
    private TextField Id;
    @FXML
    private ChoiceBox conclusion;
    @FXML
    private DatePicker date;


    public ControllerAEDchecked() throws SQLException {

    }

    void createTable() throws SQLException {
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));

        init();

        table.setItems(usersData);
    }

    void init() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM gibdd_look ");
        while (rs.next()) {
            int id = rs.getInt(1);
            String conclusion = rs.getString(3);
            Date date = rs.getDate(2);
            LocalDate localD = date.toLocalDate();
            usersData.add(new Data(id, localD, conclusion));
        }
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String query="SELECT COUNT(id_look) FROM gibdd_look WHERE id_look="+Integer.parseInt(Id.getText());
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            query="UPDATE gibdd_look SET date_look='"+date.getValue()+"',conclusion='"+conclusion.getValue().toString()+"' WHERE id_look="+Integer.parseInt(Id.getText());
            System.out.println(query);
            stmt.execute(query);
        }else if(count==0){
            query="INSERT INTO gibdd_look values("+Integer.parseInt(Id.getText())+",'"+date.getValue()+"','"+conclusion.getValue().toString()+"')";
            System.out.println(query);
            stmt.execute(query);
        }
        createTable();
    }

    public void onClickMethod(ActionEvent actionEvent) throws SQLException {
        createTable();
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        String idStr = Id.getText();
        Statement stmt = conn.createStatement();
        String query="DELETE FROM gibdd_look WHERE id_look="+"'"+idStr+"'";
        stmt.execute(query);
        createTable();
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        String idStr = Id.getText();
        Statement stmt = conn.createStatement();
        String query="SELECT * FROM gibdd_look WHERE id_look="+"'"+idStr+"'";
        System.out.println(
                query
        );
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Date d=rs.getDate(2);
            date.setValue(d.toLocalDate());
            conclusion.setValue(rs.getString(3));
        }
    }
}
