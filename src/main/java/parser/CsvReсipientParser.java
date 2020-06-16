package parser;

import database.SqlUtility;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvReсipientParser {
    private static String sql = " INSERT INTO Recipient(email, name) VALUES(?,?) ";
    private static PreparedStatement ps = null;

    //public static void main(String[] args) {
        public void run() {
        try {
            Connection connection = SqlUtility.connect();
            new CsvReсipientParser().start(connection);
        } catch(SQLException | ClassNotFoundException sqle) { sqle.printStackTrace(); }
    }

    public void start(Connection connection) {
        try {
//            BufferedReader bReader = new BufferedReader(
//                    new FileReader(getClass().getClassLoader().getResource("recipients.csv").getFile())
//            );
//            InputStream input = CsvReсipientParser.class.getClassLoader().getResourceAsStream("recipients.csv");
//            InputStreamReader inputStreamReader = new InputStreamReader(input);
//            BufferedReader bReader = new BufferedReader(inputStreamReader);
            String path = "./recipients.csv";
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
