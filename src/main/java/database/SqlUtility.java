package database;

import builders.PersonBuilder;
import objects.Event;
import objects.Person;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SqlUtility {
    public static Statement statement;
    public static ResultSet resultSet;
    public static Connection connection;

    public SqlUtility setConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:db/sqlite.s3db");
        return this;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException{
        Connection connection = SqlUtility.connect();
//        SqlUtility.createPersonTable(connection);
//        SqlUtility.createRecipientTable(connection);
//        SqlUtility.writePerson(connection);
//        database.SqlUtility.readDB(connection);
//        database.SqlUtility.close(connection);
//        grubData(connection);

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

    public SqlUtility createPersonTable() throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        //statement.execute("DROP TABLE 'Person'; ");
        statement.execute(
                "CREATE TABLE IF NOT EXISTS 'Person' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "'event' VARCHAR2(64)," +
                " 'birthday_date' DATE DEFAULT CURRENT_DATE," +
                " 'last_congrat' INTEGER," +
                "'firstName' VARCHAR2(64)," +
                "'secondName' VARCHAR2(64)," +
                "'lastName'VARCHAR2(64));"
        );
        return this;
    }

    // --------DROP TABLES--------
    public SqlUtility dropAllTables() throws SQLException
    {
        statement = connection.createStatement();
        statement.execute("DROP TABLE 'Person'; ");
        statement.execute("DROP TABLE 'Recipient';");
        return this;
    }

    public SqlUtility createRecipientTable() throws ClassNotFoundException, SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS 'Recipient' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'email' VARCHAR2(64)," +
                " 'name' VARCHAR2(64));"
        );
        return this;
    }

    // --------INSERT PERSON--------
//    public static void writePerson(Connection connection) throws SQLException
//    {
//        statement = connection.createStatement();
//        statement.execute("INSERT INTO 'Person' ('person', 'birthday_date','last_congrat') " +
//                "VALUES ('Test', '1993-1-14',2018); ");
//    }

    // -------- Show table--------
//    public static void readDB(Connection connection) throws ClassNotFoundException, SQLException
//    {
//        statement = connection.createStatement();
//        resultSet = statement.executeQuery("SELECT * FROM Person");
//
//        while(resultSet.next())
//        {
//            int id = resultSet.getInt("id");
//            String  person = resultSet.getString("person");
//            String  birthday_date = resultSet.getString("birthday_date");
//            System.out.println( "ID = " + id );
//            System.out.println( "person = " + person );
//            System.out.println( "birthday_date = " + birthday_date );
//            System.out.println();
//        }
//
//    }

    //--------Grub all recipients emails in string--------
    public StringBuilder grubEmails() throws ClassNotFoundException, SQLException
    {
        StringBuilder addressList = new StringBuilder("");
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Recipient");
        while(resultSet.next())
        {
            addressList.append(resultSet.getString("email")).append(", ");
        }
        return addressList;
    }

    public List<Person> grubData() throws ClassNotFoundException, SQLException,
            ParseException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate= calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //Map<String,java.util.Date> map = new HashMap<String,java.util.Date>();
        List<Person> list = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Person");
        while(resultSet.next())
        {
            String  person = resultSet.getString("firstName");
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

                Person p = new PersonBuilder()
                        .setBirthDay(date)
                        .setFirstName(resultSet.getString("firstName"))
                        .setSecondName(resultSet.getString("secondName"))
                        .setLastName(resultSet.getString("lastName"))
                        .setEvent(Event.valueOf(resultSet.getString("event")))
                        .build();
                list.add(p);
            }
        }
        return list;

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
