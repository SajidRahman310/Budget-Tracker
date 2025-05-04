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

    public void saveEntries() {
        try (PrintWriter writer = new PrintWriter(new File("data/entries.csv"))) {
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
            System.out.println("Error saving entries: " + e.getMessage());
        }
    }

    public void loadEntries() {
        File file = new File("data/entries.csv");
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                double amount = Double.parseDouble(data[0]);
                LocalDate date = LocalDate.parse(data[1]);
                String category = data[2];
                String description = data[3];
                String type = data[4];
                entries.add(new Entry(amount, date, category, description, type));
            }
        } catch (Exception e) {
            System.out.println("Error loading entries: " + e.getMessage());
        }
    }
}