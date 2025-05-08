# Budget-Tracker
Final project for OOP Spring 2025 - Budget Tracker app

## Team Members:
- Sajid Rahman (sr6068)
- Shamaamah Ahmad (sa6477)

## Description of Project:
The **Budget Tracker** is a GUI-based application designed to help users track personal income and expenses.
It addresses the need for a simple, offline tool to manage budgets without relying on complex spreadsheets or 
online financial services. The primary users are individuals who want to monitor and manage their spending effectively. 
All data will be **persisted locally using files,** allowing users to retrieve and review their financial records over
time.

## Features:
### General User:
Anyone using the application will have access to the following features:
- **Add Entry:** Add an income or expense by specifying amount, category, date, and description.
- **Modify Entry:** Edit or update existing income or expense entries.
- **Delete Entry:** Remove entries that are no longer needed.
- **View All Entries:** Browse a list of all recorded transactions.
- **Sort Entries:** Sort by date, category, or amount for better readability and analysis.
- **Filter Transactions:** Filter entries based on name, amount, or date.
- **Search Entries:** Search by keywords or category to locate specific transactions.
- **Summary View:** Generate summaries of income vs. expenses on a monthly or total basis.
- **Download Reports:** Export budget summaries as a text file or other suitable format.
- **Data Persistence:** All transactions will be saved locally in a file (CSV), so users can retrieve them when they 
reopen the application. 

### Java Version
Java 21

### Setup
#### Option 1 (Manual Download):
1) Download JavaFX SDK 21 from https://gluonhq.com/products/javafx/
2) Add the JavaFX SDK `lib` folder as a Library in IntelliJ.
3) In Run Configurations, add VM options:
  --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
#### Option 2 (Preferred MAVEN):
1) Make sure Maven is installed, run ```mvn -v``` to check 
2) If Maven not installed run ```brew --version``` to check if homebrew is installed
3) If Homebrew is not installed:
   1) Run this to install Homebrew:
       ```/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"```
   2) Run this to install Maven:
     ```brew install maven```
   3) Check if successful with ```mvn -v```
4) Navigate to project directory
5) Compile project with ```mvn compile```
6) After compilation run project ```mvn javafx:run```

## Features Checklist:
| Feature | Status     |
|---------|------------|
| Add Entry | ✅ Complete |
| Modify Entry | ✅ Complete |
| Delete Entry | ✅ Complete |
| View All Entries | ✅ Complete |
| Sort Entries (by date, category, amount) | ✅ Complete |
| Filter Transactions (by name, amount, date) | ✅ Complete  |
| Search Entries (by keywords or category) | ✅ Complete |
| Summary View (income vs expenses) | ✅ Complete |
| Download Reports (export as CSV) | ✅ Complete  |
| Data Persistence (save to CSV) | ✅ Complete |
