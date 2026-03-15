package org.example.model;

import org.jetbrains.annotations.NotNull;

public interface DocTag {
    @NotNull
    String getName();

    @NotNull
    String getValue();
}
