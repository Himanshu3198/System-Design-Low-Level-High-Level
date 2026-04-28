package lld.practice;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

// ── Enums ──────────────────────────────────────────────────────────────────
enum VehicleType { CAR, BIKE }

// ── Vehicle hierarchy ──────────────────────────────────────────────────────
abstract class Vehicle {
    private final String vehicleNumber;
    private final VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType   = vehicleType;
    }
    public String      getVehicleNumber() { return vehicleNumber; }
    public VehicleType getVehicleType()   { return vehicleType;   }
}

class Car  extends Vehicle { public Car (String n) { super(n, VehicleType.CAR);  } }
class Bike extends Vehicle { public Bike(String n) { super(n, VehicleType.BIKE); } }

// ── ParkingSpot ────────────────────────────────────────────────────────────
class ParkingSpot {
    private String  currentVehicle;
    private boolean isAvailable = true;

    public boolean isAvailable()        { return isAvailable;   }
    public String  getCurrentVehicle()  { return currentVehicle; }

    public void assignVehicle(String vehicleNumber) {
        this.currentVehicle = vehicleNumber;
        this.isAvailable    = false;
    }

    public void releaseVehicle() {
        this.currentVehicle = null;
        this.isAvailable    = true;
    }
}

// ── Level ──────────────────────────────────────────────────────────────────
class Level {
    private final int levelNumber;
    private final Map<Integer, ParkingSpot> spots = new HashMap<>();

    public Level(int levelNumber, int numberOfSpots) {
        this.levelNumber = levelNumber;
        for (int i = 1; i <= numberOfSpots; i++) {
            spots.put(i, new ParkingSpot());
        }
    }

    public int getLevelNumber() { return levelNumber; }

    /** Returns first available spot, or null if full. */
    public ParkingSpot getAvailableSpot() {
        return spots.values().stream()
                .filter(ParkingSpot::isAvailable)
                .findFirst()
                .orElse(null);
    }

    /** Returns the spot holding this vehicle, or null. */
    public ParkingSpot findSpotByVehicle(String vehicleNumber) {
        return spots.values().stream()
                .filter(s -> vehicleNumber.equals(s.getCurrentVehicle())) // NPE-safe
                .findFirst()
                .orElse(null);
    }

    public long availableCount() {
        return spots.values().stream().filter(ParkingSpot::isAvailable).count();
    }
}

// ── Fare strategy ──────────────────────────────────────────────────────────
interface FareCalculationStrategy {
    double calculateFare(LocalDateTime entryTime);
}

class BasicFareCalculationStrategy implements FareCalculationStrategy {
    private static final double RATE_PER_HOUR = 20.0;

    @Override
    public double calculateFare(LocalDateTime entryTime) {
        // FIX: use Duration so midnight crossings & minutes are handled correctly
        long minutes = Duration.between(entryTime, LocalDateTime.now()).toMinutes();
        double hours = Math.max(minutes / 60.0, 1.0); // minimum 1 hour charge
        return Math.ceil(hours) * RATE_PER_HOUR;
    }
}

// ── Ticket ─────────────────────────────────────────────────────────────────
record ParkingTicket(
        String ticketId,
        String vehicleNumber,
        VehicleType vehicleType,
        LocalDateTime entryTime) {}

// ── ParkingLotManager ──────────────────────────────────────────────────────
class ParkingLotManager {

    // FIX: CAR → level 2, BIKE → level 1  (same business rule, but now data-driven)
    private final Map<VehicleType, Integer> vehicleTypeLevelMap = Map.of(
            VehicleType.CAR,  2,
            VehicleType.BIKE, 1
    );

    private final Map<Integer, Level>         levelMap         = new HashMap<>(); // FIX: initialized
    private final Map<String, ParkingTicket>  parkingTicketMap = new HashMap<>(); // FIX: initialized
    private final FareCalculationStrategy     fareStrategy;

    public ParkingLotManager() {
        levelMap.put(1, new Level(1, 20)); // bikes
        levelMap.put(2, new Level(2, 30)); // cars
        fareStrategy = new BasicFareCalculationStrategy();
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = getAvailableSpot(vehicle.getVehicleType());
        if (spot == null) {
            throw new RuntimeException("No parking available for " + vehicle.getVehicleType());
        }

        spot.assignVehicle(vehicle.getVehicleNumber());

        String         ticketId = UUID.randomUUID().toString();
        ParkingTicket  ticket   = new ParkingTicket(
                ticketId, vehicle.getVehicleNumber(),
                vehicle.getVehicleType(), LocalDateTime.now());

        parkingTicketMap.put(vehicle.getVehicleNumber(), ticket);

        System.out.printf("[PARK]   %-10s | Level %d | Ticket: %s%n",
                vehicle.getVehicleNumber(),
                vehicleTypeLevelMap.get(vehicle.getVehicleType()),
                ticketId.substring(0, 8) + "...");

        printAvailability();
    }

