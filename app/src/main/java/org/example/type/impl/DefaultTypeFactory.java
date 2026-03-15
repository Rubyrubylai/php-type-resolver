package org.example.type.impl;

import java.util.List;

import org.example.type.PhpType;
import org.example.type.TypeFactory;
import org.jetbrains.annotations.NotNull;

public final class DefaultTypeFactory implements TypeFactory {
    @Override
    public @NotNull PhpType createType(@NotNull String typeName) {
        return new SimplePhpType(typeName);
    }

    @Override
    public @NotNull PhpType createUnionType(@NotNull List<PhpType> types) {
        return new UnionType(types);
    }
}
