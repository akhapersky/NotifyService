import objects.Person;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListToMessage {

    public static String converter(List<Person> list){
        String result = "";
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            result = result + pair.getKey() + "; \n\n";
//            System.out.println(pair.getKey() + " = " + pair.getValue());
//            it.remove(); // avoids a ConcurrentModificationException
//        }
        for (Person p: list){
            result = result +
                    p.getEvent().toString() +
                    ":  " +
                    p.getFirstName() +
                    "  " +
                    p.getLastName() +
                    " <br>";
        }
        return result;
    }
}
