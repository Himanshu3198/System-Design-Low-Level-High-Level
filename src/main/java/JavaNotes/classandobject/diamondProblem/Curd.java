package JavaNotes.classandobject.diamondProblem;

public interface Curd {

     default void print(){
         System.out.println("I am milk");
     }
}
