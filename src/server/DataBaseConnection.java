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
    private CommandHandler command;

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
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM \"humans\";");
            PreparedStatement getSkills;
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                String username = result.getString("username");
                String name = result.getString("name");
                int age = result.getInt("age");
                int skillId = result.getInt("skill_id");
                String date = result.getString("creation_date");
                if (date != null) {
                    time = OffsetDateTime.parse(result.getString("creation_date").replace(" ", "T"));
                }
                Human h = new Human(name, age);
                h.setDateTime(time);
                h.setOwner(username);

                getSkills = connection.prepareStatement("SELECT * FROM \"skills\" WHERE id=?");
                getSkills.setInt(1,skillId);
                ResultSet resSkill = getSkills.executeQuery();
                if (resSkill.next()) {
                    h.addSkill(new Skill(resSkill.getString("name"), resSkill.getString("info")));
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

    public void addToDB(Human h, String username) {
        try {
            addHuman(h, username);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while adding a human to a DataBase");
        }
    }

    private void addHuman(Human h, String username) throws SQLException {
        int skillId = h.getDateTime().getNano();
        PreparedStatement preStatement = connection.prepareStatement("INSERT INTO humans VALUES (?, ?, ?, ?, ?);");
        preStatement.setString(1,h.getName());
        preStatement.setInt(2,h.getAge());
        preStatement.setString(3,username);
        preStatement.setString(4,h.getDateTime().toString());
        preStatement.setInt(5,skillId);
        preStatement.executeUpdate();
        PreparedStatement statementSkills = connection.prepareStatement("INSERT INTO skills VALUES (?, ?, ?);");
        statementSkills.setInt(1,skillId);
        Iterator<Skill> iterator = h.getSkills().iterator();
        if (iterator.hasNext()) {
            Skill skill = iterator.next();
            statementSkills.setString(2, skill.getName());
            if (skill.getAction() != null && !skill.getAction().trim().isEmpty()) {
                statementSkills.setString(3, skill.getAction());
            }else {statementSkills.setString(3, "NULL");}
        } else {
            statementSkills.setString(2,"NULL");
            statementSkills.setString(3, "NULL");
        }
        statementSkills.executeUpdate();
    }

    public void savePersons(Vector<Human> humans) {
        try {
            if (humans != null) {

                Iterator<Human> iterator = humans.iterator();

                while (iterator.hasNext()) {
                    Human h = iterator.next();
                    addHuman(h, h.getOwner());
                }
                System.out.println("The DataBase has been updated.");
            } else {
                System.out.println("Collection is empty; nothing to save!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst saving Humans to the DataBase");
        }
    }

    public int executeLogin(String login, String hash) {
        try {
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM users WHERE username=? and hash=?;");
            preStatement.setString(1,login);
            preStatement.setString(2,hash);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) return 0;
            else {
                PreparedStatement preStatement2 = connection.prepareStatement("SELECT * FROM users WHERE username=?;");
                preStatement2.setString(1,login);
                ResultSet result2 = preStatement2.executeQuery();
                if (result2.next()) return 2;
                else return 1;
            }
        } catch (Exception e) {
            System.out.println("Login error");
            return -1;
        }
    }


    public boolean removePerson(String username, Human human) {
        try {
            String skills = !human.getSkills().toString().equals("[]") && human.getSkills().toString() != null ? human.getSkills().toString() : "NULL";
            PreparedStatement preStatement = connection.prepareStatement(("DELETE FROM humans WHERE name=? AND username=? AND age=? AND skill=?;"));
            preStatement.setString(1, human.getName());
            preStatement.setString(2,username);
            preStatement.setInt(3,human.getAge());
            preStatement.setString(4,skills);
            preStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error while removing a human from a DataBase");
            return false;
        }
    }


    public int executeRegister(String login, String mail, String pass) {
        try {

            PreparedStatement ifLog = connection.prepareStatement("SELECT * FROM users WHERE username=?;");
            ifLog.setString(1,login);
            ResultSet result = ifLog.executeQuery();
            if (result.next()){return 0;}
            String hash = DataBaseConnection.encryptString(pass);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?);");
            statement.setString(1, login);
            statement.setString(2, mail);
            statement.setString(3, hash);
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

            //encrypt random string with MD2
            return buffer.toString();

        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptString(String input)
    {
        try {
            // getInstance() method is called with algorithm MD2
            MessageDigest md = MessageDigest.getInstance("MD2");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into sign representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashText = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            // return the HashText
            return hashText.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println("Error whilst generating hashText of password");
            e.getMessage();
            return null;
        }
    }

}