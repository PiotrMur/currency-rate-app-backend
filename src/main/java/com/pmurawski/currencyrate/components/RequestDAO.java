package com.pmurawski.currencyrate.components;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class RequestDAO {

    private final JdbcTemplate jdbcTemplate;

    public RequestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ValueRequestDTO> fetchAllRequests() {
        String sql = "SELECT name, currency, request_date, value_rate FROM requests";
        return jdbcTemplate.query(sql, (resultSet, i) -> toValueRequestEntity(resultSet));
    }

    private ValueRequestDTO toValueRequestEntity(ResultSet resultSet) throws SQLException {
        ValueRequestDTO value = new ValueRequestDTO();
        value.setName(resultSet.getString("NAME"));
        value.setCurrency(resultSet.getString("CURRENCY"));
        value.setDate(resultSet.getTimestamp("REQUEST_DATE").toLocalDateTime());
        value.setValue(resultSet.getDouble("VALUE_RATE"));
        return value;
    }

    @Transactional
    public void saveCurrencyValueRequest(String currencyCode, String name, Double value) {
        ValueRequestDTO entity = new ValueRequestDTO(currencyCode, name, value);
        saveEntityToDatabase(entity);
    }

    private void saveEntityToDatabase(ValueRequestDTO entity) {
        String sql = "INSERT INTO requests (currency, name, request_date, value_rate) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, entity.getCurrency(), entity.getName(), entity.getDate(), entity.getValue());
    }
}
