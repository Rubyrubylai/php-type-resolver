package org.example.model;

import org.jetbrains.annotations.NotNull;

public interface DocTag {
    @NotNull
    String name();

    @NotNull
    String value();
}
