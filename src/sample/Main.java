package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.DBHandler;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = load(getClass().getResource("/sample/view/welcome.fxml"));
        primaryStage.setTitle("Blood Bank Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        DBHandler.connect();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
