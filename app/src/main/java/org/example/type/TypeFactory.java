package org.example.type;

import java.util.List;

public interface TypeFactory {
    PhpType createType(String typeName);

    PhpType createUnionType(List<PhpType> types);
}
