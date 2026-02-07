package JavaNotes.classandobject.diamondProblem;

public interface Paneer {

    default void print(){
        System.out.println("I am milk");
    }
}
