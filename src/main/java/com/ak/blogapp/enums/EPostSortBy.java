package com.ak.blogapp.enums;

public enum EPostSortBy {
    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    CONTENT("content");

    private final String value;

    EPostSortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}