/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Janco1
 */
public class Database {

    private JdbcTemplate jdbcTemplate;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);


    public Database() {

        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:hsql://localhost:12345/prijimackydb");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    List<Otazka> getOtazkyListBiologia() {
        List<Otazka> otazkyVsetky = new ArrayList<>();
        Otazka defaultna = new Otazka();
        defaultna.otazka = "default";
        defaultna.id = 0;
        defaultna.jeSpravne = new boolean[8];
        defaultna.odpovede = new String[]{"default 1", "default 2", "default 3", "default 4", "default 5", "default 6", "default 7", "default 8"};
        otazkyVsetky.add(defaultna);
        try {
            RowMapper<Otazka> rowMapper = new OtazkaRowMapper();
            String sql = "SELECT * FROM biologia order by id asc";
            List<Otazka> otazky = jdbcTemplate.query(sql, rowMapper);
            otazkyVsetky.addAll(otazky);
            return otazkyVsetky;
        } catch (Exception exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
        }
        return null;
    }

    void inzertOtazkyBiologia(List<Otazka> otazky) {
        StringBuilder sql = new StringBuilder();
        int id = 0;
        // na prvej pozicii je defaultna otazka
        for (int i = 1; i < otazky.size(); i++) {
            Otazka otazka = otazky.get(i);
            id++;
            sql.append("INSERT INTO biologia (id,otazka,odpoved_1,odpoved_2,odpoved_3,odpoved_4,odpoved_5,odpoved_6,odpoved_7,odpoved_8,"
                    + "jeSpravna_1,jeSpravna_2,jeSpravna_3,jeSpravna_4,jeSpravna_5,jeSpravna_6,jeSpravna_7,jeSpravna_8)"
                    + "VALUES('" + id + "',"
                    + "'" + otazka.otazka + "',"
                    + "'" + otazka.odpovede[0] + "',"
                    + "'" + otazka.odpovede[1] + "',"
                    + "'" + otazka.odpovede[2] + "',"
                    + "'" + otazka.odpovede[3] + "',"
                    + "'" + otazka.odpovede[4] + "',"
                    + "'" + otazka.odpovede[5] + "',"
                    + "'" + otazka.odpovede[6] + "',"
                    + "'" + otazka.odpovede[7] + "',"
                    + "" + otazka.jeSpravne[0] + ","
                    + "" + otazka.jeSpravne[1] + ","
                    + "" + otazka.jeSpravne[2] + ","
                    + "" + otazka.jeSpravne[3] + ","
                    + "" + otazka.jeSpravne[4] + ","
                    + "" + otazka.jeSpravne[5] + ","
                    + "" + otazka.jeSpravne[6] + ","
                    + "" + otazka.jeSpravne[7] + "); \n");
             //       + "" + otazka.jeSpravne[7] + "); \n"
           // + "INSERT INTO Statistika_Biologia (otazka_id) VALUES('" + id + "');\n");
        }
        //System.out.println("INSERT SQL:" +sql.toString());
        if (sql.length() == 0) {
            return;
        }
        try {
            jdbcTemplate.execute(sql.toString());
        } catch (Exception exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
            System.out.println("BAD SQL:" + sql.toString());
        }
    }

    List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            RowMapper<String> rowMapper = new RowMapper() {

                @Override
                public Object mapRow(ResultSet rs, int i) throws SQLException {
                    return rs.getString("username");
                }
            };
            String sql = "SELECT * FROM users";
            List<String> usernames = jdbcTemplate.query(sql, rowMapper);
            // mame mena userov, teraz si stiahneme ich statistiky
            for (String s : usernames) {
                User novy = new User();
                novy.username = s;
                try {
                    RowMapper<StatistikaUsera> intRowMapper = new StatistikaUseraRowMapper(s);
                    sql = "SELECT otazka_id," + s + " FROM statistika_biologia order by otazka_id";
                    List<StatistikaUsera> values = jdbcTemplate.query(sql, intRowMapper);
                    int[] statistika = new int[values.get(values.size() - 1).otazka_id + 1];
                    for (StatistikaUsera su : values) {
                        statistika[su.otazka_id] = su.pocet;
                    }
                    novy.statistika = statistika;
                } catch (Exception exception) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
                }
                users.add(novy);
            }

            return users;
        } catch (Exception exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
        }

        return null;
    }

    void inzertUser(String username) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO users (username) VALUES ('" + username + "'); \n"
                + "alter table Statistika_Biologia add " + username + " integer default '0'; \n");

        //System.out.println("INSERT SQL:" +sql.toString());
        if (sql.length() == 0) {
            return;
        }
        try {
            jdbcTemplate.execute(sql.toString());
            changes.firePropertyChange("userAdded", sql, username);
        } catch (Exception exception) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
            System.out.println("BAD SQL:" + sql.toString());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    void updateUspesne(String aktUser, long id, boolean uspesne, int pocet) {
        // updatneme statistiku pre usera
        StringBuilder sql = new StringBuilder();
        if (pocet >= 0) {
            if (uspesne) {
                if (pocet < 3) {
                    // sme uspesni a boli sme oranzovi, tak zmenime na zelenu
                    sql.append("UPDATE statistika_biologia SET " + aktUser + "=" + aktUser + "+1 WHERE otazka_id=" + id + "  ;\n");
                } else {
                    // sme uspesni a boli sme zeleny tak ostaneme zeleny s 3kou
                    return;
                }
            } else {
                sql.append("UPDATE statistika_biologia SET " + aktUser + "=" + aktUser + "-1 WHERE otazka_id=" + id + "  ;\n");
            }
        } else {
            if (uspesne) {
                // bolo -1 a uhadli sme, tak davame hned na 1
                sql.append("UPDATE statistika_biologia SET " + aktUser + "=1 WHERE otazka_id=" + id + "  ;\n");
            } else {
                // bolo -1 a sme neuspesni tak sa nic nemeni
                return;
            }
        }
        //System.out.println("INSERT SQL:" +sql.toString());
        if (sql.length() == 0) {
            return;
        }
//        try {
            jdbcTemplate.execute(sql.toString());
//        } catch (Exception exception) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
//            System.out.println("BAD SQL:" + sql.toString());
//        }
    }

    
    
    void dbUserAutentifikacia(String hostname) {
        RowMapper<String> rowMapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getString(1);
            }
        };
        List<String> users = null;
        // najprv musime iba getnut userov a potom porovnavat ci tam uz je alebo nie

        StringBuilder sql = new StringBuilder("select * from allowed_users");
        try {
            users = jdbcTemplate.query(sql.toString(), rowMapper);
            // tabulka existuje, lebo neni vynimka, ideme sa hladat
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equalsIgnoreCase(hostname)) {
                    // nasli sme sa
                    System.out.println("nasli sme sa v zozname userov:users check OK!");
                    return;
                }
            }
            // nenasli sme sa v zozname userov, check ci mozeme pridat noveho usera
            if (users.size() >= Autentifikator.MAX_USEROV) {
                JOptionPane.showMessageDialog(null, "Túto kópiu programu nie je možné používať na viac ako 2 počítačoch!");
                System.exit(0);
            } else {
                // nie sme v zozname userov, ale sa este mozeme pridat
                sql = new StringBuilder("insert into allowed_users values('" + hostname + "'); select * from allowed_users");
                users = jdbcTemplate.query(sql.toString(), rowMapper);
                System.out.println("neboli sme v zozname, pridali sme sa: users check OK!");
                return;
            }
        } catch (Exception exception) {
            // nemame tabulku userov
            System.out.println("nemame tabulku userov" + exception);
            sql = new StringBuilder("CREATE TABLE allowed_users (pouzivatel varchar(255) NOT NULL UNIQUE); ");
            jdbcTemplate.execute(sql.toString());
            sql = new StringBuilder("insert into allowed_users values('" + hostname + "'); select * from allowed_users");
            users = jdbcTemplate.query(sql.toString(), rowMapper);
            System.out.println("vytvorena tabulka userov: users check OK! " + users);
        }
    }

    void updateOtazka(Otazka otazka) {
        StringBuilder sql=new StringBuilder();
        sql.append("UPDATE biologia set "
                + "otazka='"+otazka.otazka+"',"
                + "odpoved_1='"+otazka.odpovede[0]+"',"
                + "odpoved_2='"+otazka.odpovede[1]+"',"
                + "odpoved_3='"+otazka.odpovede[2]+"',"
                + "odpoved_4='"+otazka.odpovede[3]+"',"
                + "odpoved_5='"+otazka.odpovede[4]+"',"
                + "odpoved_6='"+otazka.odpovede[5]+"',"
                + "odpoved_7='"+otazka.odpovede[6]+"',"
                + "odpoved_8='"+otazka.odpovede[7]+"' "
                + "WHERE id="+otazka.id+";");

//        try {
            jdbcTemplate.execute(sql.toString());
//        } catch (Exception exception) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, exception);
//            System.out.println("BAD SQL:" + sql.toString());
//        }
    }

    void executeSkript(String sql) {
        jdbcTemplate.execute(sql);
    }
    
}
