package lab.example;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Group;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import lab.example.controller.IController;
import lab.example.model.state.ProgramState;
import lab.example.service.IService;
import lab.example.service.Service;
import lab.example.view.GUIMenu;
import java.io.IOException;

import javafx.scene.control.TableColumn;

//import javax.swing.Action;

/**
 * JavaFX App
 */
public class App extends Application {

    private IService service;
    private GUIMenu menu;

    private TextField noOfPrgStates;
    private ListView<String> outList;
    private ListView<String> fileTable;
    private TableView<Pair<Integer, String>> heapTable;
    private TableView<Pair<Integer, Integer>> lockTable;
    private ListView<Integer> prgStateIdList;
    private TableView<Pair<String, String>> symbTable;
    private ListView<String> exeStack;
    private Button oneStepButton;

    @Override
    public void start(Stage inputStage) throws IOException {
        // scene = new Scene(loadFXML("primary"), 640, 480);
        // stage.setScene(scene);
        // stage.show();

        // Button btn = new Button("Click");   
        // btn.setOnAction(new EventHandler<ActionEvent>() {
            
        //     @Override
        //     public void handle(ActionEvent event) {
        //         System.out.println("heloooo");
        //     }
        // });

        inputStage.setTitle("Input Programs");

        this.menu = new GUIMenu();
        this.menu.populateMenu();

        VBox root = new VBox();
        Scene scene1 = new Scene(root, 500, 500, Color.WHITE);
        ListView<String> programList = new ListView<String>();
        programList.setItems(FXCollections.observableArrayList(menu.getProgramsDesc()));
        root.getChildren().add(programList);
        
        inputStage.setScene(scene1);
        inputStage.show();

        programList.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                // String commandKey = new_val.substring(0, new_val.indexOf(".", 0));
                // Command command = menu.getCommand(commandKey);

                // if (command instanceof ExitCommand) {
                //     Platform.exit();
                //     System.exit(0);
                // }
                

                // RunExampleCommand runCommand = (RunExampleCommand) command;
                // Controller controller = (Controller) runCommand.getController();

                IController controller = this.menu.getController(new_val);
                this.service = new Service(controller);
                try {
                    this.service.runTypeChecker();
                    this.mainWindow();
                }
                catch (Exception e) {
                    this.showErrorWindow("Error", "Type Check failed!", e.getMessage());
                }   
        });
    }

    public void mainWindow () {
        Stage mainWindow = new Stage();
        mainWindow.setTitle("Program States");

        GridPane rootPane = new GridPane();
        Scene scene = new Scene(rootPane, 1000, 1000, Color.WHITE);

        rootPane.add(new Label("No. of Program States: "), 0, 0);
        this.noOfPrgStates = new TextField();
        rootPane.add(this.noOfPrgStates, 1, 0);

        rootPane.add(new Label("Out List"), 0, 1);
        this.outList = new ListView<String>();
        rootPane.add(this.outList, 0, 2);

        rootPane.add(new Label("File Table"), 1, 1);
        this.fileTable = new ListView<String>();
        rootPane.add(this.fileTable, 1, 2);

        rootPane.add(new Label("Heap Table"), 0, 3, 2, 1);
        this.heapTable = new TableView<Pair<Integer, String>>();
        TableColumn<Pair<Integer, String>, Integer> firstColHeap = new TableColumn<Pair<Integer, String>, Integer>("Address");
        firstColHeap.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<Pair<Integer, String>, String> secondColHeap = new TableColumn<Pair<Integer, String>, String>("Value");
        secondColHeap.setCellValueFactory(new PropertyValueFactory<>("value"));
        firstColHeap.prefWidthProperty().bind(this.heapTable.widthProperty().multiply(0.5));
        secondColHeap.prefWidthProperty().bind(this.heapTable.widthProperty().multiply(0.5));
        this.heapTable.getColumns().add(firstColHeap);
        this.heapTable.getColumns().add(secondColHeap);
        rootPane.add(this.heapTable, 0, 4, 2, 1);

        rootPane.add(new Label("Lock Table"), 0, 5, 2, 1);
        this.lockTable = new TableView<Pair<Integer, Integer>>();
        TableColumn<Pair<Integer, Integer>, Integer> firstColLock = new TableColumn<Pair<Integer, Integer>, Integer>("Address");
        firstColLock.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<Pair<Integer, Integer>, Integer> secondColLock = new TableColumn<Pair<Integer, Integer>, Integer>("Program Id");
        secondColLock.setCellValueFactory(new PropertyValueFactory<>("value"));
        firstColLock.prefWidthProperty().bind(this.lockTable.widthProperty().multiply(0.5));
        secondColLock.prefWidthProperty().bind(this.lockTable.widthProperty().multiply(0.5));
        this.lockTable.getColumns().add(firstColLock);
        this.lockTable.getColumns().add(secondColLock);
        rootPane.add(this.lockTable, 0, 6, 2, 1);

        rootPane.add(new Label("Program States Identifiers"), 2, 0, 2, 1);
        this.prgStateIdList = new ListView<Integer>();
        rootPane.add(this.prgStateIdList, 2, 1, 2, 1);

        this.prgStateIdList.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Integer> ov, Integer old_val, Integer new_val) -> {
                try {
                    this.populateSpecificInformation();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        });

        rootPane.add(new Label("Symbols Table"), 2, 2);
        this.symbTable = new TableView<Pair<String, String>>();
        TableColumn<Pair<String, String>, String> firstColSymb = new TableColumn<Pair<String, String>, String>("Variable Name");
        firstColSymb.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<Pair<String, String>, String> secondColSymb = new TableColumn<Pair<String, String>, String>("Value");
        secondColSymb.setCellValueFactory(new PropertyValueFactory<>("value"));
        firstColSymb.prefWidthProperty().bind(this.symbTable.widthProperty().multiply(0.5));
        secondColSymb.prefWidthProperty().bind(this.symbTable.widthProperty().multiply(0.5));
        this.symbTable.getColumns().add(firstColSymb);
        this.symbTable.getColumns().add(secondColSymb);
        rootPane.add(this.symbTable, 2, 3);

        rootPane.add(new Label("Execution Stack"), 3, 2);
        this.exeStack = new ListView<String>();
        rootPane.add(this.exeStack, 3, 3);

        this.oneStepButton = new Button("Run One Step");
        this.oneStepButton.setOnAction(event -> {
            try {
                if (this.service.getNoOfPrgStates() == 0)
                    throw new Exception("No Program States left!");

                this.service.oneStepForAllPrg();
                this.populateGeneralInformation();
                this.populateSpecificInformation();
            }
            catch (Exception e) {
                this.showErrorWindow("Error", "Exception thrown!", e.getMessage());
                mainWindow.close();
                this.service.closeService();
            }
        });
        rootPane.add(oneStepButton, 3, 4, 2, 1);

        this.populateGeneralInformation();

        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public void populateGeneralInformation() {
        this.noOfPrgStates.setText(String.valueOf(this.service.getNoOfPrgStates()));
        this.prgStateIdList.setItems(FXCollections.observableArrayList(this.service.getProgramsId()));
        if (! this.prgStateIdList.getItems().isEmpty()) {
            this.lockTable.setItems(FXCollections.observableArrayList(this.service.getLockTable()));
            this.outList.setItems(FXCollections.observableArrayList(this.service.getOutList()));
            this.fileTable.setItems(FXCollections.observableArrayList(this.service.getFileTable()));
            this.heapTable.setItems(FXCollections.observableArrayList(this.service.getHeapTable()));
        }
        
    }
    
    public void populateSpecificInformation() {
        if (this.prgStateIdList.getSelectionModel().getSelectedItem() == null || this.prgStateIdList.getItems().isEmpty())
            return;

        int id = this.prgStateIdList.getSelectionModel().getSelectedItem();
        try {
            ProgramState state = this.service.getProgramState(id);
            this.exeStack.setItems(FXCollections.observableArrayList(this.service.getExeStack(state.getId())));
            this.symbTable.setItems(FXCollections.observableArrayList(this.service.getSymbTable(state.getId())));    
        }    
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showErrorWindow(String title, String header, String message) {
        Alert errorWindow = new Alert(AlertType.ERROR);
        errorWindow.setTitle(title);
        errorWindow.setHeaderText(header);
        errorWindow.setContentText(message);
        errorWindow.showAndWait();
    }

    // static void setRoot(String fxml) throws IOException {
    //     scene.setRoot(loadFXML(fxml));
    // }

    // private static Parent loadFXML(String fxml) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    //     return fxmlLoader.load();
    // }

    public static void main(String[] args) {
        launch(args);
    }

}