package com.m2dl.nater.data;

import java.util.ArrayList;
import java.util.List;

public class KeyNode {
    private List<KeyNode> choices = new ArrayList<>();
    private String value;

    public void setChoices(List<KeyNode> choices) {
        this.choices = choices;
    }

    public List<KeyNode> getChoices() {
        return choices;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
