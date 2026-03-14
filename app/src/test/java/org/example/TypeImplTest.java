package org.example;

import java.util.ArrayList;
import java.util.List;

import org.example.type.PhpType;
import org.example.type.TypeFactory;
import org.example.type.impl.DefaultTypeFactory;
import org.example.type.impl.SimplePhpType;
import org.example.type.impl.UnionType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TypeImplTest {

    @Test
    public void simplePhpTypeValueEqualityWorks() {
        SimplePhpType typeA = new SimplePhpType("User");
        SimplePhpType typeB = new SimplePhpType("User");

        assertEquals(typeA, typeB);
        assertEquals(typeA.hashCode(), typeB.hashCode());
        assertEquals("User", typeA.toString());
    }

    @Test
    public void unionTypeStoresImmutableCopyOfTypes() {
        List<PhpType> source = new ArrayList<>();
        source.add(new SimplePhpType("string"));
        UnionType unionType = new UnionType(source);
        source.add(new SimplePhpType("int"));

        assertEquals(1, unionType.getTypes().size());
        assertEquals(new SimplePhpType("string"), unionType.getTypes().get(0));
    }

    @Test
    public void unionTypeEqualityWorksByMembers() {
        UnionType a = new UnionType(List.of(new SimplePhpType("string"), new SimplePhpType("int")));
        UnionType b = new UnionType(List.of(new SimplePhpType("string"), new SimplePhpType("int")));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void defaultTypeFactoryCreatesSimpleType() {
        TypeFactory factory = new DefaultTypeFactory();

        PhpType result = factory.createType("User");

        assertTrue(result instanceof SimplePhpType);
        assertEquals("User", ((SimplePhpType) result).getName());
    }

    @Test
    public void defaultTypeFactoryCreatesUnionType() {
        TypeFactory factory = new DefaultTypeFactory();

        PhpType result = factory.createUnionType(List.of(new SimplePhpType("string"), new SimplePhpType("int")));

        assertTrue(result instanceof UnionType);
        UnionType unionType = (UnionType) result;
        assertEquals(2, unionType.getTypes().size());
        assertEquals(new SimplePhpType("string"), unionType.getTypes().get(0));
        assertEquals(new SimplePhpType("int"), unionType.getTypes().get(1));
    }
}
