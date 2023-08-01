package com.pmurawski.currencyrate.components.fetchingcurrencyrate;

public interface CurrencyRateEventPublisher {
    void publish(String currencyCode, String name, double valueForCurrencyCode);
}
