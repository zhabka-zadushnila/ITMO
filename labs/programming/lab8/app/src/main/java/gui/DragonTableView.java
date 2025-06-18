package gui;

import java.time.LocalDate;
import java.util.Map;

import gui.utils.DragonDisplayWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import managers.CollectionManager;
import structs.classes.Color;
import structs.classes.Coordinates;
import structs.classes.Dragon;
import structs.classes.DragonCave;
import structs.classes.DragonCharacter;
import structs.classes.DragonType;

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
    private TableView<DragonDisplayWrapper> table = new TableView<>();
    private TextField filterField;

    public static void initialize(CollectionManager cm) {
        collectionManager = cm;
    }

    @Override
    public void start(Stage primaryStage) {
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

        HBox topPanel = createTopPanel();
        root.setTop(topPanel);

        setupTable();
        root.setCenter(table);

        FlowPane bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        Pane visualPane = createRightPannel(primaryStage); 
        visualPane.prefWidthProperty().bind(root.widthProperty().multiply(0.475));
        visualPane.prefHeightProperty().bind(root.heightProperty());
        visualPane.setStyle(
            "-fx-border-color: #333333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 4;" +
            "-fx-background-color: white;"
        );


        Label title = new Label("Область визуализации");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 14));


        VBox rightBox = new VBox(5);
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.getChildren().addAll(title, visualPane);

        BorderPane.setMargin(rightBox, new Insets(0, 0, 0, 10)); 

        root.setRight(rightBox);

        loadDataFromCollectionManager();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopPanel() {
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER_LEFT);

        Label filterLabel = new Label("Filter:");
        filterField = new TextField();
        filterField.setPromptText("Type to filter...");

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> loadDataFromCollectionManager());

        topPanel.getChildren().addAll(filterLabel, filterField, refreshButton);
        return topPanel;
    }

    private Pane createRightPannel(Stage primaryStage){
        Pane pane = new Pane();
        Map<String, Dragon> collection = collectionManager.getCollection();
        for (Map.Entry<String, Dragon> entry : collection.entrySet()) {
            Dragon dragon = entry.getValue();
            Node visualisation = getVisualisation(dragon, primaryStage);
            pane.getChildren().add(visualisation);
        }
        return pane;
    }


    private Node getVisualisation(Dragon dragon, Stage primaryStage){
        DragonType type = dragon.getType();
        String ownersLogin = dragon.getOwnerLogin();
        javafx.scene.paint.Color color = getColorByOwner(ownersLogin);
        double x = dragon.getCoordinates().getX();
        double y = dragon.getCoordinates().getY();
        double correctX = ((101*x) % 500) + 50;
        double correctY = (y % 600);
        switch(type){
            case FIRE -> {
            Circle figure = new Circle(correctX, correctY, 15);
            figure.setFill(color);
            attachInfoHandler(figure, dragon, primaryStage);
            return figure;
        }
        case AIR -> {
            Polygon figure = new Polygon();
            figure.getPoints().addAll(
                correctX, correctY - 20,
                correctX - 15, correctY + 15,
                correctX + 15, correctY + 15
            );
            figure.setFill(color);
            attachInfoHandler(figure, dragon, primaryStage);
            return figure;
        }
        case WATER -> {
            Polygon figure = new Polygon();
            figure.getPoints().addAll(
                correctX, correctY - 20,
                correctX - 15, correctY,
                correctX, correctY + 20,
                correctX + 15, correctY
            );
            figure.setFill(color);
            attachInfoHandler(figure, dragon, primaryStage);
            return figure;

        }
        case UNDERGROUND -> {
            Rectangle figure = new Rectangle(correctX - 15, correctY - 15, 30, 30);
            figure.setFill(color);
            attachInfoHandler(figure, dragon, primaryStage);
            return figure;
        }default ->{
            Circle figure = new Circle();
            figure.setFill(color);
            attachInfoHandler(figure, dragon, primaryStage);
            return figure;
        }
        }


    }

    private javafx.scene.paint.Color getColorByOwner(String login){
        int hash = Math.abs(login.hashCode());
        double hue = (hash*44) % 360;
        double saturation = 0.7;
        double brightness = 0.9;
        return javafx.scene.paint.Color.hsb(hue, saturation, brightness);
    }

    private void attachInfoHandler(Node node, Dragon dragon, Stage primaryStage) {
        node.setOnMouseClicked(event -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.initOwner(primaryStage);
            info.setTitle("Информация о драконе");
            info.setHeaderText(dragon.getName() + " (" + dragon.getOwnerLogin() + ")");
            StringBuilder content = new StringBuilder();
            content.append("Имя: ").append(dragon.getName()).append("\n")
                   .append("Координаты: (")
                    .append(dragon.getCoordinates().getX()).append(", ")
                    .append(dragon.getCoordinates().getY()).append(")\n")
                   .append("Возраст: ").append(dragon.getAge()).append("\n")
                   .append("Тип: ").append(dragon.getType()).append("\n")
                   .append("Характер: ").append(dragon.getCharacter());
            if (dragon.getCave() != null) {
                content.append("\nПещера: ")
                       .append(dragon.getCave().getNumberOfTreasures())
                       .append(" сокровищ");
            }
            info.setContentText(content.toString());
            info.showAndWait();
        });
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
        bottomPanel.setPadding(new Insets(10, 10, 10, 120));
        bottomPanel.setHgap(10);
        bottomPanel.setVgap(10);
        bottomPanel.setAlignment(Pos.CENTER_LEFT);

        String[] commandNames = {"info", "show", "insert", "update", "remove_key", "clear", "remove_greater", "replace_if_lower"};

        for (String commandName : commandNames) {
            Button btn = new Button(commandName.replace('_', ' '));
            //btn.setOnAction(e -> handleCommand(commandName));
            bottomPanel.getChildren().add(btn);
        }
        return bottomPanel;
    }

    /*
    private void handleCommand(String commandName) {

        System.out.println("Executing command: " + commandName);

        switch (commandName) {
            case "insert":
                DragonFormDialog insertDialog = new DragonFormDialog();
                Optional<Dragon> insertResult = insertDialog.showAndWait();
                insertResult.ifPresent(dragon -> {

                    TextInputDialog keyDialog = new TextInputDialog("new_key");
                    keyDialog.setTitle("Insert Dragon");
                    keyDialog.setHeaderText("Enter the key for the new dragon.");
                    keyDialog.setContentText("Key:");
                    Optional<String> keyResult = keyDialog.showAndWait();
                    keyResult.ifPresent(key -> {
                        System.out.println("Executing insert with key: " + key + " and dragon: " + dragon.getName());
                        // collectionManager.addElement(key, dragon);
                        // loadDataFromCollectionManager();
                    });
                });
                break;

            case "update":
            case "replace_if_lower":
                DragonDisplayWrapper selectedForUpdate = table.getSelectionModel().getSelectedItem();
                if (selectedForUpdate == null) {
                    showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a dragon in the table to " + commandName + ".");
                    return;
                }
                DragonFormDialog updateDialog = new DragonFormDialog(selectedForUpdate.getOriginalDragon());
                Optional<Dragon> updateResult = updateDialog.showAndWait();
                updateResult.ifPresent(dragon -> {
                    System.out.println("Executing " + commandName + " on key: " + selectedForUpdate.getKey());
                    // collectionManager.replaceElement(selectedForUpdate.getKey(), dragon);
                    // loadDataFromCollectionManager();
                });
                break;

            case "remove_key":
            case "remove_greater":
                DragonDisplayWrapper selectedForKey = table.getSelectionModel().getSelectedItem();
                String initialKey = (selectedForKey != null) ? selectedForKey.getKey() : "";
                TextInputDialog removeDialog = new TextInputDialog(initialKey);
                removeDialog.setTitle("Input Required");
                removeDialog.setHeaderText("Enter the key for command: " + commandName);
                removeDialog.setContentText("Key:");
                Optional<String> removeResult = removeDialog.showAndWait();
                removeResult.ifPresent(key -> {
                    System.out.println("Executing " + commandName + " with key: " + key);
                    // collectionManager.killElement(key);
                    // loadDataFromCollectionManager();
                });
                break;

            case "clear":
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Confirm Clear");
                confirmDialog.setHeaderText("Are you sure you want to clear the entire collection?");
                confirmDialog.setContentText("This action cannot be undone.");
                Optional<ButtonType> confirmResult = confirmDialog.showAndWait();
                if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                    System.out.println("Executing clear...");
                    // collectionManager.clearCollection();
                    // loadDataFromCollectionManager();
                }
                break;

            case "info":
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
    */
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

