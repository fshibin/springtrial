package com.thefans.sdjdbc4mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertData() {
        jdbcTemplate.execute("INSERT INTO Product(name,description) VALUES('Honey','Manukau honey produced in New Zealand')");
        jdbcTemplate.execute("INSERT INTO Product(name,description) VALUES('Almond','Nuts imported from Austrilia')");
        jdbcTemplate.execute("INSERT INTO Product(name,description) VALUES('Kumara', 'Homegrown sweet potato good for health')");
        jdbcTemplate.execute("INSERT INTO Product(name,description) VALUES('feijo', 'Many gardens have feijo trees full of fruits')");
    }
}