package org.example.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;

public final class SimplePhpDocBlock implements PhpDocBlock {
    private final List<DocTag> tags;

    public SimplePhpDocBlock(List<DocTag> tags) {
        Objects.requireNonNull(tags);
        this.tags = Collections.unmodifiableList(new ArrayList<>(tags));
    }

    @Override
    public List<DocTag> getTagsByName(String tagName) {
        if (tagName == null) {
            return Collections.emptyList();
        }

        List<DocTag> matched = new ArrayList<>();
        for (DocTag tag : tags) {
            if (tagName.equals(tag.getName())) {
                matched.add(tag);
            }
        }
        return matched;
    }
}
