package MockTest.singleton;

import org.designPattern.Singleton.SingletonDesign;

public class TestSingleton {

    public static void main(String[] args) {

        SingletonDesign s1 = SingletonDesign.getInstance();
        SingletonDesign s2 = SingletonDesign.getInstance();
        NormalClass n1 = new NormalClass();
        NormalClass n2 = new NormalClass();

        System.out.println(s1.hashCode() == s2.hashCode());
        // compare hashcode of normal class
        System.out.println(n1.hashCode() == n2.hashCode());
    }
}
