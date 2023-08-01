package com.pmurawski.currencyrate.components.fetchingcurrencyrate;

import java.util.List;

public class RatesCollection {
    private List<Rate> rates;

    public RatesCollection() {
    }

    public RatesCollection(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
