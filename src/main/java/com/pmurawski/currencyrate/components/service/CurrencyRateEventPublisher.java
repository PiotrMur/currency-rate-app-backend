package com.pmurawski.currencyrate.components.service;

public interface CurrencyRateEventPublisher {
    void publish(String currencyCode, String name, double valueForCurrencyCode);
}
