package org.example.type.impl;

import java.util.Objects;

import org.example.type.PhpType;

public final class SimplePhpType implements PhpType {
    private final String name;

    public SimplePhpType(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimplePhpType other)) {
            return false;
        }
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
