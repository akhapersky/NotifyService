import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class MapToMessage {

    public static String converter(Map<String, Date> map){
        String result = "";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result = result + pair.getKey() + "; \n\n";
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return result;
    }
}
