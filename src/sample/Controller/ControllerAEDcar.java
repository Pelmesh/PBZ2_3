package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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

public class ControllerAEDcar implements Initializable {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> table, tableOwners;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col7, col9, col10, col12;
    @FXML
    private TableColumn<Data, Integer> colid, col13, col11;
    @FXML
    private TextField id, autonumber, color, engine, model, passport, year, idOwners, FIO, certificate, address, auto;
    @FXML
    private ChoiceBox gender;
    private ObservableList<Data> DataAuto = FXCollections.observableArrayList();
    private ObservableList<Data> DataOwners = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            createTable();

    }

    private void createTable() {
        DataAuto.clear();
        DataOwners.clear();
        table.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
        col1.setCellValueFactory(new PropertyValueFactory<Data, String>("autonumber"));
        col2.setCellValueFactory(new PropertyValueFactory<Data, String>("engine"));
        col3.setCellValueFactory(new PropertyValueFactory<Data, String>("color"));
        col4.setCellValueFactory(new PropertyValueFactory<Data, String>("model"));
        col5.setCellValueFactory(new PropertyValueFactory<Data, String>("password"));
        col7.setCellValueFactory(new PropertyValueFactory<Data, String>("id"));
        col9.setCellValueFactory(new PropertyValueFactory<Data, String>("FIO"));
        col10.setCellValueFactory(new PropertyValueFactory<Data, String>("gender"));
        col11.setCellValueFactory(new PropertyValueFactory<Data, Integer>("address"));
        col12.setCellValueFactory(new PropertyValueFactory<Data, String>("certificate"));
        col13.setCellValueFactory(new PropertyValueFactory<Data, Integer>("years"));

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto");
             ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                DataAuto.add(new Data(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6)));
            }
            table.setItems(DataAuto);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner");
             ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                DataOwners.add(new Data(rs.getInt(1), rs.getString(4),
                        rs.getString(2), rs.getString(5), rs.getString(3), rs.getInt(6)));
            }
            tableOwners.setItems(DataOwners);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement preparedStatementCount() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_auto) FROM gibdd_auto WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementUpdate() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE gibdd_auto SET autonumber=?,engine=?,color=?,model=?,passport=? WHERE id_auto=?");
        preparedStatement.setString(1, autonumber.getText());
        preparedStatement.setString(2, engine.getText());
        preparedStatement.setString(3, color.getText());
        preparedStatement.setString(4, model.getText());
        preparedStatement.setString(5, passport.getText());
        preparedStatement.setInt(6, Integer.parseInt(id.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementSave() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gibdd_auto(autonumber,engine, color, model, passport) VALUES \n" +
                "    (?, ?,?,?,?);");
        preparedStatement.setString(1, autonumber.getText());
        preparedStatement.setString(2, engine.getText());
        preparedStatement.setString(3, color.getText());
        preparedStatement.setString(4, model.getText());
        preparedStatement.setString(5, passport.getText());
        return preparedStatement;
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        int count = 0;
        if (!id.getText().equals("")) {
            try(PreparedStatement preparedStatement = preparedStatementCount();
            ResultSet rs = preparedStatement.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (count == 1) {
            try(PreparedStatement preparedStatement = preparedStatementUpdate();){
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (count == 0) {
            try(PreparedStatement preparedStatement = preparedStatementSave();){
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        createTable();
        id.clear();
    }

    private PreparedStatement preparedStatementSearch() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_auto WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        return preparedStatement;
    }

    public void search(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementSearch();) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                autonumber.setText(rs.getString(2));
                engine.setText(rs.getString(3));
                color.setText(rs.getString(4));
                model.setText(rs.getString(5));
                passport.setText(rs.getString(6));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private PreparedStatement preparedStatementDelete() throws SQLException{
        PreparedStatement   preparedStatement = conn.prepareStatement("DELETE FROM gibdd_auto CASCADE WHERE id_auto=?");
        preparedStatement.setInt(1, Integer.parseInt(id.getText()));
        return preparedStatement;
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementDelete();) {
            preparedStatement.executeUpdate();
            createTable();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement preparedStatementCountOwner() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id_owner) FROM gibdd_owner WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementUpdateOwner() throws SQLException{
        PreparedStatement preparedStatement =conn.prepareStatement("UPDATE gibdd_owner SET certificate=?,FIO=?,adress=?,gender=?,year=? WHERE id_owner=?");
        preparedStatement.setString(1, certificate.getText());
        preparedStatement.setString(2, FIO.getText());
        preparedStatement.setString(3, address.getText());
        preparedStatement.setString(4, gender.getValue().toString());
        preparedStatement.setInt(5, Integer.parseInt(year.getText()));
        preparedStatement.setInt(6, Integer.parseInt(idOwners.getText()));
        return preparedStatement;
    }

    private PreparedStatement preparedStatementSaveOwner() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gibdd_owner( certificate,FIO, adress, gender, year) VALUES \n" +
                "    (?,?,?,?,?);");
        preparedStatement.setString(1, certificate.getText());
        preparedStatement.setString(2, FIO.getText());
        preparedStatement.setString(3, address.getText());
        preparedStatement.setString(4, gender.getValue().toString());
        preparedStatement.setInt(5, Integer.parseInt(year.getText()));
        return preparedStatement;
    }

    public void saveOwner(ActionEvent actionEvent) throws SQLException {
        int count = 0;
        if (!idOwners.getText().equals("")) {
            try(PreparedStatement preparedStatement = preparedStatementCountOwner();
            ResultSet rs = preparedStatement.executeQuery();){
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (count == 1) {
            try(PreparedStatement preparedStatement = preparedStatementUpdateOwner();) {
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (count == 0) {
            try(PreparedStatement preparedStatement = preparedStatementSaveOwner();) {
                preparedStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        createTable();
    }

    private PreparedStatement preparedStatementDeleteOwner() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM gibdd_owner CASCADE WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        return preparedStatement;
    }

    public void deleteOwners(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementDeleteOwner();){
            preparedStatement.executeUpdate();
            createTable();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement preparedStatementSearchOwners() throws SQLException{
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM gibdd_owner WHERE id_owner=?");
        preparedStatement.setInt(1, Integer.parseInt(idOwners.getText()));
        return preparedStatement;
    }

    public void searchOwners(ActionEvent actionEvent) throws SQLException {
        try(PreparedStatement preparedStatement = preparedStatementSearchOwners();
           ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                certificate.setText(rs.getString(2));
                FIO.setText(rs.getString(3));
                address.setText(rs.getString(4));
                gender.setValue(rs.getString(5));
                year.setText(String.valueOf(rs.getInt(6)));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

