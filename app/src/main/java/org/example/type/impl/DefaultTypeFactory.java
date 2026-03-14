package org.example.type.impl;

import java.util.List;

import org.example.type.PhpType;
import org.example.type.TypeFactory;

public final class DefaultTypeFactory implements TypeFactory {
    @Override
    public PhpType createType(String typeName) {
        return new SimplePhpType(typeName);
    }

    @Override
    public PhpType createUnionType(List<PhpType> types) {
        return new UnionType(types);
    }
}
