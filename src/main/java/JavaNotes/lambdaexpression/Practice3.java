package JavaNotes.lambdaexpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class User{
    private final Integer id;
    private final String name;
    private final String city;

    public User(Integer id,String name,String city){
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getCity(){
        return city;
    }
}
public class Practice3 {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User(1,"himanshu","delhi"));
        users.add(new User(2,"priyanshu","mp"));
        users.add(new User(3,"divyanshu","bihar"));

        Map<Integer,String> map = users.stream().collect(Collectors.toMap(u->u.getId(),u->u.getName()+";"+u.getCity()));
        map.forEach((k,v)-> System.out.println(k+":"+v));
    }
}
