package org.example;

import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.example.model.SimpleDocTag;
import org.example.model.SimplePhpDocBlock;
import org.example.model.SimplePhpVariable;
import org.example.resolver.TypeResolver;
import org.example.type.DefaultTypeFactory;
import org.example.type.PhpType;
import org.example.type.SimplePhpType;
import org.example.type.UnionType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AppTest {
    private final TypeResolver resolver = new TypeResolver(new DefaultTypeFactory());

    @Test
    public void inferStandardTypeFromUnnamedVarTag() {
        PhpVariable variable = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "User")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "User");
    }

    @Test
    public void inferUnionTypeFromVarTag() {
        PhpVariable variable = new SimplePhpVariable("$id", docBlockWithTags(new SimpleDocTag("var", "string|int")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertTrue(result instanceof UnionType);
        UnionType unionType = (UnionType) result;
        assertEquals(2, unionType.getTypes().size());
        assertSimpleType(unionType.getTypes().get(0), "string");
        assertSimpleType(unionType.getTypes().get(1), "int");
    }

    @Test
    public void inferNamedTypeWhenTagMatchesVariableName() {
        PhpVariable variable = new SimplePhpVariable("$log", docBlockWithTags(new SimpleDocTag("var", "Logger $log")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "Logger");
    }

    @Test
    public void ignoreNamedTagWhenVariableNameDoesNotMatch() {
        PhpVariable variable = new SimplePhpVariable("$guest", docBlockWithTags(new SimpleDocTag("var", "Admin $adm")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void chooseCorrectTypeFromMultipleNamedVarTags() {
        PhpVariable variable = new SimplePhpVariable(
                "$name",
                docBlockWithTags(new SimpleDocTag("var", "int $id"), new SimpleDocTag("var", "string $name")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "string");
    }

    @Test
    public void fallbackToMixedWhenDocBlockIsMissing() {
        PhpVariable variable = new SimplePhpVariable("$anything", null);

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    private PhpDocBlock docBlockWithTags(DocTag... tags) {
        return new SimplePhpDocBlock(List.of(tags));
    }

    private void assertSimpleType(PhpType type, String expectedName) {
        assertTrue(type instanceof SimplePhpType);
        SimplePhpType simplePhpType = (SimplePhpType) type;
        assertEquals(expectedName, simplePhpType.getName());
    }
}
