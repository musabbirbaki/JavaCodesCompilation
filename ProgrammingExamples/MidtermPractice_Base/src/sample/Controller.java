package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller
 *
 * This class will act as the controller for this application's UI.
 **/
public class Controller {
    @FXML private TreeView<ProjectFile> projectTreeView;
    @FXML private TextArea editor;
    private String currentFilePath;
    private TextArea currentFileTextArea;

    public void initialize() {
    }

    /**
     * This function runs when on Open is clicked on menu bar
     * @param actionEvent
     */
    public void onOpenFolder(ActionEvent actionEvent) {
        //open directory chooser
        Stage fileChooserStage = new Stage();
        fileChooserStage.initModality(Modality.APPLICATION_MODAL);
        //directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(fileChooserStage);
        String fileDirectoryPath = mainDirectory.getPath();

        //save in this directory
        System.out.println(fileDirectoryPath);

        try {
            processFile(mainDirectory, rootItem);
        } catch (IOException e) {
            System.out.println("Main Directory selection FileIO Problem.");
            e.printStackTrace();
        }

        //currentFilePath is file path of file chosen
        //set tree view drop down to elements directory
        //Adding action listener
        //sets to child one, because child 0 is the same as rootitem
        projectTreeView.setRoot(rootItem.getChildren().get(0));

        projectTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ProjectFile>>() {
            /**
             * This function is change in selection of the tree view
             * @param observable
             * @param oldValue
             * @param newValue
             */
            @Override
            public void changed(ObservableValue<? extends TreeItem<ProjectFile>> observable, TreeItem<ProjectFile> oldValue, TreeItem<ProjectFile> newValue) {
                TreeItem<ProjectFile> selectedItem = (TreeItem<ProjectFile>)newValue;
                if (selectedItem.getValue().toString().contains(".")) {//if selected value file name contains .txt

                    //now open the file and set content of text inside this
                    String foundFilePath = selectedItem.getValue().getFile().getAbsolutePath();//found file path is the selected folder path in tree view
                    Main.getStage().setTitle(selectedItem.getValue().toString());
                    if(!foundFilePath.equals("")){
                        String content = getData(foundFilePath, " ");
                        editor.setText(content);

                        editor.textProperty().addListener(new ChangeListener<String>() {
                            /**
                             * this function is change in textArea
                             * @param observable
                             * @param oldValue
                             * @param newValue
                             */
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                //put * in title
                                if(!Main.getStage().getTitle().contains("*")) {
                                    Main.getStage().setTitle(Main.getStage().getTitle() + "*");
                                }
                                currentFilePath = foundFilePath;
                                currentFileTextArea = editor;
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * This function recursively goes thru file directory and makes TreeItem as parent or child
     */
    private TreeItem<ProjectFile> rootItem;
    public void processFile(File file, TreeItem<ProjectFile> root) throws IOException {
        //if main root is null
        if(null == this.rootItem){
            this.rootItem = new TreeItem<>(new ProjectFile(file));
            this.rootItem.setExpanded(true);
            root = this.rootItem;
        }

        //recursive calls
        if (file.isDirectory()) {
            //if directory set expandable and add to root
            TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(file));
            subRootItem.setExpanded(true);
            root.getChildren().add(subRootItem);

            // process all the files in that directory
            File[] contents = file.listFiles();
            for (File current: contents) {
                root = subRootItem;
                processFile(current, root);
            }
        } else if (file.exists()) {
            //if file don't set expandable and connect to root
            TreeItem<ProjectFile> subRootItem = new TreeItem<>(new ProjectFile(file));
            subRootItem.setExpanded(false);
            root.getChildren().add(subRootItem);
        }
    }

    public void onSaveFile(ActionEvent actionEvent) throws IOException {
        //System.out.println("Save" + currentFilePath);
        //System.out.println("New Text: " + currentFileTextArea.getText());
        String title = Main.getStage().getTitle();
        if(title.contains("*")){
            title = title.substring(0, title.length()-1);
            Main.getStage().setTitle(title);
        }

        if(!this.currentFilePath.equals("")) {
            writeToFile(this.currentFilePath, currentFileTextArea.getText());
        }

    }

    //This method writes to filePath, which is the abs file path, and writes the content
    private void writeToFile(String filePath, String content) throws IOException {
        FileWriter fileOut = new FileWriter(filePath);
        fileOut.append(content);
        fileOut.close();
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*
    /this method reads a file using path name and delimitor
     */
    public String getData(String path, String delimiter) {

        String content = "";
        List<String> srcList = new ArrayList<>();
        // Special Case
        if (path == null || path.length() == 0) {
            return "";
        }
        if(delimiter == null || delimiter.length() == 0) {
            delimiter = ",";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            //Read the next line until end of file
            for (String line; (line = br.readLine()) != null;) {
                content+= line +  "\n";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }



//    /**
//     * this function takes a start/root directory and a file name then finds, the file path of the file name
//     * File file arg - is main directory/root directory
//     * String fileName - file that u want to find
//     *
//     * the name will be stored in this private String foundFilePath, which can be accessed ouside of local
//     * scope.
//     */
//    private String foundFilePath;
//    public String findFilePath(File file, String fileName) {
//        String path = "";
//
//        if(file.exists()){
//            if (file.isDirectory()) {
////                System.out.println("DIR: " +file.getName());
//
//                // process all the files in that directory
//                File[] contents = file.listFiles();
//                for (File current: contents) {
//                    findFilePath(current, fileName);
//                }
//            } else if (file.exists()) {
//                if(file.getName().equals(fileName)){
//                    System.out.println("BINGO");
//                    System.out.println(file.getAbsolutePath());
//                    this.foundFilePath = file.getAbsolutePath();
//
////                    return path;
//                }
////                System.out.println("FILE: " +file.getName());
//            }
//
//        }
//        return path;
//    }
}