    public synchronized void unParkVehicle(Vehicle vehicle) {
        int         levelNo = vehicleTypeLevelMap.get(vehicle.getVehicleType()); // FIX: use map
        Level       level   = levelMap.get(levelNo);
        ParkingSpot spot    = level.findSpotByVehicle(vehicle.getVehicleNumber());

        if (spot == null) {
            throw new RuntimeException("Vehicle not found: " + vehicle.getVehicleNumber());
        }

        ParkingTicket ticket = parkingTicketMap.get(vehicle.getVehicleNumber());
        double fare = fareStrategy.calculateFare(ticket.entryTime());

        spot.releaseVehicle();                              // FIX: was missing unassign
        parkingTicketMap.remove(vehicle.getVehicleNumber());

        System.out.printf("[UNPARK] %-10s | Fare: ₹%.2f%n",
                vehicle.getVehicleNumber(), fare);

        printAvailability();
    }

    // FIX: now correctly calls Level.getAvailableSpot() instead of casting Level → Map
    private ParkingSpot getAvailableSpot(VehicleType type) {
        int levelNo = vehicleTypeLevelMap.get(type);
        return levelMap.get(levelNo).getAvailableSpot();
    }

    private void printAvailability() {
        levelMap.forEach((num, level) ->
                System.out.printf("         Level %d → %d spots free%n",
                        num, level.availableCount()));
    }
}

// ── Main ───────────────────────────────────────────────────────────────────
public class ParkingLotEx1 {
    public static void main(String[] args) throws InterruptedException {
        ParkingLotManager manager = new ParkingLotManager();

        Vehicle car1  = new Car("CAR-001");
        Vehicle car2  = new Car("CAR-002");
        Vehicle bike1 = new Bike("BIKE-001");

        System.out.println("=== Parking vehicles ===");
        manager.parkVehicle(car1);
        manager.parkVehicle(car2);
        manager.parkVehicle(bike1);

        System.out.println("\n=== Simulating 2 seconds parked ===");
        Thread.sleep(2000);

        System.out.println("\n=== Unparking vehicles ===");
        manager.unParkVehicle(car1);
        manager.unParkVehicle(bike1);

        System.out.println("\n=== Trying to unpark already-gone vehicle ===");
        try {
            manager.unParkVehicle(car1);
        } catch (RuntimeException e) {
            System.out.println("[ERROR]  " + e.getMessage());
        }
    }
}


// 50min sol accepted for major product based

