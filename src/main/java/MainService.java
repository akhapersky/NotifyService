//import com.sun.tools.jdeprscan.CSVParseException;
import database.SqlUtility;
import objects.Person;
import parser.CsvPersonParser;
import parser.CsvReсipientParser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
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
            //recreating tables added to update csv data without application restart
            new SqlUtility().setConnection().dropAllTables().createPersonTable().createRecipientTable();
            new CsvPersonParser().run();
            new CsvReсipientParser().run();

            List<Person> list;
            list = new SqlUtility().setConnection().grubData();
            String result = "";
            result = ListToMessage.converter(list);
            if(!"".equals(result)) {
                NotificationSender.sender(result, email, pass, addressList);
            }
        }   catch (ClassNotFoundException e){e.printStackTrace();}
            catch (SQLException ee){ee.printStackTrace();}
            catch (ParseException eee) {eee.printStackTrace();}

    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        if (args.length !=0) {
            switch (args[0]) {
                case "parse_persons":
                    new CsvPersonParser().run();
                    break;
                case "parse_recipient":
                    new CsvReсipientParser().run();
                    break;
                case "parse_all_data":
                    new CsvPersonParser().run();
                    new CsvReсipientParser().run();
                    break;
                case "recreate_db_and_parse":
                    new SqlUtility().setConnection().dropAllTables().createPersonTable().createRecipientTable();
                    new CsvPersonParser().run();
                    new CsvReсipientParser().run();
                    break;
                default:
                    break;
            }
        } else {

            try (InputStream input = MainService.class.getClassLoader().getResourceAsStream("config.properties")) {
                prop = new Properties();
                if (input == null) {
                    System.out.println("Unable to find config.properties");
                    return;
                }
                //load a properties file from class path
                prop.load(input);
                email = prop.getProperty("email.notification.sender");
                pass = prop.getProperty("pass.notification.sender");
                //send to recipients from the Recipients table
                try {
                    addressList = new SqlUtility().setConnection().grubEmails().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /* use it instead the previous grab to grab from properties file
                addressList = prop.getProperty("address.list");
                 */
                //MainService.run();

                //scheduler
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(MainService::run, 0, 1, TimeUnit.DAYS);
            }
        }



///////        executorService.scheduleAtFixedRate(MainService::run,0,10, TimeUnit.SECONDS);
    }
}

