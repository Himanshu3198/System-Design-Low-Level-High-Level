package JavaNotes.lambdaexpression;
import  java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamPractice06 {

    public static void main(String[] args) {

        List<String> fruits = List.of("Apple","Apple","Mango","Mango","Banana","Grapes");
        Set<String> st = new HashSet<>();

        Map<String,Long> groupByFreq = fruits.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        groupByFreq.forEach((k,v)-> System.out.println(k+","+v));

        System.out.println("Group by length");
        fruits.stream().collect(Collectors.groupingBy(String::length)).forEach((k,v)-> System.out.println(k+":"+v));
        System.out.println("Find duplicates");
        fruits.stream().filter(x->!st.add(x)).toList().forEach(System.out::println);

        List<Integer> nums = List.of(1,2,3,4,5,6,7);
        System.out.println("partition by ");
        Map<Boolean,List<Integer>> mp = nums.stream().collect(Collectors.partitioningBy(x->x%2==0));
        mp.forEach((k,v)-> System.out.println(k+":"+v));
        Map<Boolean,Long> mpC = nums.stream().collect(Collectors.partitioningBy(x->x%2==0,Collectors.counting()));
        System.out.println("count even and odd");
        mpC.forEach((k,v)-> System.out.println(k+":"+v));
        Map<Boolean,Long> mpS = nums.stream().collect(Collectors.partitioningBy(x->x%2==0,Collectors.summingLong(x->x)));
        System.out.println("sum even odd numbers");
        mpS.forEach((k,v)-> System.out.println(k+":"+v));

        // filter even numbers multply with 2 and then calculate averaga
        double avg = nums.stream().filter(x->x%2==0).map(x->x*2).mapToDouble(Integer::doubleValue).average().orElse(0.0);
        System.out.println("avg is "+avg);

        String str = "banana";
        System.out.println("Count freq of each character");
        Map<Character,Long> mpF = str.chars().mapToObj(x->(char)x).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        mpF.forEach((k,v)->System.out.println(k+":"+v));

        String str2 = "stress";
        System.out.println("Find first non-repeating character");

        Character fnr = str2.chars().mapToObj(x->(char)x).filter(x-> str2.indexOf(x) == str2.lastIndexOf(x)).findFirst().orElse('!');
        System.out.println("first non-repeating character "+fnr);

        System.out.println("find longest string using stream");
        List<String> words = List.of(
                "Java",
                "SpringBoot",
                "Microservices"
        );

        String longestString = words.stream().max(Comparator.comparing(String::length)).orElse("lol");
        System.out.println(longestString);

        System.out.println("join string using delimeter");
        String joinedString = fruits.stream().collect(Collectors.joining(","));
        System.out.println(joinedString);

        System.out.println("Reverse word in string using stream");
        String hello = "Hello World";
        String revH = Arrays.stream(str.split(" ")).map(x->new StringBuilder(x).reverse().toString()).collect(Collectors.joining(" "));
        System.out.println(revH);

        System.out.println("Find palindrome string using stream");
        List<String> words2 = List.of(
                "madam","java","level","spring"
        );
        words2.stream().filter(x->x.contentEquals(new StringBuilder(x).reverse())).toList().forEach(System.out::println);
    }
}