//package lld.practice;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.*;
//
///**
// * Parking Lot LLD (50 min interview version)
// *
// * Covers:
// * 1. Core entities
// * 2. Strategy pattern
// * 3. Park / Unpark flow
// * 4. Extensible design
// * 5. Clean code
// *
// * Intentionally skipped:
// * - DB
// * - Payment gateway integration
// * - Multiple gates
// * - Coupons
// * - Notifications
// */
//
//// =======================================================
//// ENUMS
//// =======================================================
//
//enum VehicleType {
//    CAR,
//    BIKE
//}
//
//enum SpotType {
//    CAR,
//    BIKE
//}
//
//// =======================================================
//// VEHICLE
//// =======================================================
//
//abstract class Vehicle {
//    private final String number;
//    private final VehicleType type;
//
//    public Vehicle(String number, VehicleType type) {
//        this.number = number;
//        this.type = type;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public VehicleType getType() {
//        return type;
//    }
//}
//
//class Car extends Vehicle {
//    public Car(String number) {
//        super(number, VehicleType.CAR);
//    }
//}
//
//class Bike extends Vehicle {
//    public Bike(String number) {
//        super(number, VehicleType.BIKE);
//    }
//}
//
//// =======================================================
//// PARKING SPOT
//// =======================================================
//
//class ParkingSpot {
//    private final int id;
//    private final SpotType type;
//    private Vehicle vehicle;
//
//    public ParkingSpot(int id, SpotType type) {
//        this.id = id;
//        this.type = type;
//    }
//
//    public boolean isFree() {
//        return vehicle == null;
//    }
//
//    public SpotType getType() {
//        return type;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void park(Vehicle vehicle) {
//        this.vehicle = vehicle;
//    }
//
//    public void removeVehicle() {
//        this.vehicle = null;
//    }
//
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//}
//
//// =======================================================
//// LEVEL
//// =======================================================
//
//class Level {
//    private final int floor;
//    private final List<ParkingSpot> spots = new ArrayList<>();
//
//    public Level(int floor) {
//        this.floor = floor;
//    }
//
//    public void addSpot(ParkingSpot spot) {
//        spots.add(spot);
//    }
//
//    public int getFloor() {
//        return floor;
//    }
//
//    public List<ParkingSpot> getSpots() {
//        return spots;
//    }
//}
//
//// =======================================================
//// STRATEGY: SPOT ALLOCATION
//// =======================================================
//
//interface SpotAllocationStrategy {
//    ParkingSpot findSpot(List<Level> levels, Vehicle vehicle);
//}
//
///**
// * Finds first matching free spot.
// */
//class NearestSpotStrategy implements SpotAllocationStrategy {
//
//    @Override
//    public ParkingSpot findSpot(List<Level> levels, Vehicle vehicle) {
//
//        SpotType needed =
//                vehicle.getType() == VehicleType.CAR
//                        ? SpotType.CAR
//                        : SpotType.BIKE;
//
//        for (Level level : levels) {
//            for (ParkingSpot spot : level.getSpots()) {
//                if (spot.isFree() && spot.getType() == needed) {
//                    return spot;
//                }
//            }
//        }
//
//        return null;
//    }
//}
//
//// =======================================================
//// STRATEGY: PRICING
//// =======================================================
//
//interface PricingStrategy {
//    double calculate(LocalDateTime entryTime);
//}
//
//class HourlyPricingStrategy implements PricingStrategy {
//
//    @Override
//    public double calculate(LocalDateTime entryTime) {
//
//        long minutes =
//                Duration.between(entryTime, LocalDateTime.now())
//                        .toMinutes();
//
//        double hours = Math.max(1, Math.ceil(minutes / 60.0));
//
//        return hours * 20;
//    }
//}
//
//// =======================================================
//// TICKET
//// =======================================================
//
//class Ticket {
//    private final String id;
//    private final Vehicle vehicle;
//    private final ParkingSpot spot;
//    private final LocalDateTime entryTime;
//
//    public Ticket(String id,
//                  Vehicle vehicle,
//                  ParkingSpot spot,
//                  LocalDateTime entryTime) {
//        this.id = id;
//        this.vehicle = vehicle;
//        this.spot = spot;
//        this.entryTime = entryTime;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//
//    public ParkingSpot getSpot() {
//        return spot;
//    }
//
//    public LocalDateTime getEntryTime() {
//        return entryTime;
//    }
//}
//
//// =======================================================
//// PARKING LOT MANAGER
//// =======================================================
//
//class ParkingLotManager {
//
//    private final List<Level> levels = new ArrayList<>();
//
//    private final Map<String, Ticket> activeTickets =
//            new HashMap<>();
//
//    private final SpotAllocationStrategy allocationStrategy;
//    private final PricingStrategy pricingStrategy;
//
//    public ParkingLotManager(
//            SpotAllocationStrategy allocationStrategy,
//            PricingStrategy pricingStrategy) {
//
//        this.allocationStrategy = allocationStrategy;
//        this.pricingStrategy = pricingStrategy;
//    }
//
//    public void addLevel(Level level) {
//        levels.add(level);
//    }
//
//    public synchronized Ticket parkVehicle(Vehicle vehicle) {
//
//        if (activeTickets.containsKey(vehicle.getNumber())) {
//            throw new RuntimeException("Vehicle already parked");
//        }
//
//        ParkingSpot spot =
//                allocationStrategy.findSpot(levels, vehicle);
//
//        if (spot == null) {
//            throw new RuntimeException("No spot available");
//        }
//
//        spot.park(vehicle);
//
//        Ticket ticket = new Ticket(
//                UUID.randomUUID().toString(),
//                vehicle,
//                spot,
//                LocalDateTime.now()
//        );
//
//        activeTickets.put(vehicle.getNumber(), ticket);
//
//        System.out.println(
//                "Parked " + vehicle.getNumber()
//                        + " at Spot " + spot.getId());
//
//        return ticket;
//    }
//
//    public synchronized void unparkVehicle(String vehicleNumber) {
//
//        Ticket ticket = activeTickets.get(vehicleNumber);
//
//        if (ticket == null) {
//            throw new RuntimeException("Vehicle not found");
//        }
//
//        double amount =
//                pricingStrategy.calculate(
//                        ticket.getEntryTime());
//
//        ticket.getSpot().removeVehicle();
//
//        activeTickets.remove(vehicleNumber);
//
//        System.out.println(
//                "Unparked " + vehicleNumber
//                        + " | Amount: ₹" + amount);
//    }
//}
//
//// =======================================================
//// MAIN
//// =======================================================
//
//public class ParkingLotEx1 {
//
//    public static void main(String[] args)
//            throws InterruptedException {
//
//        ParkingLotManager manager =
//                new ParkingLotManager(
//                        new NearestSpotStrategy(),
//                        new HourlyPricingStrategy()
//                );
//
//        // Floor 1
//        Level l1 = new Level(1);
//        l1.addSpot(new ParkingSpot(1, SpotType.BIKE));
//        l1.addSpot(new ParkingSpot(2, SpotType.BIKE));
//
//        // Floor 2
//        Level l2 = new Level(2);
//        l2.addSpot(new ParkingSpot(3, SpotType.CAR));
//        l2.addSpot(new ParkingSpot(4, SpotType.CAR));
//
//        manager.addLevel(l1);
//        manager.addLevel(l2);
//
//        Vehicle car = new Car("CAR-101");
//        Vehicle bike = new Bike("BIKE-11");
//
//        manager.parkVehicle(car);
//        manager.parkVehicle(bike);
//
//        Thread.sleep(2000);
//
//        manager.unparkVehicle("CAR-101");
//        manager.unparkVehicle("BIKE-11");
//    }
//}