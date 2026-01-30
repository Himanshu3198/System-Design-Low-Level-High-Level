package JavaNotes.lambdaexpression;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Practice04 {

    public static void main(String[] args) {

        List<Integer> list = List.of(5,20,30,66,41,23,75,79,78,80);
        List<Integer> even = list.stream().filter(x->x%2==0).collect(Collectors.toList());
        even.forEach(System.out::println);
        list.stream().filter(x->x%2==0 && x>50).collect(Collectors.toList()).forEach(x-> System.out.println(x));

        int sum = list.stream().reduce(0,(a,b)->a+b);
        int square = list.stream().reduce(0,(a,b)->a+b*b);
//        int sum2 = list.stream().reduce(0,Integer::sum);
//        System.out.println("sum "+sum+","+square);

        // Q. calculate even number square sum
        int  squareSumEven = list.stream().filter(x->x%2==0).mapToInt(x->x*x).reduce(0,(a,b)->a+b);
        System.out.println(squareSumEven);

        //Q count the frequency of a word
        List<String> fruits = List.of("apple", "banana", "apple", "orange", "banana");
        Map<String,Long> freq = fruits.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println("Freq of word");
        freq.forEach((k,v)-> System.out.println(k+","+v));

    }
}
