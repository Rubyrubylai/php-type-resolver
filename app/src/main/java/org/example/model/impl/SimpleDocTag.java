package org.example.model.impl;

import org.example.model.DocTag;
import org.jetbrains.annotations.NotNull;

public record SimpleDocTag(@NotNull String name, @NotNull String value) implements DocTag {
}
