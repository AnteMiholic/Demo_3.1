package Database;

import hr.classes.classes.Subscriptions;
import hr.classes.classes.UsersSubs;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.toIntExact;

import static Database.Queries.*;

import java.time.temporal.ChronoUnit.*;


public class SubscriptionQueryClass {
    public static void insertSubscription(String tip, LocalDate subStart, LocalDate subEnd, int uID){
        Connection conn = Database.UserQuery.spajanjeNaBazu();
        try(PreparedStatement preparedStatement = conn.prepareStatement(Database.Queries.INSERT_SUBSCRIPTION)) {

            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());



            preparedStatement.setString(1, tip);
            preparedStatement.setDate(2, Date.valueOf(subStart));
            preparedStatement.setDate(3, Date.valueOf(subEnd));
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5,uID);
            preparedStatement.setDate(6, sqlDate);



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
    public static List<Subscriptions> getSubscriptions() {
        List<Subscriptions> subs = new ArrayList<Subscriptions>();
        Connection conn = Database.UserQuery.spajanjeNaBazu();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(Database.Queries.GET_SUBSCRIPTIONS);

            while (rs.next()) {
                int id = rs.getInt("subID");

                String tip = rs.getString("tip");
                Date sqlDateOfStart = rs.getDate("subStart");
                LocalDate dateOfStart = sqlDateOfStart.toLocalDate();

                Date sqlDateOfEnd = rs.getDate("subEnd");
                LocalDate dateOfEnd = sqlDateOfEnd.toLocalDate();


                int subFrozen = rs.getInt("subFrozen");

                int uID = rs.getInt("uID");

                Date sqlCreated = rs.getDate("created");
                LocalDate created = sqlCreated.toLocalDate();



                Subscriptions U = new Subscriptions(id, "Grupna", dateOfStart, dateOfEnd, subFrozen, uID, created);
                subs.add(U);

            }
            conn.close();
            return subs;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subs;

    }
    public static List<UsersSubs> getUsersSubs(){
        List<UsersSubs> usersSubs = new ArrayList<UsersSubs>();
        Connection conn = Database.UserQuery.spajanjeNaBazu();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Database.Queries.GET_USERS_SUBS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                LocalDate dateOfStart = null;
                LocalDate dateOfEnd = null;

                int uID = rs.getInt("uID");

                String name = rs.getString("uName");
                String surname = rs.getString("uSurname");

                String tip = rs.getString("tip");

                Date sqlDateOfStart = rs.getDate("subStart");
                if(sqlDateOfStart != null){
                    dateOfStart = sqlDateOfStart.toLocalDate();
                }
                Date sqlDateOfEnd = rs.getDate("subEnd");

                if(sqlDateOfStart != null){
                    dateOfEnd = sqlDateOfEnd.toLocalDate();
                }



                int subFrozen = rs.getInt("subFrozen");


                UsersSubs U = new UsersSubs(uID,name, surname,  dateOfStart, dateOfEnd, tip, subFrozen);
                usersSubs.add(U);

            }

