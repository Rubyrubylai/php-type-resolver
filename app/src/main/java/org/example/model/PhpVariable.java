package org.example.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PhpVariable {
    @Nullable
    PhpDocBlock getDocBlock();

    @NotNull
    String getName();
}
