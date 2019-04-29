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
        System.out.print((char)27 + "[31m" + "> " + (char)27 + "[37m");
        Human h = new Human("Alice", 123);
        System.out.println(h.getOwner());
    }
}
