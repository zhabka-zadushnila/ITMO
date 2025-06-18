package gui;

import gui.managers.CommandsManager;
import gui.screens.DragonFormScreen;
import gui.screens.LoginScreen;
import gui.utils.DragonDisplayWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import managers.CollectionManager;
import structs.User;
import structs.classes.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * ПРИМЕЧАНИЕ: Поскольку классы-сущности (Dragon, Coordinates, и т.д.) не были предоставлены,
 * я создал их базовые версии в конце этого файла для полноты и компилируемости.
 * В вашем реальном проекте вы должны использовать свои собственные классы.
 * <p>
 * Этот класс отображает коллекцию драконов в виде таблицы с возможностью
 * фильтрации, сортировки и выполнения команд.
 */
public class DragonTableView extends Application {

    private static CollectionManager collectionManager;
    private final ObservableList<DragonDisplayWrapper> masterData = FXCollections.observableArrayList();
    User user = null;
    private TableView<DragonDisplayWrapper> table = new TableView<>();
    private TextField filterField;
    private CommandsManager commandsManager = new CommandsManager();

    public static void initialize(CollectionManager cm) {
        collectionManager = cm;
    }

    @Override
    public void start(Stage primaryStage) {
        startLogin();
        primaryStage.setTitle("Dragon Collection Viewer");

        if (collectionManager == null) {
            collectionManager = new CollectionManager();
            Dragon testDragon1 = new Dragon("Smaug", new Coordinates(10.5, 100L), 171, Color.BLACK, DragonType.FIRE, DragonCharacter.EVIL, new DragonCave(1000, 100000.0));
            testDragon1.setOwnerLogin("user1");
            Dragon testDragon2 = new Dragon("Viserion", new Coordinates(25.0, 500L), 5, Color.YELLOW, DragonType.AIR, DragonCharacter.FICKLE, null);
            testDragon2.setOwnerLogin("user2");
            collectionManager.addElement("smaug_key", testDragon1);
            collectionManager.addElement("viserion_key", testDragon2);
        }

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        BorderPane topPanel = createTopPanel();
        root.setTop(topPanel);

        setupTable();
        root.setCenter(table);

        FlowPane bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        loadDataFromCollectionManager();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startLogin() {
        this.user = (new LoginScreen()).start();
    }


    private BorderPane createTopPanel() {
        BorderPane topPanel = new BorderPane();

        HBox filterPanel = new HBox(10);
        filterPanel.setPadding(new Insets(10));
        filterPanel.setAlignment(Pos.CENTER_RIGHT);
        Label filterLabel = new Label("Filter:");
        filterField = new TextField();
        filterField.setPromptText("Type to filter...");

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> loadDataFromCollectionManager());
        filterPanel.getChildren().addAll(filterLabel, filterField, refreshButton);


        HBox userPanel = new HBox(10);
        userPanel.setPadding(new Insets(10));
        userPanel.setAlignment(Pos.CENTER_LEFT);
        Label userLabel = new Label((user == null) ? "Unregistered" : user.getLogin());
        if (user == null) {
            Button registerButton = new Button("Register/Login");
            userPanel.getChildren().addAll(userLabel, registerButton);
        } else {
            userPanel.getChildren().add(userLabel);
        }
        topPanel.setLeft(userPanel);
        topPanel.setRight(filterPanel);
        return topPanel;
    }

