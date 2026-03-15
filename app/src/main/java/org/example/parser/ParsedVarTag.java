package org.example.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ParsedVarTag(@NotNull String typeExpression, @Nullable String targetVariableName) {
}
