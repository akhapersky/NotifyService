import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
should be moved into the job (or into the cron)
 */
public class MainService {
    public static  Properties prop;
    public static String email;
    public static String pass;
    public static String addressList;

    public static void run() {
//        System.out.println("Running... " + new java.util.Date());
        try {
            Connection connection = SqlUtility.connect();
            Map<String, Date> map;
            map = SqlUtility.grubData(connection);
            String result = MapToMessage.converter(map);
            NotificationSender.sender(result, email, pass,addressList);
            System.out.println("Check your email!");
        }   catch (ClassNotFoundException e){}
            catch (SQLException ee){}
            catch (ParseException eee) {}

    }

    public static void main(String[] args) throws IOException {
        try (InputStream input = MainService.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            //load a properties file from class path
            prop.load(input);
            email = prop.getProperty("email.notification.sender");
            pass = prop.getProperty("pass.notification.sender");
            addressList = prop.getProperty("address.list");
        }
        //scheduler
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(MainService::run,0,24, TimeUnit.HOURS);
    }

}
