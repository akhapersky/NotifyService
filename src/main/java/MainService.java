import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
should be moved into the job (or into the cron)
 */
public class MainService {

//    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
//        Connection connection = SqlUtility.connect();
//        Map<String, Date> map;
//        map = SqlUtility.grubData(connection);
//        String result = MapToMessage.converter(map);
//        NotificationSender.sender(result);
//        System.out.println("Check your email!");
//    }

    public static void run(){//throws ClassNotFoundException, SQLException, ParseException {
//        System.out.println("Running... " + new java.util.Date());
        try {
            Connection connection = SqlUtility.connect();
            Map<String, Date> map;
            map = SqlUtility.grubData(connection);
            String result = MapToMessage.converter(map);
            NotificationSender.sender(result);
            System.out.println("Check your email!");
        }   catch (ClassNotFoundException e){}
            catch (SQLException ee){}
            catch (ParseException eee) {}

    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(MainService::run,0,600, TimeUnit.SECONDS);
    }

}
