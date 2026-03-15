package org.example.parser.impl;

import org.example.model.DocTag;
import org.example.parser.ParsedVarTag;
import org.example.parser.VarTagParser;
import org.jetbrains.annotations.Nullable;

public class DefaultVarTagParser implements VarTagParser {
    @Override
    public @Nullable ParsedVarTag parse(@Nullable DocTag docTag) {
        if (docTag == null) {
            return null;
        }

        String raw = docTag.getValue();
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\s+");
        if (parts.length == 0 || parts.length > 2) {
            return null;
        }

        String typeExpression = parts[0].trim();
        if (!isValidTypeExpression(typeExpression)) {
            return null;
        }

        String targetVariableName = null;
        if (parts.length == 2) {
            String variableToken = parts[1].trim();
            if (!isValidVariableToken(variableToken)) {
                return null;
            }
            targetVariableName = variableToken;
        }

        return new ParsedVarTag(typeExpression, targetVariableName);
    }

    private boolean isValidTypeExpression(@Nullable String typeExpression) {
        if (typeExpression == null || typeExpression.isEmpty()) {
            return false;
        }
        if (typeExpression.startsWith("$")) {
            return false;
        }

        String[] members = typeExpression.split("\\|", -1);
        if (members.length == 0) {
            return false;
        }

        for (String member : members) {
            if (member == null || member.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidVariableToken(@Nullable String variableToken) {
        return variableToken != null && variableToken.matches("^\\$[A-Za-z_][A-Za-z0-9_]*$");
    }
}
