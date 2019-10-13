package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerAEDemployee {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String> col4, col2, col3;
    @FXML
    private TableColumn<Data, Integer> colid;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    @FXML
    private TextField Id,FIO,position,rank;

    public ControllerAEDemployee() throws SQLException {

    }

    void createTable() throws SQLException {
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));

        init();

        table.setItems(usersData);
    }

    void init() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM gibdd_employee ");
        while (rs.next()) {
            int id = rs.getInt(1);
            String FIO = rs.getString(2);
            String position = rs.getString(3);
            String rank = rs.getString(4);
            usersData.add(new Data(id, FIO, position, rank));
        }
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String query="SELECT COUNT(id) FROM gibdd_employee WHERE id="+Integer.parseInt(Id.getText());
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            query="UPDATE gibdd_employee SET employee='"+FIO.getText()+"',position='"+position.getText()+"',rank='"+rank.getText()
                    +"' WHERE id="+Integer.parseInt(Id.getText());
            System.out.println(query);
            stmt.execute(query);
        }else if(count==0){
            query="INSERT INTO gibdd_employee values("+Integer.parseInt(Id.getText())+",'"+FIO.getText()+"','"+position.getText()+"','"+rank.getText()+"')";System.out.println(query);
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
        String query="DELETE FROM gibdd_employee WHERE id="+"'"+idStr+"'";
        stmt.execute(query);
        createTable();
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        String idStr = Id.getText();
        Statement stmt = conn.createStatement();
        String query="SELECT * FROM gibdd_employee WHERE id="+"'"+idStr+"'";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            FIO.setText(rs.getString(2));
            position.setText(rs.getString(3));
            rank.setText(rs.getString(4));
        }
    }
}
