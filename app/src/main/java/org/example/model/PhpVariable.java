package org.example.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PhpVariable {
    @Nullable
    PhpDocBlock docBlock();

    @NotNull
    String name();
}
