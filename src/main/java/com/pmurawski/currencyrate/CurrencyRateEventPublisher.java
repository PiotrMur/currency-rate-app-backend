package com.pmurawski.currencyrate;

public interface CurrencyRateEventPublisher {
    void publish(String currencyCode, String name, double valueForCurrencyCode);
}
