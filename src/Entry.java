import java.time.LocalDate;

public class Entry {
    private double amount;
    private LocalDate date;
    private String category;
    private String description;
    private String type; // "Income" or "Expense"

    public Entry(double amount, LocalDate date, String category, String description, String type) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    // Getters
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getType() { return type; }

    // Setters (optional, for modify functionality)
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
}