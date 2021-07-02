package de.karottenboy33.clansysteme.mysql;

import de.karottenboy33.clansysteme.ClanSysteme;

import java.sql.*;

public class MySQLCreate {

    private static String host = ClanSysteme.host;
    private static int port = 3306;
    private static String database= ClanSysteme.database;
    private static String user = ClanSysteme.username;
    private static String password = ClanSysteme.password;

    private static Connection con;

    @SuppressWarnings("static-access")
    public MySQLCreate(String host, String database, String user, String password) {
        host = host;
        database = database;
        user = user;
        password = password;

        connect();
    }

    public static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", user, password);
            System.out.println("§7ZinseSys: §aMySQL wurde verbunden...");
        } catch (SQLException e) {
            System.out.println("[MySQL] Die Verbindung zur MySQL ist fehlgeschlagen! Fehler: " + e.getMessage());
        }
    }

    public static void close() {
        try {
            if(con != null) {
                con.close();
                System.out.println("[MySQL] Die Verbindung zur MySQL wurde Erfolgreich beendet!");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Fehler beim beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
        }
    }

    public static void update(String qry) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
    }

    public static ResultSet query(String qry) {
        ResultSet rs = null;

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static void createsUserTable() {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS clanuser (uuid TEXT, clanid TEXT)");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createsClanTable() {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS clan (clanid TEXT, name TEXT, ownerid TEXT)");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
