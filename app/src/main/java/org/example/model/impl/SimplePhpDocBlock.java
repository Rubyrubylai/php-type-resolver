package org.example.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.jetbrains.annotations.NotNull;

public record SimplePhpDocBlock(@NotNull List<DocTag> tags) implements PhpDocBlock {
    public SimplePhpDocBlock {
        tags = Collections.unmodifiableList(new ArrayList<>(tags));
    }

    @Override
    public @NotNull List<DocTag> getTagsByName(@NotNull String tagName) {
        List<DocTag> matched = new ArrayList<>();
        for (DocTag tag : tags) {
            if (tagName.equals(tag.name())) {
                matched.add(tag);
            }
        }
        return matched;
    }
}
