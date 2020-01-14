import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SqlUtility {
    public static Statement statement;
    public static ResultSet resultSet;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException{
        Connection connection = SqlUtility.connect();
        SqlUtility.createPersonTable(connection);
        SqlUtility.createRecipientTable(connection);
        SqlUtility.writePerson(connection);
//        SqlUtility.readDB(connection);
//        SqlUtility.close(connection);
        grubData(connection);

    }
    //connect to DB (return connection)
    //DONE
    public static Connection connect() throws ClassNotFoundException, SQLException
    {
        Connection connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:db/sqlite.s3db");
        return connection;
    }

    public static void createPersonTable(Connection connection) throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS 'Person' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'person' VARCHAR2(64), 'birthday_date' DATE" +
                "  DEFAULT CURRENT_DATE, 'last_congrat' INTEGER);");
    }

    public static void createRecipientTable(Connection connection) throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS 'Recipient' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'email' VARCHAR2(64), 'name' VARCHAR2(64));");
    }

    // --------INSERT PERSON--------
    public static void writePerson(Connection connection) throws SQLException
    {
        statement = connection.createStatement();
        statement.execute("INSERT INTO 'Person' ('person', 'birthday_date','last_congrat') " +
                "VALUES ('Test', '1993-1-14',2018); ");
    }

    // -------- Show table--------
    public static void readDB(Connection connection) throws ClassNotFoundException, SQLException
    {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Person");

        while(resultSet.next())
        {
            int id = resultSet.getInt("id");
            String  person = resultSet.getString("person");
            String  birthday_date = resultSet.getString("birthday_date");
            System.out.println( "ID = " + id );
            System.out.println( "person = " + person );
            System.out.println( "birthday_date = " + birthday_date );
            System.out.println();
        }

    }

    public static Map<String,java.util.Date> grubData(Connection connection) throws ClassNotFoundException, SQLException,
            ParseException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate= calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,java.util.Date> map = new HashMap<String,java.util.Date>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Person");
        while(resultSet.next())
        {
            String  person = resultSet.getString("person");
            String  birthday_date = resultSet.getString("birthday_date");
            System.out.println("birthday_date FROM SQL: "+birthday_date);
            java.util.Date date = df.parse(birthday_date);
            System.out.println("CHECK DATA TRANSFORMED TO java.util.date ===> " + date.toString());
            System.out.println("CHECK current date ===> " + currentDate.toString());

            System.out.println("currentDate.getMonth() "+currentDate.getMonth());
            System.out.println("currentDate.getDate() "+ currentDate.getDate());
            System.out.println("Date.getMonth() "+date.getMonth());
            System.out.println("Date.getDate() "+ date.getDate());

            if ((currentDate.getMonth()==date.getMonth()) && currentDate.getDate()==date.getDate()){
                System.out.println("There is the birthday found for "+person+"!!!");
                map.put(person,date);
            }
        }
        return map;

    }

    // --------Closer--------
//    public static void close(Connection connection) throws ClassNotFoundException, SQLException
//    {
//        connection.close();
//        statement.close();
//        resultSet.close();
//
//        System.out.println("Connections closed");
//    }

}