    private void setupTable() {
        TableColumn<DragonDisplayWrapper, String> keyCol = new TableColumn<>("Key");
        keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<DragonDisplayWrapper, String> ownerCol = new TableColumn<>("Owner");
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));

        TableColumn<DragonDisplayWrapper, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DragonDisplayWrapper, Double> xCol = new TableColumn<>("X");
        xCol.setCellValueFactory(new PropertyValueFactory<>("x"));

        TableColumn<DragonDisplayWrapper, Long> yCol = new TableColumn<>("Y");
        yCol.setCellValueFactory(new PropertyValueFactory<>("y"));

        TableColumn<DragonDisplayWrapper, LocalDate> dateCol = new TableColumn<>("Creation Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        TableColumn<DragonDisplayWrapper, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<DragonDisplayWrapper, String> colorCol = new TableColumn<>("Color");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn<DragonDisplayWrapper, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<DragonDisplayWrapper, String> charCol = new TableColumn<>("Character");
        charCol.setCellValueFactory(new PropertyValueFactory<>("character"));

        TableColumn<DragonDisplayWrapper, Integer> depthCol = new TableColumn<>("Cave Depth");
        depthCol.setCellValueFactory(new PropertyValueFactory<>("depth"));

        TableColumn<DragonDisplayWrapper, Double> treasuresCol = new TableColumn<>("Treasures");
        treasuresCol.setCellValueFactory(new PropertyValueFactory<>("treasures"));

        table.getColumns().addAll(keyCol, ownerCol, nameCol, xCol, yCol, dateCol, ageCol, colorCol, typeCol, charCol, depthCol, treasuresCol);

        FilteredList<DragonDisplayWrapper> filteredData = new FilteredList<>(masterData, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dragonWrapper -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                return dragonWrapper.getStreamOfFields()
                        .anyMatch(field -> field.toLowerCase().contains(lowerCaseFilter));
            });
        });


        SortedList<DragonDisplayWrapper> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private FlowPane createBottomPanel() {
        FlowPane bottomPanel = new FlowPane();
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setHgap(10);
        bottomPanel.setVgap(10);
        bottomPanel.setAlignment(Pos.CENTER);

        String[] commandNames = {"info", "show", "insert", "update", "remove_key", "remove_greater", "replace_if_lower"};

        for (String commandName : commandNames) {
            Button btn = new Button(commandName.replace('_', ' '));
            btn.setOnAction(e -> handleCommand(commandName));
            bottomPanel.getChildren().add(btn);
        }
        return bottomPanel;
    }


    private void handleCommand(String commandName) {

        System.out.println("Executing command: " + commandName);

        switch (commandName) {

            case "insert":
                DragonFormScreen insertDialog = new DragonFormScreen();
                Map.Entry<String, Dragon> newEntry = insertDialog.getNewDragon();
                if (newEntry != null) {
                    newEntry.getValue().setOwnerLogin(user.getLogin());
                    String response = commandsManager.insertDragon(newEntry, user);
                    showAlert(Alert.AlertType.INFORMATION, "Execution result", response);
                    collectionManager.sync();
                    loadDataFromCollectionManager();
                }
                break;

            case "update":
                DragonDisplayWrapper selectedForUpdate = table.getSelectionModel().getSelectedItem();
                if (selectedForUpdate.getOwner().equals(user.getLogin())) {
                    DragonFormScreen updateDialog = new DragonFormScreen();
                    Dragon newDragon = updateDialog.updateDragon(selectedForUpdate);
                    if (newDragon != null) {
                        collectionManager.replaceElement(selectedForUpdate.getKey(), newDragon);
                        String response = commandsManager.updateDragon(Map.entry(selectedForUpdate.getKey(), newDragon), user);
                        showAlert(Alert.AlertType.INFORMATION, "Execution result", response);
                        collectionManager.sync();
                        loadDataFromCollectionManager();
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Bruh", "Sorry, but you can not modify someones dragon");
                }
                break;

            case "replace_if_lower":
                DragonDisplayWrapper selectedForReplace = table.getSelectionModel().getSelectedItem();
                if (selectedForReplace.getOwner().equals(user.getLogin())) {
                    DragonFormScreen updateDialog = new DragonFormScreen();
                    Dragon newDragon = updateDialog.updateDragon(selectedForReplace);
                    if (newDragon != null) {
                        collectionManager.replaceElement(selectedForReplace.getKey(), newDragon);
                        String response = commandsManager.replaceIfLowerDragon(Map.entry(selectedForReplace.getKey(), newDragon), user);
                        showAlert(Alert.AlertType.INFORMATION, "Execution result", response);
                        collectionManager.sync();
                        loadDataFromCollectionManager();
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Bruh", "Sorry, but you can not modify someones dragon");
                }
                break;

            case "remove_key":
                DragonDisplayWrapper selectedForRemove = table.getSelectionModel().getSelectedItem();
                if (selectedForRemove.getOwner().equals(user.getLogin())) {
                    String response = commandsManager.removeKeyDragon(new String[]{selectedForRemove.getKey()}, user);
                    showAlert(Alert.AlertType.INFORMATION, "Execution result", response);
                    collectionManager.sync();
                    loadDataFromCollectionManager();

                } else {
                    showAlert(Alert.AlertType.ERROR, "Bruh", "Sorry, but you can not modify someones dragon");
                }
                break;
            case "remove_greater":
                DragonDisplayWrapper selectedForRemoveGr = table.getSelectionModel().getSelectedItem();
                if (selectedForRemoveGr.getOwner().equals(user.getLogin())) {
                    String response = commandsManager.removeKeyGrDragon(new String[]{selectedForRemoveGr.getKey()}, user);
                    showAlert(Alert.AlertType.INFORMATION, "Execution result", response);
                    collectionManager.sync();
                    loadDataFromCollectionManager();

                } else {
                    showAlert(Alert.AlertType.ERROR, "Bruh", "Sorry, but you can not modify someones dragon");
                }
                break;

            case "info":
                collectionManager.sync();
                Map<String, Object> info = collectionManager.getCollectionInfoMap();
                showAlert(Alert.AlertType.INFORMATION, "Collection Info",
                        "Type: " + info.get("Type") + "\n" +
                                "Initialization Date: " + info.get("Date") + "\n" +
                                "Number of elements: " + info.get("ElementsQuantity"));
                break;

            case "show":
            default:
                System.out.println("Displaying current collection state.");
                loadDataFromCollectionManager();
                break;
        }
    }

    private void loadDataFromCollectionManager() {
        masterData.clear();
        Map<String, Dragon> collection = collectionManager.getCollection();
        for (Map.Entry<String, Dragon> entry : collection.entrySet()) {
            masterData.add(new DragonDisplayWrapper(entry.getKey(), entry.getValue()));
        }
        table.sort();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Вспомогательный класс-обертка для удобного отображения в TableView.
     * Он "уплощает" объект Dragon, делая его поля доступными через простые геттеры.
     */

}

