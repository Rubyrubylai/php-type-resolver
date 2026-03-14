package org.example.model;

import java.util.List;

public interface PhpDocBlock {
    List<DocTag> getTagsByName(String tagName);
}
