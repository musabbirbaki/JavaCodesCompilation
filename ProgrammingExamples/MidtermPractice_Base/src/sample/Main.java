package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main
 *
 * This class loads the user interface, and gains access to the UI's
 * controller class.
 **/
public class Main extends Application implements FileModifiedListener {
    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();

        Scene scene = new Scene(root, 759, 430);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        this.primaryStage = primaryStage;
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

        public static Stage getStage () {
            return primaryStage;
        }

        public static void main (String[]args) throws IOException {
        launch(args);
//        System.out.println("Helo");
//        ServerSocket serverSocket = new ServerSocket(8080);
//        while (true) {
//            try {
//                Socket clientSocket = serverSocket.accept();
//
//                InputStream inStream = clientSocket.getInputStream();
//                InputStreamReader reader = new InputStreamReader(inStream);
//                BufferedReader in = new BufferedReader(reader);
//
//                String line = null;
//                while ((line = in.readLine()) != null) {
//                    // do something with 'line'
//                    System.out.println(line);
//                }
//
//            } catch (IOException e) {
//                System.out.println("Client Disconnected");
////                e.printStackTrace();
//
//
//            }
//            //... input and output goes here ...
//        }



    }

}
