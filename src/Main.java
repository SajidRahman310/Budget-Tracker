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
        addEntryButton.setOnAction(event -> showAddEntryWindow());
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


    private void showAddEntryWindow() {
        Stage addStage = new Stage();
        addStage.setTitle("Add New Entry");

        // Form fields
        javafx.scene.control.TextField amountField = new javafx.scene.control.TextField();
        amountField.setPromptText("Amount");

        javafx.scene.control.ComboBox<String> typeBox = new javafx.scene.control.ComboBox<>();
        typeBox.getItems().addAll("Income", "Expense");
        typeBox.setPromptText("Type");

        javafx.scene.control.TextField categoryField = new javafx.scene.control.TextField();
        categoryField.setPromptText("Category");

        javafx.scene.control.DatePicker datePicker = new javafx.scene.control.DatePicker();
        datePicker.setPromptText("Date");

        javafx.scene.control.TextField descriptionField = new javafx.scene.control.TextField();
        descriptionField.setPromptText("Description");

        javafx.scene.control.Button saveBtn = new javafx.scene.control.Button("Save");

        // Save button action
        saveBtn.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String type = typeBox.getValue();
                String category = categoryField.getText();
                String description = descriptionField.getText();
                java.time.LocalDate date = datePicker.getValue();

                if (type == null || category.isEmpty() || date == null) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }

                Entry newEntry = new Entry(amount, date, category, description, type);
                budgetManager.addEntry(newEntry);
                addStage.close();
            } catch (Exception ex) {
                System.out.println("Error adding entry: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(amountField, typeBox, categoryField, datePicker, descriptionField, saveBtn);

        Scene scene = new Scene(layout, 300, 300);
        addStage.setScene(scene);
        addStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}