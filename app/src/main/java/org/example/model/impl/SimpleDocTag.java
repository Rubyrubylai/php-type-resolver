package org.example.model.impl;

import java.util.Objects;

import org.example.model.DocTag;

public final class SimpleDocTag implements DocTag {
    private final String name;
    private final String value;

    public SimpleDocTag(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
