package shared;

import server.CommandHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String name = "lab";
    private static String pass = "lab1234";

    public static void main(String[] args){
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Installed Driver");
            connection = DriverManager.getConnection(url, name, pass);
            System.out.println("The Connection is successfully established\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't connect to the database");
        }

    }
}
