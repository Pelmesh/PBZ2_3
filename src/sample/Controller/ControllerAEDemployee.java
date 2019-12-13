package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Data;
import sample.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerAEDemployee implements Initializable{
    @FXML
    private TableView<Data> table;
    @FXML
    private TableColumn<Data, String> col4, col2, col3;
    @FXML
    private TableColumn<Data, Integer> colid;
    @FXML
    private Connection conn = Main.returnCon();
    @FXML
    private TextField Id,FIO,position,rank;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();

    private void createTable() throws SQLException {
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("position"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("rank"));

        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_employee");
        ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                usersData.add(new Data(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            table.setItems(usersData);
        }catch (SQLException e) {
            e.printStackTrace();
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

    public void delete(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementDelete();){
            preparedStatement.executeUpdate();
            createTable();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private PreparedStatement preparedStatementDelete() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_employee CASCADE WHERE id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        return preparedStatement;
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementSearch();
        ResultSet rs = preparedStatement.executeQuery();){
            while (rs.next()) {
                FIO.setText(rs.getString(2));
                rank.setText(rs.getString(3));
                position.setText(rs.getString(4));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement preparedStatementSearch() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_employee WHERE id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementCount() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_employee) FROM gibdd_employee WHERE id_employee=?");
        preparedStatement.setInt(1, Integer.parseInt(Id.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementUpdate() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE gibdd_employee SET fio=?,rank=?,position=? WHERE id_employee=?");
        preparedStatement.setString(1, FIO.getText());
        preparedStatement.setString(2, rank.getText());
        preparedStatement.setString(3, position.getText());
        preparedStatement.setInt(4, Integer.parseInt(Id.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementSave() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gibdd_employee(FIO,rank,position) VALUES (?,?,?)");
        preparedStatement.setString(1, FIO.getText());
        preparedStatement.setString(2, rank.getText());
        preparedStatement.setString(3, position.getText());
        System.out.println(preparedStatement);
        return preparedStatement;
    }


    public void save(ActionEvent actionEvent) throws SQLException {
        int count=0;
        if(!Id.getText().equals("")) {
            try(PreparedStatement preparedStatement = preparedStatementCount();
            ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (count==1){
            try(PreparedStatement preparedStatement = preparedStatementUpdate();) {
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(count==0) {
            try( PreparedStatement preparedStatement = preparedStatementSave();) {
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        createTable();
    }
}
