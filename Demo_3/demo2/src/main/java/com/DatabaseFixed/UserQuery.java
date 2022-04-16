package com.DatabaseFixed;

import hr.classes.classes.Users;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserQuery {
    private static final String POST_URL = "https://sd.zagnus.eu/temp/API.php";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

    private static final String POST_PARAMS = "SQL_query=SELECT * FROM users";

    public static void main(String[] args) throws IOException {

        sendPOST();
        System.out.println("POST DONE");
    }

    public static void sendPOST() throws IOException {
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {

            //parsing a CSV file into Scanner class constructor
            Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(
                    con.getInputStream())));

            int i = 0;
            sc.useDelimiter("</br>");
            List<String> lines = new ArrayList<String>();
            while(sc.hasNextLine()){

                lines.add(sc.next());

            }




            sc.close();
            /*

          __
     w  c(..)o   (
      \__(-)    __)
          /\   (
         /(_)___)
         w /|
          | \
          m  m

             */
            lines.remove(0);
            lines.remove(0);

            lines.remove(lines.size() - 1);
            System.out.println(lines.toString());
            for (String l: lines){
                readUser(l);
            }


        } else {
            System.out.println("POST request not worked");
        }
    }

    private static void readUser(String l) {
        String[] elements = l.split(";");

        List<String> fixedLenghtList = Arrays.asList(elements);

        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

        System.out.println("list from comma separated String : " + listOfString);
        System.out.println("size of ArrayList : " + listOfString.size());
        System.out.println(listOfString.get(0));
        Users janez = new Users(Integer.parseInt(listOfString.get(0)),listOfString.get(1),listOfString.get(2),listOfString.get(3),listOfString.get(4), LocalDate.parse(listOfString.get(5), formatter));

    }
}
