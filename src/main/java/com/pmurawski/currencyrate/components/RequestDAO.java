package com.pmurawski.currencyrate.components;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class RequestService {

    private final JdbcTemplate jdbcTemplate;

    public RequestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ValueRequestEntity> fetchAllRequests() {
        String sql = "SELECT name, currency, request_date, value_rate FROM requests";
        return jdbcTemplate.query(sql, (resultSet, i) -> toValueRequestEntity(resultSet));
    }

    private ValueRequestEntity toValueRequestEntity(ResultSet resultSet) throws SQLException {
        ValueRequestEntity value = new ValueRequestEntity();
        value.setName(resultSet.getString("NAME"));
        value.setCurrency(resultSet.getString("CURRENCY"));
        value.setDate(resultSet.getTimestamp("REQUEST_DATE").toLocalDateTime());
        value.setValue(resultSet.getDouble("VALUE_RATE"));
        return value;
    }

    @Transactional
    public void saveCurrencyValueRequest(String currencyCode, String name, Double value) {
        ValueRequestEntity entity = new ValueRequestEntity(currencyCode, name, value);
        saveEntityToDatabase(entity);
    }

    private void saveEntityToDatabase(ValueRequestEntity entity) {
        String sql = "INSERT INTO requests (currency, name, request_date, value_rate) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, entity.getCurrency(), entity.getName(), entity.getDate(), entity.getValue());
    }
}
