package org.example.model.impl;

import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record SimplePhpVariable(@NotNull String name, @Nullable PhpDocBlock docBlock) implements PhpVariable {
}
