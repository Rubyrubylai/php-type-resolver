package org.example.model.impl;

import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SimplePhpVariable implements PhpVariable {
    private final String name;
    private final PhpDocBlock docBlock;

    public SimplePhpVariable(@NotNull String name, @Nullable PhpDocBlock docBlock) {
        this.name = name;
        this.docBlock = docBlock;
    }

    @Override
    public @Nullable PhpDocBlock getDocBlock() {
        return docBlock;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
