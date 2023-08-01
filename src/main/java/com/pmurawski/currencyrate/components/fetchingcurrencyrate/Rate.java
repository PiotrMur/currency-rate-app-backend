package com.pmurawski.currencyrate.components.fetchingcurrencyrate;

public class Rate {
    private String code;
    private Double mid;

    public Rate() {
    }

    public Rate(String code, Double mid) {
        this.code = code;
        this.mid = mid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }
}
