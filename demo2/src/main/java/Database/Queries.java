package Database;

public interface Queries {
    /**
    SQL za Prepared statment -> ubacivanje korisnika u bazu
     */
    String INSERT_USER =
            "INSERT INTO USERS ( uName, uSurname, uBirth, uSex, uEmail, uRegistration, uPhoneNumber)" +
                    " VALUES (?,?,?,?,?,?,?)";

    /**
    SQL za Prepared statment -> uzimanje korisnika iz baze
     */
    String GET_USER ="SELECT * from users";

    /**
     * SQL za Prepared statment -> ubacivanje clanarine u bazuu
     */
    String INSERT_SUBSCRIPTION = "INSERT INTO SUBSCRIPTIONS (tip, subStart, subEnd, subFrozen, uID, created)" +
            " VALUES (?, ?, ? ,? ,?,? )";

    /**
     SQL za Prepared statment -> uzimanje clanarina iz baze
     */
    String GET_SUBSCRIPTIONS ="SELECT * from subscriptions";

    /**
     * SQL za Prepared statment -> uzimanje clanarina za korisnika
     */
      String GET_SUBS_FOR_USER = "SELECT TIP, SUBSTART, SUBEND,SUBFROZEN,SUBID, CREATED, COUNTER \n" +
            "      FROM USERS\n" +
            "      LEFT JOIN SUBSCRIPTIONS\n" +
            "      ON USERS.UID = SUBSCRIPTIONS.UID\n" +
            "      WHERE users.UID = ?" +
            "      ORDER BY SUBEND DESC;";


    /**
     * SQL za povlačenje članarina po IDu
     */

    String GET_MAX_USER = "SELECT MAX(UID) FROM USERS";

    String GET_USERS_SUBS = "SELECT * FROM USERSSUBS";

    String GET_USER_FROM_ID = "SELECT * FROM USERS" +
            "                       WHERE UID = ?";
    String GET_SUB_FROM_ID = "SELECT * FROM USERS" +
            "                   WHERE SUBID = ?";
    String CHANGE_USER = "UPDATE USERS \n" +
            "SET UNAME = ?,\n" +
            "USURNAME = ?,\n" +
            "UPHONENUMBER  = ?,\n" +
            "UBIRTH = ?,\n" +
            "USEX = ?,\n" +
            "UEMAIL = ?\n" +
            "WHERE UID = ?";

        String CHANGE_SUBSCRIPTION = "UPDATE SUBSCRIPTIONS \n" +
            "SET TIP = ?,\n" +
            "SUBSTART = ?,\n" +
            "SUBEND = ?,\n" +
            "CREATED = ?,\n" +
            "SUBFROZEN = ?\n" +
            "WHERE SUBID = ?";

    String CHANGE_SUBSCRIPTION1 = "UPDATE SUBSCRIPTIONS \n" +
            "SET TIP = ?,\n" +
            "SUBSTART = ?,\n" +
            "SUBEND = ?,\n" +
            "CREATED = ?,\n" +
            "SUBFROZEN = ?,\n" +
            "WHERE SUBID = ?";

        String UNFREEZE_SUBSCRIPTION = "UPDATE SUBSCRIPTIONS \n" +
                "SET SUBFROZEN = 0, \n" +
                "SUBEND = ?, \n" +
                "COUNTER = ? \n" +
                "WHERE SUBID = ?";

        String FREEZE_SUBSCRIPTION = "UPDATE SUBSCRIPTIONS \n"+
                "SET SUBFROZEN = 1,\n" +
                "CREATED = ? \n" +
                "WHERE SUBID = ?";

        String DELETE_SUBSCRIPTION = "DELETE FROM SUBSCRIPTIONS \n" +
                "WHERE SUBID = ?";

        String EXCECUTE_ORDER_66 = "DELETE FROM SUBSCRIPTIONS \n" +
                "WHERE UID = ?";

        String DELETE_USER = "DELETE FROM USERS \n" +
                " WHERE UID = ?";
        String ALTER = "ALTER TABLE USERS \n" +
                "ALTER COLUMN UBIRTH DATE;";

}
