package org.example.type.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.type.PhpType;
import org.jetbrains.annotations.NotNull;

public record UnionType(@NotNull List<PhpType> types) implements PhpType {
    public UnionType {
        types = Collections.unmodifiableList(new ArrayList<>(types));
    }

    @Override
    public @NotNull String toString() {
        return types.toString();
    }
}
