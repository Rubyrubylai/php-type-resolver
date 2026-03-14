package org.example.model.impl;

import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;

public final class SimplePhpVariable implements PhpVariable {
    private final String name;
    private final PhpDocBlock docBlock;

    public SimplePhpVariable(String name, PhpDocBlock docBlock) {
        this.name = name;
        this.docBlock = docBlock;
    }

    @Override
    public PhpDocBlock getDocBlock() {
        return docBlock;
    }

    @Override
    public String getName() {
        return name;
    }
}
