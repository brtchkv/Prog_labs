package server;

import shared.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
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
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM \"persons\";");
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
                if (skills != null) {
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
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO persons VALUES ('" + human.getName() + "', '" +
                    human.getClass().toString().replace("class Entities.", "") + "', '"
                    +human.getAge() + "', '"+ human.getSkills() + "', '" + human.getDisabilities() + "', '"
                    + human.getOwner() + "', '" + human.getDateTime().toString().replace("T", " ") +"');");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении в БД персонажа");
        }
    }

    public void savePersons(Vector<Human> humans) {
        try {
            Iterator<Human> iterator = humans.iterator();
            while (iterator.hasNext()) {
                Human h = iterator.next();
                Statement statement = connection.createStatement();
                String query = "UPDATE persons SET name ="+h.getName()+ ", "
                        + "age ="+h.getAge() + "skill =" + h.getSkills().toString() + " WHERE username='"+ h.getOwner() + "';";
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            System.out.println("Error whilst saving Humans to the DataBase");
        }
    }

    public int executeLogin(String login, String pass) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users WHERE username='" + login + "', "+ "hash='" + pass + "';");
            if (result.next()) return 0;
            else return 1;
        } catch (Exception e) {
            System.out.println("Login error");
            return -1;
        }
    }

//    public boolean checkAuthToken(Client client, String user, String token) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery("SELECT * FROM user_tokens WHERE username='" + user + "' AND "+ " auth_token='"+token+"';");
//            if (result.next()) {
//                LocalDateTime reg_time = LocalDateTime.parse(result.getString("auth_token_time").replace(" ", "T"));
//                LocalDateTime now = LocalDateTime.now();
//                long time = Duration.between(reg_time, now).get(SECONDS);
//                if (time > 90) {
//                    client.sendMessage(cActions.SEND, "Срок действия токена истёк\n");
//                    client.setIsAuth(false);
//                    client.setIsTokenValid(false);
//                    client.setHuman(null);
//                    client.sendMessage(cActions.DEAUTH, null);
//                    client.getServer().sendToAllClients(client.getUserName()+ " отключился от сервера.", null);
//                } else {
//                    statement.executeUpdate("UPDATE user_tokens SET auth_token_time='"+LocalDateTime.now()+"' WHERE username='"+user+"';");
//                    return true;
//                }
//            } else client.sendMessage(cActions.SEND, "Неверный токен\n");
//        } catch (Exception e) {
//            System.out.println("Ошибка при проверке токена");
//        }
//        return false;
//    }

    public String checkRegToken(String user, String token) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM user_tokens WHERE username='" + user + "' AND "+ " reg_token='"+token+"';");
            if (result.next()) {
                confirmRegister(user);
                return "Email registration is approved!";
            } else return "Wrong Token\n";
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Error while proof checking your token");
            return "Error while proof checking your token";
        }
    }

    public void setAuthToken(String username, String token) {
        try {
            Statement state = connection.createStatement();
            state.executeUpdate("UPDATE user_tokens SET auth_token='" + token + "', auth_token_time='"+LocalDateTime.now()+  "' WHERE username='"+username+"';");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении авторизационного токена");
        }
    }

    public void confirmRegister(String username) {
        try {
            Statement state = connection.createStatement();
            state.executeUpdate("UPDATE users SET email_conf='TRUE' WHERE username='"+username+"';");
        } catch (Exception e) {
            System.out.println("Ошибка при подтвеждении регистрации");
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

//    public void loadPersons(Client client) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery("SELECT * FROM persons WHERE username='" + client.getUserName() + "';");
//            while (result.next()) {
//                Human person = WorldManager.getInstance().getHuman(client.getUserName() + result.getString("name"));
//                client.addHuman(result.getString("name"), person);
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка при загрузке персонажей");
//        }
//    }

    public boolean executeRegister(String login, String mail, String hash) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users VALUES ('" + login + "', '" + mail + "', '" + hash + "');");
            new Thread(() -> JavaMail.registration(mail, hash)).start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error whilst registration");
            return false;
        }
    }


    public static String getToken() {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random(); // get random string
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }

            //encrypt random string with SHA-224

            return encryptString(buffer.toString());
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