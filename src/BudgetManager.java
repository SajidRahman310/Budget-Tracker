import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class BudgetManager {
    private List<Entry> entries;

    public BudgetManager() {
        entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public void modifyEntry(int index, Entry newEntry) {
        if (index >= 0 && index < entries.size()) {
            entries.set(index, newEntry);
        }
    }

    public void deleteEntry(int index) {
        if (index >= 0 && index < entries.size()) {
            entries.remove(index);
        }
    }

    public List<Entry> getAllEntries() {
        return entries;
    }

    public List<Entry> filterEntries(String category, String type, double minAmount, double maxAmount) {
        List<Entry> filtered = new ArrayList<>();
        for (Entry entry : entries) {
            boolean matches = (category == null || entry.getCategory().equalsIgnoreCase(category)) &&
                    (type == null || entry.getType().equalsIgnoreCase(type)) &&
                    (entry.getAmount() >= minAmount && entry.getAmount() <= maxAmount);
            if (matches) {
                filtered.add(entry);
            }
        }
        return filtered;
    }

    public void sortEntriesByAmount() {
        entries.sort(Comparator.comparingDouble(Entry::getAmount));
    }

    public void sortEntriesByDate() {
        entries.sort(Comparator.comparing(Entry::getDate));
    }

    public Summary calculateSummary() {
        double income = 0;
        double expense = 0;
        for (Entry e : entries) {
            if (e.getType().equalsIgnoreCase("Income")) {
                income += e.getAmount();
            } else {
                expense += e.getAmount();
            }
        }
        return new Summary(income, expense);
    }

    public void exportReport(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("Amount,Date,Category,Description,Type");
            for (Entry e : entries) {
                writer.printf("%f,%s,%s,%s,%s\n",
                        e.getAmount(),
                        e.getDate().toString(),
                        e.getCategory(),
                        e.getDescription(),
                        e.getType());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
}