package JavaNotes.practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPractice04 {

    public static void main(String[] args) {

        String s = "SWISS";
        System.out.println("first non repeating character");
        Optional<Character> nonRepeating =  s.chars().mapToObj(c->(char)c).filter(c->s.indexOf(c) == s.lastIndexOf(c)).findFirst();
        System.out.println(nonRepeating);

        List<String> names = Arrays.asList("java", "stream", "api");
        names.stream().map(String::toUpperCase).toList().forEach(System.out::println);

        System.out.println("Sum the number");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> sum = numbers.stream().reduce((a, b)->a+b);
        System.out.println(sum.get());

        System.out.println("Check if any string in a list contains “API”");
        List<String> strings = Arrays.asList("Java", "Stream API", "Lambda");
        Optional<String> matchAny  = strings.stream().filter(s1->s1.contains("API")).findAny();
        System.out.println(matchAny.get());

        System.out.println("Find duplicate element in list");
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 2, 5, 1);
        Set<Integer> unique = new HashSet<>();
        numbers1.stream().filter(n->!unique.add(n)).collect(Collectors.toSet()).forEach(System.out::println);

        System.out.println("Group a list of strings based on their length.");
        List<String> words = Arrays.asList("Java", "Stream", "API", "Code", "Fun");
        words.stream().collect(Collectors.groupingBy(String::length)).forEach((k,v)-> System.out.println(k+","+v));

        System.out.println("Given a list of lists, flatten it into a single list.");
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );
        List<Integer> flattenList = listOfLists.stream().flatMap(List::stream).toList();
        flattenList.forEach(System.out::println);

        System.out.println("Concatenate all strings in a list into a single string");
        List<String> words1= Arrays.asList("Stream", "API", "is", "powerful");
        String concatWord = words1.stream().reduce("",(s1,s2)->s1+" "+s2);
        System.out.println(concatWord);

        System.out.println("Find the longest string in a list.");
        List<String> words3 = Arrays.asList("Java", "Stream", "API", "Development");
        String longestWord = words3.stream().reduce((s1,s2)->s1.length()>s2.length()?s1:s2).orElse(null);
        System.out.println(longestWord);

        System.out.println("Count the frequency of each character in a string.");
        String input = "Success";
        input.chars().
                mapToObj(c->(char)c).
                collect(Collectors.groupingBy(c1->c1+"$",Collectors.counting())).
                forEach((k,v)-> System.out.println(k+","+v));

        System.out.println("Remove null values from a list using filter.");
        List<String> words4 = Arrays.asList("Java", null, "Stream", null, "API");
        words4.stream().filter(Objects::nonNull).toList().forEach(System.out::println);

        System.out.println("Calculate the average of a list of integers.");
        List<Integer> numbers4 = Arrays.asList(10, 20, 30, 40, 50);
        double avgNumber = numbers4.stream().mapToDouble(i->(int)i).average().orElse(0.0);
        System.out.println("avgNumber is "+avgNumber);

        System.out.println("Partition a list of numbers into even and odd");
        List<Integer> numbers5 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers5.stream().collect(Collectors.groupingBy(n->n%2==0?"even":"odd")).forEach((k,v)-> System.out.println(k+":"+v));

        System.out.println("Find the 3rd largest element in a list");
        List<Integer> numbers6 = Arrays.asList(10, 20, 50, 40, 30);
        int thirdLargest= numbers6.stream().sorted(Collections.reverseOrder()).skip(2).findFirst().orElse(-1);
        System.out.println(thirdLargest);










    }
}
