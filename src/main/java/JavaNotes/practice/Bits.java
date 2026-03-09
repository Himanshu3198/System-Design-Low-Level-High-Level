package JavaNotes.practice;

import java.util.ArrayList;
import java.util.List;

public class Bits {
    public static void main(String[] args) {
       List<Integer> list =  List.of(2,3,6);
       for(int bit=0;bit<32;bit++){
           List<Integer> filter = new ArrayList<>();
           for(int i=0;i<list.size();i++){
               int opr = (1<<bit)&list.get(i);
               if(opr>0){
                   filter.add(list.get(i));
               }
           }
           for(int f:filter){
               System.out.print(f+" ");
           }
           System.out.println();
       }
    }
}
