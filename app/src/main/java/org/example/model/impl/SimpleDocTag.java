package org.example.model.impl;

import org.example.model.DocTag;
import org.jetbrains.annotations.NotNull;

public final class SimpleDocTag implements DocTag {
    private final String name;
    private final String value;

    public SimpleDocTag(@NotNull String name, @NotNull String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getValue() {
        return value;
    }
}
