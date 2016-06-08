/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.io.Closeable;
import java.io.StreamTokenizer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 *
 * @author Janco1
 */
public class Autentifikator implements Runnable {

    public static final int MAX_USEROV = 3;
    public static String hostname;
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql51.websupport.sk:3309/prijimackydb?characterEncoding=UTF-8";
    //  Database credentials
    static final String USER = "prijimackydbus";
    static final String PASS = "adatasuperiorsh93";
    private static Connection conn;
    private static Statement stmt;
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    public static final String APP_ID = "1.0";

    /**
     * skontrolujeme ako sa vola aktualny pocitac ak aktualny pocitac nie je v databaze, tak sa tam
     * prida za predpokladu ze sa nedosiahol limit userov
     *
     * @param database
     */
    public static void userCountAutentifikacia(Database database) {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (Exception ex) {
            System.out.println("Hostname can not be resolved");
        }
        if (hostname.equalsIgnoreCase("Unknown")) {
            JOptionPane.showMessageDialog(null, "Nepodarilo sa správne spustiť program. \n Overte či nie je tento program blokovaný firewallom.");
            System.exit(0);
        }
        hostname = hostname.replaceAll("'", "");
        if (hostname.length() > 20) {
            hostname = hostname.substring(0, 20);
        }
        System.out.println("hostname: " + hostname);
        Autentifikator.hostname = hostname;
        database.dbUserAutentifikacia(hostname);
    }

    /**
     * odosle statistiku na server
     */
    private static void odosliStatistiku(int[] statistika) {
        if (statistika == null) {
            return;
        }
        int topID = getTopID();
        topID++;
        StringBuilder sql = new StringBuilder(""
                //+ "SET @v1 := (select id from statistika order by id desc limit 1);\n"
                // + "SET @v1:=@v1+1;\n"
                + "INSERT INTO statistika (id,hostname,appid,stats,time_inserted)"
                //+ "VALUES (@v1+1,'" + hostname + "','" + Arrays.toString(statistika) + "',now()  );");
                + "VALUES (" + topID + ",'" + hostname + "','" + APP_ID + "','" + Arrays.toString(statistika) + "',now()  );");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            //stmt.executeQuery(sql.toString());
            stmt.executeUpdate(sql.toString());
        } catch (Exception exception) {
            Logger.getLogger(Autentifikator.class.getName()).log(Level.SEVERE, null, exception);
            System.out.println("BAD SQL:" + sql.toString());
        } finally {
            close();
        }
    }

    static void close(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // don't throw now as it might leave following closables in undefined state
        }
    }

    // you need to close all three to make sure
    static void close() {
        close((Closeable) resultSet);
        close((Closeable) statement);
        close((Closeable) connect);
    }

    private static int getTopID() {
        int topID = 0;
        String sql = "";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            sql = " select id from statistika order by id desc limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                topID = Integer.parseInt(rs.getString("id"));
            }

        } catch (Exception e) {
            Logger.getLogger(Autentifikator.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("BAD MYSQL: " + sql);
        } finally {
            close();
        }
        System.out.println("topID: " + topID);
        return topID;
    }
    public static int[] statistika;

    List<String> getUseri() {
        List<String> useri = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                throw new RuntimeException("no connection");
            }
            stmt = conn.createStatement();
            String sql;
            sql = "select hostname from (select * from statistika group by hostname) as T;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String hostname = rs.getString("hostname");
                useri.add(hostname);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Autentifikator.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            close();
        }
        return useri;
    }

    @Override
    public void run() {
        odosliStatistiku(statistika);
    }

    List<Statistika> getStatistika(String hostname) {
        List<Statistika> statistiky = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                throw new RuntimeException("no connection");
            }
            stmt = conn.createStatement();
            String sql;
            sql = "select stats, time_inserted from statistika where hostname='" + hostname + "' order by time_inserted desc limit 30;";
            //sql = "select stats from statistika where hostname='"+hostname+"' and id=136 ;";
            ResultSet rs = stmt.executeQuery(sql);
            String stats = "";
            while (rs.next()) {
                int[] statistika = new int[1501];
                stats = rs.getString("stats");
                stats = stats.trim();
                System.out.println(stats);
                stats = stats.substring(1, stats.length() - 1);
                System.out.println(stats);
                stats = stats.replaceAll(",", " ");
                System.out.println(stats);
                StringTokenizer st = new StringTokenizer(stats);
                int i = 0;
                while (st.hasMoreTokens()) {
                    statistika[i] = Integer.parseInt(st.nextToken());
                    i++;
                }
                Statistika nova = new Statistika();
                nova.stats = statistika;
                String cas = rs.getString("time_inserted");
                nova.cas = cas;
                statistiky.add(nova);
            }

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Autentifikator.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            close();
        }
        return statistiky;
    }

    public class Statistika {

        int[] stats;
        String cas;
    }
}
