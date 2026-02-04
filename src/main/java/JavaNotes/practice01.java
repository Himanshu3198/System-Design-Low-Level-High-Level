package JavaNotes;

import java.util.*;
import java.util.stream.Collectors;

class Transaction {
    String userId;
    String stock;
    int quantity;

    public Transaction(String id,String stock,int q){
        this.userId = id;
        this.stock = stock;
        this.quantity = q;
    }
}

public class practice01 {

    public static void main(String[] args) {

        Integer [] arr = {1,2,3,4,6,7};

        Arrays.sort(arr,Collections.reverseOrder());
        for(int i=0;i<arr.length;i++){
//            System.out.println(arr[i]);
        }

        List<Integer> list = new ArrayList<>();
        for(int i=1;i<=5;i++){
            list.add(i);
        }
//        System.out.println("List demo");
        list.sort((a, b) -> Integer.compare(b, a));
//        list.forEach(System.out::println);
        list.reversed();
//        list.forEach(System.out::println);


        String t = " I,am,doing,my,best,to,fix,it";
        t=t.trim();
        String[] tArr = t.split(",");

        for(int i=0;i< tArr.length;i++){
//            System.out.println(tArr[i]);
        }

        List<Transaction>transactions = new ArrayList<>();
        transactions.add(new Transaction("1","hs",50));
        transactions.add(new Transaction("2","rs",100));
        transactions.add(new Transaction("3","js",150));

        Map<String,Integer> map = transactions.stream().collect(Collectors.toMap(x->x.userId,y->y.quantity));
        map.forEach((String k,Integer v )-> System.out.println(k+","+v));


    }
}
