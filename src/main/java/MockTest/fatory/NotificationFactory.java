package MockTest.fatory;

import java.util.HashMap;
import java.util.Map;

public class NotificationFactory {

       private Map<String,Notification> registry ;
       public NotificationFactory(){
           this.registry = new HashMap<>();
           registry.put("SMS",new SmsNotification());
           registry.put("Email",new EmailNotification());
       }

       public  Notification getChannel(String channel){
           if(!registry.containsKey(channel)){
               throw new RuntimeException("Not supported channel found");
           }
           return registry.get(channel);
       }



}
