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

public class ControllerTwo {
    private Connection conn = Main.returnCon();
    @FXML
    private TableView<Data> Two;
    @FXML
    private TableColumn<Data, String> col1, col2, col3, col4, col5, col6, col7, col8, col10;
    @FXML
    private TableColumn<Data, Integer> colid, col9;
    private ObservableList<Data> usersData = FXCollections.observableArrayList();
    @FXML
    private TextField Id,autonumber,FIO,certificate,address,engine,model,color,passport,years;
    @FXML
    private ChoiceBox sex;

    public ControllerTwo() throws SQLException {

    }


    void createTable() throws SQLException {
        Two.getItems().clear();
        colid.setCellValueFactory(new PropertyValueFactory<Data, Integer>("id"));
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

        init();

        Two.setItems(usersData);
    }


    void init() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM gibdd_info GROUP BY id");
        while (rs.next()) {
            int id = rs.getInt(1);
            String autonumber = rs.getString(2);
            String engine = rs.getString(3);
            String color = rs.getString(4);
            String model = rs.getString(5);
            String password = rs.getString(6);
            String certificate = rs.getString(7);
            String FIO = rs.getString(8);
            String address = rs.getString(9);
            int years = rs.getInt(10);
            String sex = rs.getString(11);

            usersData.add(new Data(id, autonumber, engine, color,
                     model,password, certificate, FIO, address, years, sex));

        }

    }

    public void onClickMethod(ActionEvent actionEvent) throws SQLException {
        createTable();
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        Statement stmt = conn.createStatement();
        int count = 0;
        String query="SELECT COUNT(id) FROM gibdd_info WHERE id="+Integer.parseInt(Id.getText());
        ResultSet rs =stmt.executeQuery(query);
        while (rs.next()) {
            count=rs.getInt(1);
        }

        if (count==1){
            query="UPDATE gibdd_info SET autonumber='"+autonumber.getText()+"',engine='"+engine.getText()+"',color='"+color.getText()+"',model='"+
                    model.getText()+"',passport='"+passport.getText()+"',certificate='"+certificate.getText()+"',FIO='"+FIO.getText()+"',adress='"+address.getText()+
                    "',yers="+Integer.parseInt(years.getText())+",sex='"+sex.getValue().toString()+"' WHERE id="+Integer.parseInt(Id.getText());
            stmt.execute(query);
        }else if(count==0){
            query="INSERT INTO gibdd_info values("+Integer.parseInt(Id.getText())+",'"+autonumber.getText()+"','"+engine.getText()+"','"+color.getText()+"','"+model.getText()+"','"+passport.getText()+"','"+certificate.getText()+"','"+FIO.getText()+"','"+address.getText()+"',"
                    +Integer.parseInt(years.getText())+",'"+sex.getValue().toString()+"')";
            stmt.execute(query);
        }
        createTable();
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        String idStr = Id.getText();
        Statement stmt = conn.createStatement();
        String query="DELETE FROM gibdd_info WHERE id="+idStr;
        System.out.println(query);
        stmt.execute(query);
        createTable();
    }



    public void search(ActionEvent actionEvent) throws SQLException {
        String idStr = Id.getText();
        Statement stmt = conn.createStatement();
        String query="SELECT * FROM gibdd_info WHERE id="+"'"+idStr+"'";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            autonumber.setText(rs.getString(2));
            engine.setText(rs.getString(3));
            color.setText(rs.getString(4));
            model.setText(rs.getString(5));
            passport.setText(rs.getString(6));
            certificate.setText(rs.getString(7));
            FIO.setText(rs.getString(8));
            address.setText(rs.getString(9));
            years.setText(rs.getString(10));
            sex.setValue(rs.getString(11));
        }
    }
}

