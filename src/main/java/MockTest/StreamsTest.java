package MockTest;
import java.util.*;
import java.util.stream.Collectors;

class Employee{
    String name;
    String dept;
    int salary;

     public Employee(String name,String dept,int salary){
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }
    public int getSalary(){
         return salary;
    }
    public String getName(){
         return name;
    }
    public String getDept(){
         return dept;
    }
}
public class StreamsTest {

    public static boolean isEvenAndGreater(int n){
        return (n%2 ==0) && n>5;
    }
    public static void main(String[] args) {
        System.out.println("filter even and greater than 5 and sort them");
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10, 12);
        numbers.stream().filter(StreamsTest::isEvenAndGreater).map(x->x*x).sorted().toList().forEach(System.out::println);

        System.out.println("group by length and count");
        List<String> words = Arrays.asList("Java", "Python", "C", "Go", "Ruby");
        Map<Integer,Long> map = words.stream().collect(Collectors.groupingBy(String::length,Collectors.counting()));
        map.forEach((k,v)-> System.out.println(k+"="+v));

        List<Employee> employees= List.of(new Employee("himansh","1",200000),
                new Employee("priyahs","1",1200000),
                new Employee("rag","2",1000000),
                new Employee("ram","2",500000));

        Map<String, Optional<Employee>> map1 = employees.stream().collect(Collectors.groupingBy(Employee::getDept,Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println("Department by maxSalary");
        map1.forEach((k,v)-> System.out.println(k+"="+v));




    }
}
