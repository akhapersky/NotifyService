package parser;

import com.opencsv.CSVReader;
import database.SqlUtility;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvPersonParser {
    private static String sql = " INSERT INTO Person(firstName, secondName, lastName, birthday_date, event) VALUES(?,?,?,?,?) ";
    private static PreparedStatement ps = null;

    //public static void main(String[] args) {
        public void run() {
        try {
            Connection connection = SqlUtility.connect();
            new CsvPersonParser().start(connection);
        } catch(SQLException | ClassNotFoundException sqle) { sqle.printStackTrace(); }
    }

    public void start(Connection connection) {
        try {
//            BufferedReader bReader = new BufferedReader(
//                    new FileReader(getClass().getClassLoader().getResource("persons.csv").getFile())
//            );

            //CSVReader reader = new CSVReader(new FileReader("persons.csv"));

//            InputStream input = CsvPersonParser.class.getClassLoader().getResourceAsStream("persons.csv");
//            InputStreamReader inputStreamReader = new InputStreamReader(input);
//            BufferedReader bReader = new BufferedReader(inputStreamReader);
            String path = "./persons.csv";
            FileInputStream fis = new FileInputStream(path);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while ((line = bReader.readLine()) != null) {
                try {

                    if (line != null) {
                        String[] array = line.split(",+");
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, array[0]);
                            ps.setString(2, array[1]);
                            ps.setString(3, array[2]);
                            ps.setString(4, array[3]);
                            ps.setString(5, array[4]);
                            ps.execute();
                            ps.close();
                    }
                } finally {
                    if (bReader == null) {
                        bReader.close();
                    }
                }
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
