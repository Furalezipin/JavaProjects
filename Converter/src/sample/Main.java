package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Set;

public class Main extends Application {

    private TextField tf_value;
    private ChoiceBox<String> cb_dimension;
    private TabPane tabPane;

    private DimenData dimenWeight;
    private DimenData dimenDistance;
    private DimenData dimenAmount;
    private DimenData dimenCurrency;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        createDimenData();
        createUI(root);
        createLogic();

        primaryStage.setTitle("Конвертер величин");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void createUI (Group root) {
        tf_value = new TextField("0");

        cb_dimension = new ChoiceBox<String>();
        setChoiseBoxData(dimenWeight.getDimenTitles());

        Tab tabWeight = new Tab(dimenWeight.getTitle());
        tabWeight.setContent(createTabUI(dimenWeight));

        Tab tabDistance = new Tab(dimenDistance.getTitle());
        tabDistance.setContent(createTabUI(dimenDistance));

        Tab tabAmount = new Tab(dimenAmount.getTitle());
        tabAmount.setContent(createTabUI(dimenAmount));

        Tab tabCurrency = new Tab(dimenCurrency.getTitle());
        tabCurrency.setContent(createTabUI(dimenCurrency));

        tabPane = new TabPane();
        tabPane.getTabs().addAll(tabWeight, tabDistance, tabAmount, tabCurrency);

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(tf_value, cb_dimension);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, tabPane);

        root.getChildren().addAll(vBox);
    }

    public Group createTabUI(DimenData data) {
        Group group = new Group();
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2);

        for (String title : data.getDimenTitles()) {
            tilePane.getChildren().addAll(new Label(title), data.getLabel(title));
        }
        group.getChildren().addAll(tilePane);
        return group;
    }

    public void createLogic() {

        tf_value.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calculate();
            }
        });

        cb_dimension.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                calculate();
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                switch (newValue.getText()) {
                    case "Вес":
                        setChoiseBoxData(dimenWeight.getDimenTitles());
                        break;
                    case "Расстояние":
                        setChoiseBoxData(dimenDistance.getDimenTitles());
                        break;
                    case "Объем":
                        setChoiseBoxData(dimenAmount.getDimenTitles());
                        break;
                    case "Валюта":
                        setChoiseBoxData(dimenCurrency.getDimenTitles());
                        break;
                }
            }
        });

    }

    public void createDimenData() {

        dimenWeight = new DimenData("Вес");
        dimenWeight.addDimension("грамм", 1);
        dimenWeight.addDimension("килограмм", 1000);
        dimenWeight.addDimension("тонна", 1000000);
        dimenWeight.addDimension("фунт", 456.3);

        dimenDistance = new DimenData("Расстояние");
        dimenDistance.addDimension("миллиметр", 1);
        dimenDistance.addDimension("сантиметр", 10);
        dimenDistance.addDimension("метр", 100);
        dimenDistance.addDimension("ярд", 914.4);

        dimenAmount = new DimenData("Объем");
        dimenAmount.addDimension("см3", 1);
        dimenAmount.addDimension("литр", 1000);
        dimenAmount.addDimension("км3", 1000000000000.0);
        dimenAmount.addDimension("ярд", 3785);

        dimenCurrency = new DimenData("Валюта");
        dimenCurrency.addDimension("Рубль", 1);
        dimenCurrency.addDimension("Доллар", 55);
        dimenCurrency.addDimension("Евро", 65);
        dimenCurrency.addDimension("Гривна", 4);

    }

    public void setChoiseBoxData(Set<String> data) {
        ObservableList<String> dimenList = FXCollections.observableArrayList();
        for (String title : data) {
            dimenList.add(title);
        }
        cb_dimension.setItems(dimenList);
        cb_dimension.getSelectionModel().select(0);
    }

    public void calculate() {
        switch (tabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Вес":
                dimenWeight.calculate(Double.parseDouble(tf_value.getText()), cb_dimension.getValue());
                break;
            case "Расстояние":
                dimenDistance.calculate(Double.parseDouble(tf_value.getText()), cb_dimension.getValue());
                break;
            case "Объем":
                dimenAmount.calculate(Double.parseDouble(tf_value.getText()), cb_dimension.getValue());
                break;
            case "Валюта":
                dimenCurrency.calculate(Double.parseDouble(tf_value.getText()), cb_dimension.getValue());
                break;
        }
    }
}
