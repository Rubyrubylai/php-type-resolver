package org.example;

import java.util.ArrayList;
import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.example.model.impl.SimpleDocTag;
import org.example.model.impl.SimplePhpDocBlock;
import org.example.model.impl.SimplePhpVariable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ModelImplTest {

    @Test
    public void simpleDocTagExposesNameAndValue() {
        SimpleDocTag tag = new SimpleDocTag("var", "User $user");

        assertEquals("var", tag.getName());
        assertEquals("User $user", tag.getValue());
    }

    @Test
    public void simplePhpDocBlockFiltersTagsByName() {
        PhpDocBlock docBlock = new SimplePhpDocBlock(List.of(
                new SimpleDocTag("var", "User $user"),
                new SimpleDocTag("param", "string $name"),
                new SimpleDocTag("var", "int $id")));

        List<DocTag> varTags = docBlock.getTagsByName("var");

        assertEquals(2, varTags.size());
        assertEquals("User $user", varTags.get(0).getValue());
        assertEquals("int $id", varTags.get(1).getValue());
    }

    @Test
    public void simplePhpDocBlockDoesNotChangeWhenSourceListMutates() {
        List<DocTag> source = new ArrayList<>();
        source.add(new SimpleDocTag("var", "User"));
        SimplePhpDocBlock docBlock = new SimplePhpDocBlock(source);
        source.add(new SimpleDocTag("var", "Admin"));

        List<DocTag> result = docBlock.getTagsByName("var");

        assertEquals(1, result.size());
        assertEquals("User", result.get(0).getValue());
    }

    @Test
    public void simplePhpVariableExposesNameAndDocBlock() {
        PhpDocBlock docBlock = new SimplePhpDocBlock(List.of(new SimpleDocTag("var", "User")));
        SimplePhpVariable variable = new SimplePhpVariable("$user", docBlock);

        assertEquals("$user", variable.getName());
        assertSame(docBlock, variable.getDocBlock());
    }
}
