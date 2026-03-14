package org.example;

import java.util.List;

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
