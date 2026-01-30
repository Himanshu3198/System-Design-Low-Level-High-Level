package DesignUPI;

import java.time.LocalDateTime;
import java.util.*;

// ---------- ENTITIES ----------

class User {
    long id;
    String name;
    Wallet wallet;
    UPIAccount upiAccount;

    public User(long id, String name, double walletBalance) {
        this.id = id;
        this.name = name;
        this.wallet = new Wallet(this, walletBalance);
        this.upiAccount = new UPIAccount(this);
    }
}

class Wallet {
    User user;
    double balance;

    public Wallet(User user, double balance) {
        this.user = user;
        this.balance = balance;
    }

    public void addFunds(double amount) {
        balance += amount;
        System.out.println("Wallet balance: " + balance);
    }

    public void deductFunds(double amount) {
        if(balance < amount) throw new RuntimeException("Insufficient funds");
        balance -= amount;
    }
}

class BankAccount {
    long accountId;
    String bankName;
    String accountNumber;
    double balance;

    public BankAccount(long accountId, String bankName, String accountNumber, double balance) {
        this.accountId = accountId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void debit(double amount) {
        if(balance < amount) throw new RuntimeException("Bank: Insufficient funds");
        balance -= amount;
    }

    public void credit(double amount) {
        balance += amount;
    }
}

class UPIAccount {
    String upiId;
    User user;
    List<BankAccount> linkedAccounts;

    public UPIAccount(User user) {
        this.user = user;
        this.linkedAccounts = new ArrayList<>();
        this.upiId = user.name.toLowerCase() + "@upi";
    }

    public void linkBankAccount(BankAccount bankAccount) {
        linkedAccounts.add(bankAccount);
    }

    public BankAccount getPrimaryBankAccount() {
        if(linkedAccounts.isEmpty()) throw new RuntimeException("No bank account linked");
        return linkedAccounts.get(0);
    }
}

class Transaction {
    long id;
    UPIAccount fromUPI;
    UPIAccount toUPI;
    double amount;
    String status; // PENDING, SUCCESS, FAILED
    LocalDateTime timestamp;

    public Transaction(long id, UPIAccount fromUPI, UPIAccount toUPI, double amount) {
        this.id = id;
        this.fromUPI = fromUPI;
        this.toUPI = toUPI;
        this.amount = amount;
        this.status = "PENDING";
        this.timestamp = LocalDateTime.now();
    }
}

// ---------- SERVICES ----------

class NotificationService {
    private static NotificationService instance = new NotificationService();
    private NotificationService() {}
    public static NotificationService getInstance() { return instance; }

    public void notify(User user, String message) {
        System.out.println("Notification for " + user.name + ": " + message);
    }
}

class TransactionService {
    private long transactionCounter = 1;
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction sendMoney(UPIAccount fromUPI, UPIAccount toUPI, double amount) {
        Transaction tx = new Transaction(transactionCounter++, fromUPI, toUPI, amount);

        try {
            // Debit from wallet first (if balance >= amount)
            if(fromUPI.user.wallet.balance >= amount) {
                fromUPI.user.wallet.deductFunds(amount);
            } else {
                // Debit primary bank account
                fromUPI.getPrimaryBankAccount().debit(amount);
            }

            // Credit to payee wallet
            toUPI.user.wallet.addFunds(amount);

            tx.status = "SUCCESS";
        } catch (RuntimeException e) {
            tx.status = "FAILED";
            System.out.println("Transaction failed: " + e.getMessage());
        }

        transactions.add(tx);

        // Notify users
        NotificationService.getInstance().notify(fromUPI.user,
                "Transaction " + tx.status + ": Sent ₹" + amount + " to " + toUPI.upiId);
        NotificationService.getInstance().notify(toUPI.user,
                "Transaction " + tx.status + ": Received ₹" + amount + " from " + fromUPI.upiId);

        return tx;
    }

    public void showTransactions() {
        System.out.println("\nTransaction History:");
        transactions.forEach(tx -> {
            System.out.println("ID: " + tx.id + ", From: " + tx.fromUPI.upiId +
                    ", To: " + tx.toUPI.upiId + ", Amount: " + tx.amount + ", Status: " + tx.status);
        });
    }
}

// ---------- MAIN SIMULATION ----------

public class UPIDEMO{
    public static void main(String[] args) {
        // Users
        User himanshu = new User(1, "Himanshu", 1000);
        User rohit = new User(2, "Rohit", 500);

        // Bank accounts
        BankAccount himanshuBank = new BankAccount(1, "SBI", "SBI001", 5000);
        BankAccount rohitBank = new BankAccount(2, "HDFC", "HDFC001", 3000);

        himanshu.upiAccount.linkBankAccount(himanshuBank);
        rohit.upiAccount.linkBankAccount(rohitBank);

        // Transaction service
        TransactionService txService = new TransactionService();

        // Send money
        txService.sendMoney(himanshu.upiAccount, rohit.upiAccount, 800);
        txService.sendMoney(rohit.upiAccount, himanshu.upiAccount, 600); // should fail, wallet only 500

        // Show final balances
        System.out.println("\nFinal Wallet Balances:");
        System.out.println(himanshu.name + ": " + himanshu.wallet.balance);
        System.out.println(rohit.name + ": " + rohit.wallet.balance);

        // Transaction history
        txService.showTransactions();
    }
}
