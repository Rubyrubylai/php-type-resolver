package org.example.type.impl;

import org.example.type.PhpType;
import org.jetbrains.annotations.NotNull;

public record SimplePhpType(@NotNull String name) implements PhpType {
    @Override
    public @NotNull String toString() {
        return name;
    }
}
