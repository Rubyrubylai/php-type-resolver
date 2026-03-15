package org.example.type.impl;

import java.util.Objects;

import org.example.type.PhpType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SimplePhpType implements PhpType {
    private final String name;

    public SimplePhpType(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public @NotNull String getName() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimplePhpType other)) {
            return false;
        }
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public @NotNull String toString() {
        return name;
    }
}
