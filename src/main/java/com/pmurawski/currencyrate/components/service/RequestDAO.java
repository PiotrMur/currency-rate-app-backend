package com.pmurawski.currencyrate.components.service;

import com.pmurawski.currencyrate.components.dtos.ValueRequestDTO;
import com.pmurawski.currencyrate.components.dtos.ValueRequestNameOnlyDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class RequestDAO implements CurrencyRateEventPublisher {

    private final JdbcTemplate jdbcTemplate;

    public RequestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ValueRequestDTO> fetchAllRequests() {
        String sql = "SELECT * FROM requests";
        try {
            return jdbcTemplate.query(sql, (rs, i) -> toValueRequestEntity(rs));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update request", e);
        }
    }

    private ValueRequestDTO toValueRequestEntity(ResultSet resultSet) throws SQLException {
        ValueRequestDTO value = new ValueRequestDTO();
        value.setId(UUID.fromString(resultSet.getString("ID")));
        value.setName(resultSet.getString("NAME"));
        value.setCurrency(resultSet.getString("CURRENCY"));
        value.setRequestDate(resultSet.getTimestamp("REQUEST_DATE").toLocalDateTime());
        value.setValueRate(resultSet.getDouble("VALUE_RATE"));
        return value;
    }

    public ValueRequestDTO fetchRequest(UUID requestId) {
        if (requestId == null) {
            throw new IllegalArgumentException("Request id cannot be null");
        }
        String sql = "SELECT id, currency, name, request_date, value_rate FROM requests WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(ValueRequestDTO.class), requestId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch request with id [" + requestId + "]", e);
        }
    }

    @Transactional
    @Override
    public void publish(String currencyCode, String name, double value) {
        if (currencyCode == null || name == null || value < 0) {
            throw new IllegalArgumentException("Currency code and name cannot be null and value cannot be less than 0");
        }
        ValueRequestDTO entity = new ValueRequestDTO(currencyCode, name, value);
        saveEntityToDatabase(entity);
    }

    private void saveEntityToDatabase(ValueRequestDTO entity) {
        if (entity.getId() == null || entity.getCurrency() == null || entity.getName() == null || entity.getRequestDate() == null || entity.getValueRate() == null) {
            throw new IllegalArgumentException("Id, currency, name, request date, value rate cannot be null");
        }
        String sql = "INSERT INTO requests (id, currency, name, request_date, value_rate) VALUES (?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql, entity.getId(), entity.getCurrency(), entity.getName(), entity.getRequestDate(), entity.getValueRate());
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update request", e);
        }
    }

    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        String sql = "DELETE FROM requests WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update request", e);
        }
    }

    public void updateName(ValueRequestNameOnlyDTO nameUpdate, UUID requestId) {
        if (nameUpdate.getName() == null || requestId == null) {
            throw new IllegalArgumentException("CName or request id cannot be null");
        }
        String name = nameUpdate.getName();
        String sql = "UPDATE requests SET name = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, name, requestId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update request", e);
        }
    }

    public Integer calculateRequests() {
        String sql = "SELECT COUNT(*) FROM requests";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to update request", e);
        }
    }
}
