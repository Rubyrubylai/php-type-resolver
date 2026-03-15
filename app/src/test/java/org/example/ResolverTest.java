package org.example;

import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.example.model.impl.SimpleDocTag;
import org.example.model.impl.SimplePhpDocBlock;
import org.example.model.impl.SimplePhpVariable;
import org.example.parser.impl.DefaultVarTagParser;
import org.example.resolver.TypeResolver;
import org.example.type.PhpType;
import org.example.type.impl.DefaultTypeFactory;
import org.example.type.impl.SimplePhpType;
import org.example.type.impl.UnionType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ResolverTest {
    private final TypeResolver resolver = new TypeResolver(new DefaultTypeFactory(), new DefaultVarTagParser());

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
        assertEquals(2, unionType.types().size());
        assertSimpleType(unionType.types().get(0), "string");
        assertSimpleType(unionType.types().get(1), "int");
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

    @Test
    public void fallbackToMixedWhenMultipleNamedTagsMatchVariable() {
        PhpVariable variable = new SimplePhpVariable(
                "$id",
                docBlockWithTags(new SimpleDocTag("var", "int $id"), new SimpleDocTag("var", "string $id")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void fallbackToMixedWhenMultipleUnnamedTagsExistAndNoNamedMatch() {
        PhpVariable variable = new SimplePhpVariable(
                "$id",
                docBlockWithTags(new SimpleDocTag("var", "int"), new SimpleDocTag("var", "string")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedEmptyTagValueReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedMissingTypeWithVariableOnlyReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "$user")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedExtraTokenAfterVariableNameReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable(
                "$user",
                docBlockWithTags(new SimpleDocTag("var", "User $user $name")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedWrongTokenOrderReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "$user User")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedUnionWithEmptyMemberReturnsMixed() {
        PhpVariable variableA = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "|User")));
        PhpVariable variableB = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "User|")));
        PhpVariable variableC = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "User||null")));

        assertSimpleType(resolver.inferTypeFromDoc(variableA), "mixed");
        assertSimpleType(resolver.inferTypeFromDoc(variableB), "mixed");
        assertSimpleType(resolver.inferTypeFromDoc(variableC), "mixed");
    }

    @Test
    public void malformedVariableTokenWithoutDollarReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable("$user", docBlockWithTags(new SimpleDocTag("var", "User user")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedTrailingDescriptionReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable(
                "$user",
                docBlockWithTags(new SimpleDocTag("var", "User $user current logged-in user")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedMultipleVariableNamesInOneTagReturnsMixed() {
        PhpVariable variable = new SimplePhpVariable("$user",
                docBlockWithTags(new SimpleDocTag("var", "User $user, $name")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    @Test
    public void malformedTagIsIgnoredWhenAnotherValidTagExists() {
        PhpVariable variable = new SimplePhpVariable(
                "$user",
                docBlockWithTags(new SimpleDocTag("var", "$user"), new SimpleDocTag("var", "User $user")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "User");
    }

    @Test
    public void malformedUnionWithSpacesIsIgnored() {
        PhpVariable variable = new SimplePhpVariable(
                "$user",
                docBlockWithTags(new SimpleDocTag("var", "User | null $user")));

        PhpType result = resolver.inferTypeFromDoc(variable);

        assertSimpleType(result, "mixed");
    }

    private PhpDocBlock docBlockWithTags(DocTag... tags) {
        return new SimplePhpDocBlock(List.of(tags));
    }

    private void assertSimpleType(PhpType type, String expectedName) {
        assertTrue(type instanceof SimplePhpType);
        SimplePhpType simplePhpType = (SimplePhpType) type;
        assertEquals(expectedName, simplePhpType.name());
    }
}
