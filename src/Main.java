import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class Main extends Application {

    private BudgetManager budgetManager = new BudgetManager();
    private Label summaryLabel;

    @Override

    //home route
    public void start(Stage primaryStage) {
        budgetManager.loadEntries();
        primaryStage.setTitle("Budget Tracker");

        //Title
        Label title = new Label("Welcome to Budget Tracker!");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        //Summary label
        Summary summary = budgetManager.calculateSummary();
        summaryLabel = new Label(
         "Total Income: $" + summary.getTotalIncome() +
            " | Total Expense: $" + summary.getTotalExpense() +
            " | Balance: $" + summary.getBalance()
        );

        //Buttons
        Button addEntryButton = new Button("Add Entry");
        addEntryButton.setOnAction(event -> showAddEntryWindow(null, null));
        Button viewEntryButton = new Button("View All Entries");
        viewEntryButton.setOnAction(event -> showAllEntriesWindow());

        // TODO: Add action handlers for buttons later

        //Layout
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().addAll(title, summaryLabel, addEntryButton, viewEntryButton);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //add entry route
    private void showAddEntryWindow(Entry entryToEdit, TableView<Entry> table) {
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


        if (entryToEdit != null) {
            amountField.setText(String.valueOf(entryToEdit.getAmount()));
            typeBox.setValue(entryToEdit.getType());
            categoryField.setText(entryToEdit.getCategory());
            datePicker.setValue(entryToEdit.getDate());
            descriptionField.setText(entryToEdit.getDescription());
        }

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

                if (entryToEdit != null) {
                    entryToEdit.setAmount(amount);
                    entryToEdit.setType(type);
                    entryToEdit.setCategory(category);
                    entryToEdit.setDate(date);
                    entryToEdit.setDescription(description);
                } else {
                    Entry newEntry = new Entry(amount, date, category, description, type);
                    budgetManager.addEntry(newEntry);
                }
                refreshSummary();
                budgetManager.saveEntries();
                if (table != null) {
                    table.getItems().clear();
                    table.getItems().addAll(budgetManager.getAllEntries());
                }
                addStage.close();
            } catch (Exception ex) {
                System.out.println("Error saving entry: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(amountField, typeBox, categoryField, datePicker, descriptionField, saveBtn);

        Scene scene = new Scene(layout, 300, 300);
        addStage.setScene(scene);
        addStage.show();
    }

    //view entries route
    private void showAllEntriesWindow() {
        Stage viewStage = new Stage();
        viewStage.setTitle("All Entries");

        javafx.scene.control.TableView<Entry> table = new javafx.scene.control.TableView<>();

        TextField searchField = new TextField();
        searchField.setPromptText("Search by category or description");

        TextField minAmountField = new TextField();
        minAmountField.setPromptText("Minimum Amount");

        TextField maxAmountField = new TextField();
        maxAmountField.setPromptText("Maximum Amount");

        Button filterBtn = new Button("Apply Filter");

        filterBtn.setOnAction(e -> {
            String keyword = searchField.getText().toLowerCase();
            String minAmountText = minAmountField.getText();
            String maxAmountText = maxAmountField.getText();

            double minAmount = 0;
            double maxAmount = Double.MAX_VALUE;

            try {
                if (!minAmountText.isEmpty()) minAmount = Double.parseDouble(minAmountText);
                if (!maxAmountText.isEmpty()) maxAmount = Double.parseDouble(maxAmountText);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid amount entered.");
            }

            final double minAmountFinal = minAmount;
            final double maxAmountFinal = maxAmount;
            List<Entry> filtered = budgetManager.getAllEntries().stream().filter(entry -> {
                boolean matchesKeyword = keyword.isEmpty() ||
                        entry.getCategory().toLowerCase().contains(keyword) ||
                        entry.getDescription().toLowerCase().contains(keyword);

                boolean matchesAmount = entry.getAmount() >= minAmountFinal && entry.getAmount() <= maxAmountFinal;

                return matchesKeyword && matchesAmount;
            }).toList();

            table.getItems().clear();
            table.getItems().addAll(filtered);
        });

        // Columns
        javafx.scene.control.TableColumn<Entry, Double> amountCol = new javafx.scene.control.TableColumn<>("Amount");
        amountCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("amount"));

        javafx.scene.control.TableColumn<Entry, String> dateCol = new javafx.scene.control.TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));

        javafx.scene.control.TableColumn<Entry, String> categoryCol = new javafx.scene.control.TableColumn<>("Category");
        categoryCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("category"));

        javafx.scene.control.TableColumn<Entry, String> descriptionCol = new javafx.scene.control.TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));

        javafx.scene.control.TableColumn<Entry, String> typeCol = new javafx.scene.control.TableColumn<>("Type");
        typeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));

        table.getColumns().addAll(amountCol, dateCol, categoryCol, descriptionCol, typeCol);

        // Load data into the table
        table.getItems().addAll(budgetManager.getAllEntries());

        // Delete Button
        Button deleteBtn = new Button("Delete Selected Entry");
        deleteBtn.setOnAction(e -> {
            Entry selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                budgetManager.getAllEntries().remove(selected);
                table.getItems().remove(selected);
                refreshSummary();
                budgetManager.saveEntries();
            }
        });

        // Edit Button (for simplicity, we'll just delete and let the user re-add) xxx (now it actually edits instead)
        Button editBtn = new Button("Edit Selected Entry");
        editBtn.setOnAction(e -> {
            Entry selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
//                budgetManager.getAllEntries().remove(selected);
//                table.getItems().remove(selected);
                showAddEntryWindow(selected, table);
                refreshSummary();
                budgetManager.saveEntries();
                table.getItems().clear();
                table.getItems().addAll(budgetManager.getAllEntries());
//                table.refresh();
//                showAddEntryWindow(); // Just re-use the Add Entry form for now
            }
        });

        Button exportBtn = new Button("Export Report");
        exportBtn.setOnAction(e -> {
            try (PrintWriter writer = new PrintWriter(new File("data/exported_report.csv"))) {
                writer.println("Amount,Date,Category,Description,Type");
                for (Entry entry : table.getItems()) {
                    writer.printf("%f,%s,%s,%s,%s\n",
                            entry.getAmount(),
                            entry.getDate().toString(),
                            entry.getCategory(),
                            entry.getDescription(),
                            entry.getType());
                }
                System.out.println("Report exported successfully.");
            } catch (Exception ex) {
                System.out.println("Error exporting report: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(
                searchField, minAmountField, maxAmountField, filterBtn,
                table, deleteBtn, editBtn, exportBtn
        );

        Scene scene = new Scene(layout, 600, 400);
        viewStage.setScene(scene);
        viewStage.show();
    }


    private void refreshSummary() {
        Summary summary = budgetManager.calculateSummary();
        summaryLabel.setText(
                "Total Income: $" + summary.getTotalIncome() +
                        " | Total Expense: $" + summary.getTotalExpense() +
                        " | Balance: $" + summary.getBalance()
        );
    }


    public static void main(String[] args) {
        launch(args);
    }
}