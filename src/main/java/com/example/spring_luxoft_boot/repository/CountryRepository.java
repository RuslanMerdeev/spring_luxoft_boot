package com.example.spring_luxoft_boot.repository;

import com.example.spring_luxoft_boot.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Repository
public class CountryRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbc;

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    public List<Country> getCountryList() {
        return namedJdbc.query("select * from country",
                COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        return namedJdbc.query("select * from country where name like :name",
                Collections.singletonMap("name", name + "%"),
                COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        namedJdbc.update("update country SET name=:name where code_name=:code_name",
                new HashMap<String, String>() {{
                    put("name", newCountryName);
                    put("code_name", codeName);
                }});
    }

    public Country getCountryByCodeName(String codeName) {
        return namedJdbc.query("select * from country where code_name = :code_name",
                Collections.singletonMap("code_name", codeName),
                COUNTRY_ROW_MAPPER).get(0);
    }

    public Country getCountryByName(String name) throws CountryNotFoundException {
        try {
            return namedJdbc.queryForObject("select * from country where name = :name",
                    Collections.singletonMap("name", name),
                    COUNTRY_ROW_MAPPER);
        } catch (Exception e) {
            throw new CountryNotFoundException(e);
        }
    }

    static public class CountryRowMapper implements RowMapper<Country> {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CODE_NAME = "code_name";

        public Country mapRow(ResultSet resultSet, int i) throws SQLException {
            Country country = new Country();
            country.setId(resultSet.getInt(ID));
            country.setName(resultSet.getString(NAME));
            country.setCodeName(resultSet.getString(CODE_NAME));

            return country;
        }
    }
}
