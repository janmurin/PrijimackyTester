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
class StatistikaUseraRowMapper implements RowMapper<StatistikaUsera> {

    String s;

    public StatistikaUseraRowMapper(String s) {
        this.s = s;
    }

    @Override
    public StatistikaUsera mapRow(ResultSet rs, int i) throws SQLException {
        StatistikaUsera su = new StatistikaUsera();
        su.otazka_id = Integer.parseInt(rs.getString("otazka_id"));
        su.pocet = Integer.parseInt(rs.getString(s));
        return su;
    }

}
