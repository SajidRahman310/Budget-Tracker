public class Summary {
    private double totalIncome;
    private double totalExpense;

    public Summary(double totalIncome, double totalExpense) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getBalance() {
        return totalIncome - totalExpense;
    }
}