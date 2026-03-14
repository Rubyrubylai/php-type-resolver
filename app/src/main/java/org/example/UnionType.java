package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class UnionType implements PhpType {
    private final List<PhpType> types;

    public UnionType(List<PhpType> types) {
        Objects.requireNonNull(types);
        this.types = Collections.unmodifiableList(new ArrayList<>(types));
    }

    public List<PhpType> getTypes() {
        return types;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UnionType other)) {
            return false;
        }
        return types.equals(other.types);
    }

    @Override
    public int hashCode() {
        return types.hashCode();
    }

    @Override
    public String toString() {
        return types.toString();
    }
}
