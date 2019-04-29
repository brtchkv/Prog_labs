package server;

import shared.*;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DataBaseConnection {
    private String url = "jdbc:postgresql://localhost:5432/studs";
    private String name = "lab";
    private String pass = "lab1234";
    private Connection connection = null;
    private CommandHandler command = null;

    {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Installed Driver");
            connection = DriverManager.getConnection(url, name, pass);
            System.out.println("The Connection is successfully established\n");
            command = new CommandHandler();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't connect to the database");
        }
    }

    public int loadPersons(Vector<Human> storage) {
        try {
            int i = 0;
            OffsetDateTime time = OffsetDateTime.now();
           // Statement statement = connection.createStatement();
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM \"persons\";");
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String name = result.getString("name");
                int age = result.getInt("age");
                String skills = result.getString("skill");
                String date = result.getString("creation_date");
                if (date != null) {
                    time = OffsetDateTime.parse(result.getString("creation_date").replace(" ", "T"));
                }
                Human h = new Human(name, age);
                h.setDateTime(time);
                h.setOwner(username);
                if (skills != null && !skills.equals("[]") && !skills.toLowerCase().trim().equals("null")) {
                    h.addSkill(new Skill(skills.split(" ")[0], skills.split(" ")[1]));
                }
                storage.add(h);

                i++;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error whilst adding Humans");
            return -1;
        }
    }

    public void addToDB(Human human) {
        try {
            PreparedStatement preStatement = connection.prepareStatement("INSERT INTO persons VALUES ('" + human.getName() + "', '" +
                    human.getClass().toString().replace("class Entities.", "") + "', '"
                    +human.getAge() + "', '"+ human.getSkills() + "', '" + human.getDisabilities() + "', '"
                    + human.getOwner() + "', '" + human.getDateTime().toString().replace("T", " ") +"');");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении в БД персонажа");
        }
    }

    public void savePersons(Vector<Human> humans) {
        try {
            if (humans != null) {

                Iterator<Human> iterator = humans.iterator();
                if (iterator.hasNext()) {
                    PreparedStatement preStatement = connection.prepareStatement("TRUNCATE persons;");
                    preStatement.execute();
                }

                while (iterator.hasNext()) {
                    Human h = iterator.next();
                    String skills = h.getSkills().toString() != "[]" && h.getSkills().toString() != null ? h.getSkills().toString() : "NULL";
                    PreparedStatement preStatement = connection.prepareStatement("INSERT INTO persons VALUES ('" + h.getName() + "', "
                            + "'" + h.getAge() + "', '" + skills + "', '" + h.getOwner() +
                            "', '" + h.getDateTime().toString() + "');");
                    preStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst saving Humans to the DataBase");
        }
    }

    public int executeLogin(String login, String hash) {
        try {
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM users WHERE username='" + login + "' and "+ "hash='" + hash + "';");
            ResultSet result = preStatement.executeQuery();
            if (result.next()) return 0;
            else {
                PreparedStatement preStatement2 = connection.prepareStatement("SELECT * FROM users WHERE username='" + login + "';");
                ResultSet result2 = preStatement2.executeQuery();
                if (result2.next()) return 2;
                else return 1;
            }
        } catch (Exception e) {
            System.out.println("Login error");
            return -1;
        }
    }


    public boolean removePerson(String username, String name) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM persons WHERE name='"+name+"' AND username='"+username+"';");
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при удалении персонажа");
            return false;
        }
    }


    public int executeRegister(String login, String mail, String pass) {
        try {

            PreparedStatement iflog = connection.prepareStatement("SELECT * FROM users WHERE username='" + login + "';");
            ResultSet result = iflog.executeQuery();
            if (result.next()){return 0;}
            String hash = DataBaseConnection.encryptString(pass);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES ('" + login + "', '" + mail + "', '" + hash + "');");
            statement.executeUpdate();
            new Thread(() -> JavaMail.registration(mail, pass)).start();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst registration");
            return -1;
        }
    }


    public static String getToken() {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 8;
            Random random = new Random(); // get random string
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }

            //encrypt random string with SHA-224
            return buffer.toString();

        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-224
            MessageDigest md = MessageDigest.getInstance("SHA-224");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into sign representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Error whilst generating hashText of password");
            e.getMessage();
            return null;
        }
    }

}