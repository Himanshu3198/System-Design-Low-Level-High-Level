package StockBrokerageSystem;

import java.time.LocalDateTime;
import java.util.*;

// ---------- ENTITIES ----------

class User {
    long id;
    String name;
    Wallet wallet;
    Portfolio portfolio;

    public User(long id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.wallet = new Wallet(this, balance);
        this.portfolio = new Portfolio(this);
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
        System.out.println("Wallet balance: " + balance);
    }

    public void addAmount(double amount) {
        balance += amount;
    }
}

class Stock {
    long id;
    String symbol;
    double price;

    public Stock(long id, String symbol, double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }
}

class Order {
    long id;
    User user;
    Stock stock;
    int quantity;
    double price;
    String type; // BUY or SELL
    String status = "PENDING";

    public Order(long id, User user, Stock stock, int quantity, double price, String type) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }
}

class Trade {
    long id;
    Order order;
    LocalDateTime timestamp;

    public Trade(long id, Order order) {
        this.id = id;
        this.order = order;
        this.timestamp = LocalDateTime.now();
    }
}

class Position {
    Stock stock;
    int quantity;
    double avgPrice;

    public Position(Stock stock) {
        this.stock = stock;
        this.quantity = 0;
        this.avgPrice = 0.0;
    }

    public void update(int qty, double price, String type) {
        if(type.equals("BUY")) {
            int totalQty = this.quantity + qty;
            this.avgPrice = (this.avgPrice * this.quantity + price * qty) / totalQty;
            this.quantity = totalQty;
        } else if(type.equals("SELL")) {
            if(qty > this.quantity) throw new RuntimeException("Not enough shares to sell");
            this.quantity -= qty;
            if(this.quantity == 0) this.avgPrice = 0.0;
        }
    }

    @Override
    public String toString() {
        return stock.symbol + ": " + quantity + " shares @ " + avgPrice;
    }
}

class Portfolio {
    User user;
    Map<Long, Position> positions = new HashMap<>(); // stockId -> Position

    public Portfolio(User user) {
        this.user = user;
    }

    public void updatePosition(Stock stock, int qty, double price, String type) {
        Position pos = positions.getOrDefault(stock.id, new Position(stock));
        pos.update(qty, price, type);
        if(pos.quantity == 0) positions.remove(stock.id);
        else positions.put(stock.id, pos);
    }

    public void showPositions() {
        System.out.println("Portfolio for " + user.name + ":");
        if(positions.isEmpty()) System.out.println("No holdings.");
        else positions.values().forEach(System.out::println);
    }
}

// ---------- SERVICES ----------

class OrderService {
    private List<Trade> trades = new ArrayList<>();
    private long tradeCounter = 1;

    public Order placeOrder(Order order) {
        if(order.type.equals("BUY")) {
            double total = order.quantity * order.price;
            order.user.wallet.deductFunds(total);
        } else if(order.type.equals("SELL")) {
            Position pos = order.user.portfolio.positions.get(order.stock.id);
            if(pos == null || pos.quantity < order.quantity)
                throw new RuntimeException("Not enough shares to sell");
        }

        executeTrade(order);
        return order;
    }

    private void executeTrade(Order order) {
        Trade trade = new Trade(tradeCounter++, order);
        trades.add(trade);
        order.status = "EXECUTED";
        System.out.println("Trade executed: " + order.quantity + " " + order.stock.symbol + " @ " + order.price + " (" + order.type + ")");

        // Update user portfolio
        order.user.portfolio.updatePosition(order.stock, order.quantity, order.price, order.type);

        // Adjust wallet for SELL
        if(order.type.equals("SELL")) {
            double total = order.quantity * order.price;
            order.user.wallet.addAmount(total);
        }
    }
}

// ---------- MAIN SIMULATION ----------

public class StockBrokerageDemo {
    public static void main(String[] args) {
        System.out.println("==Zerodha Application==");
        // Users
        User user = new User(1, "Himanshu", 10000);

        // Stocks
        Stock tcs = new Stock(1, "TCS", 3500);
        Stock infy = new Stock(2, "INFY", 1500);

        // Service
        OrderService orderService = new OrderService();

        // Add Funds
        user.wallet.addFunds(5000);

        // Place BUY Orders
        orderService.placeOrder(new Order(1, user, tcs, 2, 3500, "BUY"));
        orderService.placeOrder(new Order(2, user, infy, 3, 1500, "BUY"));

        // Show Portfolio
        System.out.println();
        user.portfolio.showPositions();

        // Place SELL Order
        orderService.placeOrder(new Order(3, user, infy, 1, 1600, "SELL"));

        // Show updated Portfolio
        System.out.println();
        user.portfolio.showPositions();

        // Show Wallet balance
        System.out.println("\nWallet balance: " + user.wallet.balance);
    }
}
