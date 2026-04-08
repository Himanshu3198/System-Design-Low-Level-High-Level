package JavaNotes.lambdaexpression;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPractice05 {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("himanshu", "java", "stream");
        System.out.println("convert to uppercase");
        names.stream().map(String::toUpperCase).toList().forEach(System.out::println);

        String str = "aabbcdde";
        System.out.println("first non repeating character");
        Optional<Character> fnc = str.chars().mapToObj(c->(char)c).filter(idx->str.indexOf(idx) != str.lastIndexOf(idx)).findFirst();
        System.out.println(fnc.get());

        List<Integer> nums = Arrays.asList(10, 20, 5, 30, 25);
        System.out.println("Find second highest salary");

        Integer secondHighest = nums.stream().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(null);
        System.out.println(secondHighest.intValue());

        List<List<Integer>> list = Arrays.asList(
                Arrays.asList(1,2),
                Arrays.asList(3,4)
        );
        System.out.println("Flatten list");
        list.stream().flatMap(List::stream).toList().forEach(System.out::println);

        System.out.println("Partition even odd list");
        Map<Boolean,List<Integer>> partitionMap = nums.stream().collect(Collectors.partitioningBy(n->n%2==0));
        System.out.println(partitionMap.get(true));
        System.out.println(partitionMap.get(false));

        Map<String,List<Integer>> evenOdd = nums.stream().collect(Collectors.groupingBy(n->n%2==0?"even":"odd"));
        System.out.println("even"+evenOdd.get("even"));
        System.out.println("odd"+evenOdd.get("odd"));

        System.out.println("count even and odd numbers");
        Map<String,Long> countMap = nums.stream().collect(Collectors.groupingBy(n->n%2==0?"even":"odd",Collectors.counting()));
        System.out.println("even"+countMap.get("even"));
        System.out.println("odd"+countMap.get("odd"));

        System.out.println("sum even and odd numbers");
        Map<String,Integer> sumMap = nums.stream().collect(Collectors.groupingBy(n->n%2==0?"even":"odd",Collectors.summingInt(n->n)));
        System.out.println("even"+sumMap.get("even"));
        System.out.println("odd"+sumMap.get("odd"));
    }
}
