package JavaNotes.lambdaexpression;

import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String name;
    String department;

    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}
public class StreamPractice02 {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "HR"),
                new Employee("Bob", "IT"),
                new Employee("Charlie", "IT"),
                new Employee("David", "HR"),
                new Employee("Eve", "Finance")
        );

        System.out.println("Group by dep and count emp");
        Map<String,Long> deptCount = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting()));
        deptCount.forEach((k,v)-> System.out.println(k+","+v));

        System.out.println("print department with max employee");
        Optional<Map.Entry<String, Long>> maxDept = deptCount.entrySet().stream().max(Map.Entry.comparingByValue());
        maxDept.ifPresent(entry-> System.out.println(entry.getKey()+","+entry.getValue()));

        List<Integer> nums = List.of(1,2,3,4,5,6,7,8,9,10);
        System.out.println("calcuate sum of even and odd");
        Map<Boolean, Integer> sumMap= nums.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0,            // predicate: true → even
                        Collectors.summingInt(Integer::intValue) // downstream collector
                ));
        System.out.println("odd"+sumMap.get(false));
        System.out.println("even"+sumMap.get(true));

        System.out.println("split even odd");
        Map<String,List<Integer>> splitEven  = nums.stream().collect(Collectors.groupingBy(n->n%2==0?"even":"odd"));
        splitEven.forEach((k,v)-> System.out.println(k+","+v));
    }
}
