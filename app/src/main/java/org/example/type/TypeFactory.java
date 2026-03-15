package org.example.type;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface TypeFactory {
    @NotNull
    PhpType createType(@NotNull String typeName);

    @NotNull
    PhpType createUnionType(@NotNull List<PhpType> types);
}
