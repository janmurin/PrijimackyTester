/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.prijimackytester;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Janco1
 */
class OtazkaRowMapper implements RowMapper<Otazka> {

    public OtazkaRowMapper() {
    }

    @Override
    public Otazka mapRow(ResultSet rs, int i) throws SQLException {
        Otazka o=new Otazka();
        o.id=Long.parseLong(rs.getString("id"));
        o.otazka=rs.getString("otazka");
        String[] odpovede=new String[8];
        odpovede[0]=rs.getString("odpoved_1");
        odpovede[1]=rs.getString("odpoved_2");
        odpovede[2]=rs.getString("odpoved_3");
        odpovede[3]=rs.getString("odpoved_4");
        odpovede[4]=rs.getString("odpoved_5");
        odpovede[5]=rs.getString("odpoved_6");
        odpovede[6]=rs.getString("odpoved_7");
        odpovede[7]=rs.getString("odpoved_8");
        o.odpovede=odpovede;
        boolean[] jeSpravne=new boolean[8];
        jeSpravne[0]=Boolean.parseBoolean(rs.getString("jeSpravna_1"));
        jeSpravne[1]=Boolean.parseBoolean(rs.getString("jeSpravna_2"));
        jeSpravne[2]=Boolean.parseBoolean(rs.getString("jeSpravna_3"));
        jeSpravne[3]=Boolean.parseBoolean(rs.getString("jeSpravna_4"));
        jeSpravne[4]=Boolean.parseBoolean(rs.getString("jeSpravna_5"));
        jeSpravne[5]=Boolean.parseBoolean(rs.getString("jeSpravna_6"));
        jeSpravne[6]=Boolean.parseBoolean(rs.getString("jeSpravna_7"));
        jeSpravne[7]=Boolean.parseBoolean(rs.getString("jeSpravna_8"));
        o.jeSpravne=jeSpravne;
        return o;
    }
    
}
