package org.example;

import org.example.model.impl.SimpleDocTag;
import org.example.parser.ParsedVarTag;
import org.example.parser.impl.DefaultVarTagParser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class VarTagParserTest {
    private final DefaultVarTagParser parser = new DefaultVarTagParser();

    @Test
    public void parseTypeOnly() {
        ParsedVarTag parsed = parser.parse(new SimpleDocTag("var", "User"));

        assertNotNull(parsed);
        assertEquals("User", parsed.typeExpression());
        assertNull(parsed.targetVariableName());
    }

    @Test
    public void parseTypeWithVariableName() {
        ParsedVarTag parsed = parser.parse(new SimpleDocTag("var", "User $user"));

        assertNotNull(parsed);
        assertEquals("User", parsed.typeExpression());
        assertEquals("$user", parsed.targetVariableName());
    }

    @Test
    public void parseUnionTypeWithVariableName() {
        ParsedVarTag parsed = parser.parse(new SimpleDocTag("var", "User|null $user"));

        assertNotNull(parsed);
        assertEquals("User|null", parsed.typeExpression());
        assertEquals("$user", parsed.targetVariableName());
    }

    @Test
    public void rejectEmptyValue() {
        assertNull(parser.parse(new SimpleDocTag("var", "")));
    }

    @Test
    public void rejectMissingType() {
        assertNull(parser.parse(new SimpleDocTag("var", "$user")));
    }

    @Test
    public void rejectExtraTokens() {
        assertNull(parser.parse(new SimpleDocTag("var", "User $user $name")));
    }

    @Test
    public void rejectWrongOrder() {
        assertNull(parser.parse(new SimpleDocTag("var", "$user User")));
    }

    @Test
    public void rejectInvalidUnionMembers() {
        assertNull(parser.parse(new SimpleDocTag("var", "|User")));
        assertNull(parser.parse(new SimpleDocTag("var", "User|")));
        assertNull(parser.parse(new SimpleDocTag("var", "User||null")));
    }

    @Test
    public void rejectVariableWithoutDollar() {
        assertNull(parser.parse(new SimpleDocTag("var", "User user")));
    }

    @Test
    public void rejectVariableWithInvalidCharacters() {
        assertNull(parser.parse(new SimpleDocTag("var", "User $user,")));
        assertNull(parser.parse(new SimpleDocTag("var", "User $1user")));
    }

    @Test
    public void rejectUnionWithSpacesBySimplifiedParser() {
        assertNull(parser.parse(new SimpleDocTag("var", "User | null $user")));
    }
}
