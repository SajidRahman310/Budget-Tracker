import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private BudgetManager budgetManager = new BudgetManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget Tracker");

        //Title
        Label title = new Label("Welcome to Budget Tracker!");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        //Summary label
        Summary summary = budgetManager.calculateSummary();
        Label summaryLabel = new Label(
                 "Total Income: $" + summary.getTotalIncome() +
                    " | Total Expense: $" + summary.getTotalExpense() +
                    " | Balance: $" + summary.getBalance()
        );

        //Buttons
        Button addEntryButton = new Button("Add Entry");
        Button viewEntryButton = new Button("View All Entries");

        // TODO: Add action handlers for buttons later

        //Layout
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().addAll(title, summaryLabel, addEntryButton, viewEntryButton);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}