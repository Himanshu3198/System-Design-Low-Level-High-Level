package JavaNotes.lambdaexpression;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsPractice {

    public static void main(String[] args) {

        String s = "swiss";
//        Optional<Character> nonRepeat = s.chars().mapToObj(c->(char)c).filter(c->s.indexOf(c) == s.lastIndexOf(c)).findFirst();
        OptionalInt nonRepeat = s.chars().filter(c->s.indexOf(c) == s.lastIndexOf(c)).findFirst();
        System.out.println("nonRepeating Character is"+nonRepeat);

        // convert list of string to uppercase
        List<String> lowerList = List.of("APPLE","BANANA","KAHANA");
        lowerList.stream().map(String::toLowerCase).toList().forEach(System.out::println);

        // sum of all element in list

        List<Integer> numbers = List.of(1,2,3,4,5);
        int num = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum "+num);

//        Check if any string in a list contains “API”.
        List<String> strings = Arrays.asList("Java", "Stream API", "Lambda");
        boolean isContainsAPI = strings.stream().anyMatch(s1->s1.contains("API"));
        System.out.println("isContainAPI->"+isContainsAPI);

        // find duplicates in list
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 2, 5, 1);
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = numbers1.stream().filter(n->!seen.add(n)).collect(Collectors.toSet());
        System.out.println("Duplicates are");
        duplicates.forEach(System.out::println);


        // Question: Group a list of strings based on their length.

        List<String> words = Arrays.asList("Java", "Stream", "API", "Code", "Fun");
        Map<Integer,List<String>> groupByLength = words.stream().collect(Collectors.groupingBy(String::length));
        System.out.println("print groupByLength map");
        groupByLength.forEach((k,v)->System.out.println(k+"="+v));

        // Group by  freq of even odd number

        List<Integer> numbers3 = List.of(1,3,2,4,6,7,8,9,10);
        Map<String,Long> evenOddCount = numbers3.stream().collect(Collectors.groupingBy(n->n%2==0?"Even":"Odd",Collectors.counting()));
        System.out.println("Even odd freq");
        evenOddCount.forEach((k,v)-> System.out.println(k+"="+v));

        // Question: Given a list of lists, flatten it into a single list.
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );
        List<Integer> flattenedList = listOfLists.stream().flatMap(List::stream).toList();
        System.out.println("Flattened list");
        System.out.println(flattenedList);


    }
}
