package org.example.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.jetbrains.annotations.NotNull;

public final class SimplePhpDocBlock implements PhpDocBlock {
    private final List<DocTag> tags;

    public SimplePhpDocBlock(@NotNull List<DocTag> tags) {
        this.tags = Collections.unmodifiableList(new ArrayList<>(tags));
    }

    @Override
    public @NotNull List<DocTag> getTagsByName(@NotNull String tagName) {
        List<DocTag> matched = new ArrayList<>();
        for (DocTag tag : tags) {
            if (tagName.equals(tag.getName())) {
                matched.add(tag);
            }
        }
        return matched;
    }
}
