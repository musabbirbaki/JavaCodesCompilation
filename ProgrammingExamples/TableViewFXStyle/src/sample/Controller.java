package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML TableView<Person> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initial

        Person one = new Person("One", 10);
        Person two = new Person("Two", 20);

        ObservableList<Person> data = FXCollections.observableArrayList();

        data.add(one);
        data.add(two);

        tableView.setItems(data);
    }
}
