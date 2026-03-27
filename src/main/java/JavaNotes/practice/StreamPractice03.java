package JavaNotes.practice;

import java.util.*;
import java.util.stream.Collectors;

class Emp3{
    String name;
    double salary;

    public Emp3(String name,double salary){
        this.name = name;
        this.salary = salary;
    }
}

public class StreamPractice03 {

    public static boolean isPrime(int n){

        if(n<=1) return false;
        for(int i=2;i*i<=n;i++){
            if(n%i == 0) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println("find first duplicate in String");
        String s = "United States America";
        Optional<Character> duplicate = s.chars().mapToObj(c->(char) c).filter(i->s.indexOf(i) != s.lastIndexOf(i)).findFirst();
        System.out.println(duplicate);

        System.out.println("Find Employee with salary greater than 10000");
        List<Emp3> empList = List.of(new Emp3("Himanshu", 70000),new Emp3("Rahul", 1650),new Emp3("Amit", 8000),new Emp3("Neha", 190000));
        empList.stream().filter(x->x.salary>10000).toList().forEach(x-> System.out.println(x.name+","+x.salary));
        System.out.println("find max salary ");
        Emp3 maxSalEmp = empList.stream().max(Comparator.comparingDouble(e->e.salary)).orElse(null);
        System.out.println(maxSalEmp.name+","+maxSalEmp.salary);

        /* you are given a list of integer you need to filter prime number after that make square of it and then calculate average*/

        List<Integer> nums = List.of(1,5,3,10,8,11,16,7,13,17);
       OptionalDouble avg = nums.stream().filter(StreamPractice03::isPrime).map(x->x*x).mapToDouble(x->(double)x).average();
        System.out.println("avg is "+avg.orElse(0.0));

        System.out.println("Second highest salary");
        Double secondHighest = empList.stream().map(e->e.salary).distinct().sorted(Collections.reverseOrder()).skip(1).findFirst().orElse(null);
        assert secondHighest != null;
        System.out.println(secondHighest);
    }
}
