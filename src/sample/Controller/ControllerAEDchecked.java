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
import java.net.URL;
import java.sql.*;
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
    private PreparedStatement preparedStatement;
    private  ResultSet rs;

    void createTable() throws SQLException {
        table.getItems().clear();

        colidlook.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idAuto"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idOwner"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("conclusion"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, Integer>("idemp"));

        preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_look");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            usersData.add(new Data(rs.getInt(1),rs.getInt(2), rs.getInt(3),rs.getDate(4).toLocalDate(),rs.getString(5),rs.getInt(6)));
        }
        table.setItems(usersData);
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        int count=0;
        if(!Id.getText().equals("")) {
            preparedStatement = conn.prepareStatement("SELECT count(id_look) FROM gibdd_look where id_look=?");
            preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }
        if (count==1){
            preparedStatement = conn.prepareStatement("UPDATE gibdd_look SET id_auto=?,id_owner=?,date_look=?,id_employee=?,conlusion=? WHERE id_look=?");
            preparedStatement.setInt(1, Integer.parseInt(idAuto.getText()));
            preparedStatement.setInt(2, Integer.parseInt(idOwner.getText()));
            preparedStatement.setDate(3, Date.valueOf(date.getValue()));
            preparedStatement.setInt(4, Integer.parseInt(idEmp.getText()));
            preparedStatement.setString(5, conclusion.getValue().toString());
            preparedStatement.setInt(6, Integer.parseInt(Id.getText()));
            preparedStatement.executeUpdate();
        }else if(count==0){
            preparedStatement = conn.prepareStatement("INSERT INTO gibdd_look(id_auto,id_owner, date_look,conlusion, id_employee) VALUES \n" +
                    "    (?,?,?,?,?);");
            preparedStatement.setInt(1, Integer.parseInt(idAuto.getText()));
            preparedStatement.setInt(2, Integer.parseInt(idOwner.getText()));
            preparedStatement.setDate(3, Date.valueOf(date.getValue()));
            preparedStatement.setString(4, conclusion.getValue().toString());
            preparedStatement.setInt(5, Integer.parseInt(idEmp.getText()));
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
            idAuto.setText(rs.getString(2));
            idOwner.setText(rs.getString(3));
            date.setValue(rs.getDate(4).toLocalDate());
            conclusion.setValue(rs.getString(5));
            idEmp.setText(rs.getString(6));
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
