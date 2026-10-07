public class Currency {
    private int balance;

    public Currency() {
        this.balance = 100;
    }

    public Currency(int amount) {
        this.balance = amount;
    }

    public int getBalance() {
        return this.balance;
    }

    public void deposit(int amount) {
        if (amount < 0) throw new IllegalArgumentException(Ansi.BRIGHT_RED + "Cannot deposit negative amount." + Ansi.RESET);
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (amount < 0) throw new IllegalArgumentException(Ansi.BRIGHT_RED + "Cannot withdraw negative amount." + Ansi.RESET);
        if (amount > this.balance) throw new IllegalArgumentException(Ansi.BRIGHT_RED + "Insufficient funds." + Ansi.RESET);
        this.balance -= amount;
    }

    public void setBalance(int amount) {
        this.balance = amount;
    }
}