            return usersSubs;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersSubs;
    }
    public static List<Subscriptions> getSubsForUser(int Uid){
        List<Subscriptions> subList = new ArrayList<Subscriptions>();
        Connection conn = Database.UserQuery.spajanjeNaBazu();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Database.Queries.GET_SUBS_FOR_USER)) {

            preparedStatement.setInt(1, Uid);


            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                LocalDate dateOfStart = null;
                LocalDate dateOfEnd = null;

                String tip = rs.getString("tip");

                Date sqlDateOfStart = rs.getDate("subStart");
                //System.out.println(rs.getDate("subStart"));
                if(sqlDateOfStart != null) {
                    dateOfStart = sqlDateOfStart.toLocalDate();
                }
                Date sqlDateOfEnd = rs.getDate("subEnd");
                if (sqlDateOfEnd != null){
                    dateOfEnd = sqlDateOfEnd.toLocalDate();
                }



                int subFrozen = rs.getInt("subFrozen");

                int subID = rs.getInt("subID");

                Date sqlDateOfCreated = rs.getDate("created");
                LocalDate dateOfCreated = sqlDateOfCreated.toLocalDate();
                int counter = rs.getInt("counter");


                Subscriptions s = new Subscriptions(subID, tip, dateOfStart, dateOfEnd, subFrozen, Uid, dateOfCreated,
                        counter );
                subList.add(s);
            }


            return subList;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subList;
    }
    public static Subscriptions getSubFromID(int ID) {
        Subscriptions s = null;
        Connection conn = Database.UserQuery.spajanjeNaBazu();
        try (PreparedStatement preparedStatement = conn.prepareStatement(Database.Queries.GET_SUB_FROM_ID)) {

            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String tip = rs.getString("tip");

                Date sqlDateOfStart = rs.getDate("subStart");
                //System.out.println(rs.getDate("subStart"));
                LocalDate dateOfStart = sqlDateOfStart.toLocalDate();

                Date sqlDateOfEnd = rs.getDate("subEnd");
                LocalDate dateOfEnd = sqlDateOfEnd.toLocalDate();

                int subFrozen = rs.getInt("subFrozen");

                int subID = rs.getInt("subID");

                Date sqlDateOfCreated = rs.getDate("created");
                LocalDate dateOfCreated = sqlDateOfCreated.toLocalDate();


                s = new Subscriptions(subID, tip, dateOfStart, dateOfEnd, subFrozen, dateOfCreated );


            }
            conn.close();
            return s;
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }
    public static void changeSubscription(String tip, LocalDate subStart, LocalDate subEnd, int subFrozen, int subID){

        Connection conn = UserQuery.spajanjeNaBazu(); //ubaci phone_number
        try(PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_SUBSCRIPTION)) {

            preparedStatement.setString(1, tip);
            preparedStatement.setDate(2, Date.valueOf(subStart));
            preparedStatement.setDate(3, Date.valueOf(subEnd));
            preparedStatement.setDate(4, getCurrentDate());
            preparedStatement.setInt(5, subFrozen);
            preparedStatement.setInt(6, subID);

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
    public static void freezeSubscription(int subID, LocalDate datumFrozen){

        Connection conn = UserQuery.spajanjeNaBazu();
        try(PreparedStatement preparedStatement = conn.prepareStatement(FREEZE_SUBSCRIPTION)) {
            Date sqlFrozen = Date.valueOf(datumFrozen);
            preparedStatement.setDate(1, sqlFrozen);
            preparedStatement.setInt(2,subID);

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
    public static void unfreezeSubscription(int subID, LocalDate subEnd, LocalDate subUnFrozen, LocalDate subFrozen){

        Connection conn = UserQuery.spajanjeNaBazu(); //ubaci phone_number
        try(PreparedStatement preparedStatement = conn.prepareStatement(UNFREEZE_SUBSCRIPTION)) {

            long counter = ChronoUnit.DAYS.between(subUnFrozen,subFrozen);
            counter = abs(counter);
            long daysBetween = ChronoUnit.DAYS.between(subEnd, subFrozen);
            //System.out.println("Dana između: " + daysBetween);

            Date sqlSubEnd = Date.valueOf(subEnd.plusDays(counter));

            subUnFrozen.plusDays(counter);
            System.out.println("COUNTER JE: " + counter);
            preparedStatement.setDate(1,sqlSubEnd);
            Integer c =  toIntExact(counter);
            System.out.println(c);
            preparedStatement.setInt(2, c);
            preparedStatement.setInt(3,subID);

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
   public static void deleteSubscription(int subID){
        Connection conn = UserQuery.spajanjeNaBazu();
        try(PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SUBSCRIPTION)){

            preparedStatement.setInt(1, subID);

            int row = preparedStatement.executeUpdate();
            System.out.println(row);
            conn.close();
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    public static Date getCurrentDate(){
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        return date;
    }

    public static void deleteSubscriptions(int subID){
        Connection conn = UserQuery.spajanjeNaBazu();
        try(PreparedStatement palpatine = conn.prepareStatement(EXCECUTE_ORDER_66)){

            palpatine.setInt(1, subID);

            int row = palpatine.executeUpdate();
            System.out.println(row);
            conn.close();
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


//tes