import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private BudgetManager budgetManager = new BudgetManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget Tracker");

        Label welcomeLabel = new Label("Welcome to Budget Tracker!");
        StackPane root = new StackPane();
        root.getChildren().add(welcomeLabel);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}