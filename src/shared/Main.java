package shared;

import server.DataBaseConnection;

public class Main {

    public static void main(String[] args){

        System.out.println(DataBaseConnection.encryptString("admin"));
        System.out.println(DataBaseConnection.encryptString("aergaerg"));
    }
}
