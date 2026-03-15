package org.example.model;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public interface PhpDocBlock {
    @NotNull
    List<DocTag> getTagsByName(@NotNull String tagName);
}
