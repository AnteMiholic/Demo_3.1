package Database;

import hr.classes.classes.Users;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Database.Queries.*;


public class UserQuery {

    /*
    Konstatne varijable -> podaci za spajanje na bazu
     */
    final static String HOSTNAME = "/Documents/Database/GymBaza";
    final static String USER = "";
    final static String PWD = "";


    /*
    Metoda koja spaja javu na bazu, VRAĆA sql konekciju,
    kad se poziva, treba se i zatvoriti konekcija
     */
    public static Connection spajanjeNaBazu() {
        Connection conn = null;


        System.out.println("Connection is being established...");

        String url = "jdbc:h2:~" + HOSTNAME;
        try {
            conn = DriverManager.getConnection(url, USER, PWD);
            System.out.println("Database connection successfully established!");
        } catch (SQLException e) {
            System.out.println("Database connection crashed! Oops.");
            e.printStackTrace();
        }


        return conn;
    }


    /*
    Metoda koja ubacuje korisnika u bazu, validirati podatke prije nego što se poziva ova metoda (osim e-maila),
    moguće da sam i ja negdje zeznuo tak da "bear with me"

     */
    public static void insertUser(String uName, String uSurname, LocalDate uBirth, String uSex, String uEmail, String uPhoneNumber) {
        Connection conn = spajanjeNaBazu(); //ubaci phone_number
        try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER)) {
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());

            char uSexD = uSex.charAt(0);
            System.out.println(uSexD + uSex);
            preparedStatement.setString(1, uName);
            preparedStatement.setString(2, uSurname);
            preparedStatement.setDate(3, Date.valueOf(uBirth));
            preparedStatement.setString(4, String.valueOf(uSexD));
            preparedStatement.setString(5, uEmail);
            preparedStatement.setDate(6, sqlDate);
            preparedStatement.setString(7, uPhoneNumber);

            int row = preparedStatement.executeUpdate();

            // Provjera broja izmjenjenih redaka
            System.out.println(row);
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static List<Users> getUsers() {
        List<Users> users = new ArrayList<Users>();
        Connection conn = spajanjeNaBazu();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_USER);

            while (rs.next()) {
                int id = rs.getInt("uID");
                String name = rs.getString("uName");
                String surname = rs.getString("uSurname");
                String phoneNumber = rs.getString("uPhoneNumber");
                String gender = rs.getString("uSex");
                String email = rs.getString("uEmail");
                Date sqlDateOfRegistration = rs.getDate("uRegistration");
                LocalDate dateOfRegistration = sqlDateOfRegistration.toLocalDate();
                Date sqlDateOfBirth = rs.getDate("uBirth");
                LocalDate dateOfBirth = sqlDateOfBirth.toLocalDate();
                Users U = new Users(id, name, surname, phoneNumber, gender, email, dateOfRegistration, dateOfBirth);
                U.toString();
                users.add(U);
            }
            conn.close();
            return users;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }

    public static int getMaxUserID() {
        int maxID = 0;
        Connection conn = spajanjeNaBazu();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_USER);

            while (rs.next()) {
                maxID = rs.getInt("uID");

            }
            conn.close();
            return maxID;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxID;

    }

    public static Users getUserFromID(int ID) {
        Users u = null;
        Connection conn = spajanjeNaBazu();
        try (PreparedStatement preparedStatement = conn.prepareStatement(GET_USER_FROM_ID)) {

            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("uID");
                String name = rs.getString("uName");
                String surname = rs.getString("uSurname");
                String phoneNumber = rs.getString("uPhoneNumber");
                String gender = rs.getString("uSex");
                String email = rs.getString("uEmail");
                Date sqlDateOfRegistration = rs.getDate("uRegistration");
                LocalDate dateOfRegistration = sqlDateOfRegistration.toLocalDate();
                Date sqlDateOfBirth = rs.getDate("uBirth");
                LocalDate dateOfBirth = sqlDateOfBirth.toLocalDate();
                u = new Users(id, name, surname, phoneNumber, gender, email, dateOfRegistration, dateOfBirth);


            }
            conn.close();
            return u;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return u;
    }

    public static void changeUser(int id, String uName, String uSurname, LocalDate uBirth, String uSex, String uEmail,
                                  String uPhoneNumber) {
        Connection conn = spajanjeNaBazu(); //ubaci phone_number
        try (PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_USER)) {
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());

            char uSexD = uSex.charAt(0);
            System.out.println(uSexD + uSex);
            preparedStatement.setString(1, uName);
            preparedStatement.setString(2, uSurname);
            preparedStatement.setString(3, uPhoneNumber);
            preparedStatement.setDate(4, Date.valueOf(uBirth));
            preparedStatement.setString(5, String.valueOf(uSexD));
            preparedStatement.setString(6, uEmail);
            preparedStatement.setInt(7, id);

            int row = preparedStatement.executeUpdate();

            // Provjera broja izmjenjenih redaka
            System.out.println(row);
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int uID) {
        SubscriptionQueryClass.deleteSubscriptions(uID);

        Connection conn = spajanjeNaBazu();
        try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER)) {

            preparedStatement.setInt(1, uID);

            int row = preparedStatement.executeUpdate();
            System.out.println(row);
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randoQuery() {
        Connection con = spajanjeNaBazu();

        try {
            con.createStatement().execute(ALTER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Throwable ex) {

                }
            }


        }
    }
}