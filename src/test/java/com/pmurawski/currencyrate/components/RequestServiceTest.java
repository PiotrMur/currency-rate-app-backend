package com.pmurawski.currencyrate.components;

import com.pmurawski.currencyrate.components.dtos.ValueRequestDTO;
import com.pmurawski.currencyrate.components.service.RequestDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = RequestDAO.class)
class RequestServiceTest {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RequestDAO requestDAO;


    @Test
    void shouldStartSavingData() {
        //given
        String currencyCode = "USD";
        String name = "Kuba Guzik";
        Double value = 5.231;
        ValueRequestDTO entity = new ValueRequestDTO(currencyCode, name, value);

        //when
        requestDAO.publish(currencyCode, name, value);

        //then
        verify(jdbcTemplate).update(
                "INSERT INTO requests (currency, name, request_date, value_rate) VALUES (?,?,?,?)",
                entity.getCurrency(), entity.getName(), entity.getRequestDate(), entity.getValueRate()
        );
    }


}