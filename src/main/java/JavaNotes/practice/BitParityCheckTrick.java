package JavaNotes.practice;

import java.util.Arrays;

public class BitParityCheckTrick {
    public static void main(String[] args) {

        /*
          the idea is to check parity of sum of number efficiently with xor
         */

        int[] nums = {1,2,3,4,4,7,8,9};

        int sum = Arrays.stream(nums).sum();
        System.out.println("sum is"+sum);
        System.out.println("parity with sum "+sum%2);

        int xo = 0;
        for(int x:nums){
            xo = xo^x;
        }
        System.out.println("parity with xo "+xo%2);
        boolean ops = xo%2 == sum%2;
        System.out.println("parity will be same for xor of an array and sum of an array "+ops);
    }

}
