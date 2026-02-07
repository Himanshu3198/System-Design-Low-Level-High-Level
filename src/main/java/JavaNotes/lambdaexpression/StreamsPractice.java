package JavaNotes.lambdaexpression;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamsPractice {

    private static boolean isPrime(int n){

        if( n<=1) return true;
        for(int i=2;i*i<=n;i++){
            if(n%i == 0) return false;
        }
        return true;
    }


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


        // filter out prime number - change it to double->then calculate

        double avg = numbers3.stream().filter(StreamsPractice::isPrime).mapToDouble(Integer::doubleValue).average().orElse(0.0);
        System.out.println("Avg is "+avg);

        // sum of string length

        int sumOfStringLen = words.stream().mapToInt(String::length).sum();
        System.out.println("sumOfStringLen"+sumOfStringLen);
        words.stream().mapToInt(String::length).forEach(System.out::println);

        List<String> numsOfString = List.of("10","50","12","18");
        int sumOfStringNums = numsOfString.stream().mapToInt(Integer::parseInt).sum();
        System.out.println("sumOfStringNums "+sumOfStringNums);

        List<Integer>  numsRoots = List.of(1, 4, 9, 16);
        numsRoots.stream().mapToDouble(Math::sqrt).forEach(System.out::println);

        // calculate average price
        List<Double> prices = List.of(99.5, 149.9, 200.0);

        double avgPrice = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        System.out.println("Avg selling price is "+avgPrice);

        // convert C to F
        List<Double> celsius = List.of(0.0, 25.0, 100.0);
        System.out.println("Convert temperature C-F");
        celsius.stream().mapToDouble(c->(c*9/5)+32).forEach(System.out::println);



        // convert int to string mapToObj
//        Square numbers as strings
        List<Integer> n1 = List.of(1, 2, 3, 4);
        System.out.println("square number as string");
        List<String> n1String = n1.stream().mapToInt(n->n*n).mapToObj(String::valueOf).toList();
        n1String.forEach(System.out::println);

        List<String> s1Num = IntStream.range(1,5).mapToObj(x->"Num->"+x).toList();
        System.out.println("s1Num is==");
        s1Num.stream().forEach(System.out::println);

        boolean isEven = n1.stream().anyMatch(n->n%2== 0);
        System.out.println("check isEven for n1"+isEven);

        boolean isLenThree = words.stream().allMatch(n->n.length()>3);
        System.out.println("Check len of words greater than 3"+isLenThree);

        // group and sum values
        System.out.println("group and sum");
        n1.stream().collect(Collectors.groupingBy(n->n%2==0,Collectors.summarizingInt(Integer::intValue))).forEach((k,v)-> System.out.println(k+"="+v));


    ///  skip and limit elements
        System.out.println("Skip the first two element");
        n1.stream().skip(2).forEach(System.out::println);

        System.out.println("Limit 2 element");
        n1.stream().limit(3).toList().forEach(System.out::println);

        // top 2 largest numbers
        System.out.println("Print top 2 largest number");
        n1.stream().sorted(Comparator.reverseOrder()).limit(2).forEach(System.out::println);

    }
}
