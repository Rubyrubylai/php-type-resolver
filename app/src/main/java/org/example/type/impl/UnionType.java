package org.example.type.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.example.type.PhpType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UnionType implements PhpType {
    private final List<PhpType> types;

    public UnionType(@NotNull List<PhpType> types) {
        Objects.requireNonNull(types);
        this.types = Collections.unmodifiableList(new ArrayList<>(types));
    }

    public @NotNull List<PhpType> getTypes() {
        return types;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UnionType other)) {
            return false;
        }
        return types.equals(other.types);
    }

    @Override
    public int hashCode() {
        return types.hashCode();
    }

    @Override
    public @NotNull String toString() {
        return types.toString();
    }
}
