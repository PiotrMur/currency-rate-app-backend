package com.pmurawski.currencyrate.components;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CurrencyRateService currencyRateService;

    @MockBean
    RequestDAO requestDAO;

    @Autowired
    private JacksonTester<List<ValueRequestDTO>> jacksonTester;

    @Test
    void shouldReturnCurrencyRate() throws Exception {

        doReturn(5.234).when(currencyRateService).fetchCurrencyRateForCurrencyCode("EUR", "Jan Nowak");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/currencies/get-current-currency-value-command").content("""
                                        {
                                            "currency": "EUR",
                                            "name": "Jan Nowak"
                                        }
                                        """)
                                .contentType(APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).contains("\"value\":5.234");
    }

    @Test
    void shouldFetchAllRequests() throws Exception {
        //given
        Clock fixedClock = Clock.fixed(Instant.parse("2023-07-24T21:28:34.1052228Z"), ZoneId.of("Europe/Warsaw"));

        ValueRequestDTO initialRequest = createSampleRequest(fixedClock);

        ValueRequestDTO expectedResult = createSampleRequest(fixedClock);


        List<ValueRequestDTO> listOfEntities = List.of(initialRequest);

        doReturn(listOfEntities).when(requestDAO).fetchAllRequests();

        //when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/currencies/requests")
                                .contentType(APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                (jacksonTester.write(List.of(expectedResult)).getJson()
                ));
    }

    private static ValueRequestDTO createSampleRequest(Clock fixedClock) {
        ValueRequestDTO expectedResult = new ValueRequestDTO("EUR", "Kuba Guzik", 4.345);
        expectedResult.setRequestDate(LocalDateTime.now(fixedClock));
        return expectedResult;
    }
}