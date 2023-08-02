package com.pmurawski.currencyrate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Rate(String code, @JsonProperty("mid") Double rate){

    public String getCode() {
        return code;
    }

    public Double getMid() {
        return rate;
    }
}
