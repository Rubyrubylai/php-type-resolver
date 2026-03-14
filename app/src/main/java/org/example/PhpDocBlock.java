package org.example;

import java.util.List;

public interface PhpDocBlock {
    List<DocTag> getTagsByName(String tagName);
}
