package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller.Controller3;
import sample.Controller.ControllerWindowsLookThree;

public class WindowTab {
    public void start() throws Exception {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("WindowsLookThree.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public WindowTab() throws Exception {
        start();
        ControllerWindowsLookThree con = new ControllerWindowsLookThree();
    }
}
