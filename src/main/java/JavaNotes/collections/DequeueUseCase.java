package JavaNotes.collections;

import java.util.ArrayDeque;
import java.util.Deque;


public class DequeueUseCase {


    public static void main(String[] args) {
        Deque<Integer> dq= new ArrayDeque<>();
        dq.addFirst(12);
        dq.addFirst(50);
        dq.addFirst(92);
        dq.addLast(1000);
        dq.addLast(200);
        System.out.println("size="+dq.size());
        System.out.println("first="+dq.peekFirst());
        System.out.println("last="+dq.peekLast());
        System.out.println("popLast="+dq.pollLast());
        System.out.println("popFirst="+dq.pollFirst());
    }




}
