package com.pmurawski.audit;

public class ValueRequestNameOnlyDTO {
    private String name;

    public ValueRequestNameOnlyDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
