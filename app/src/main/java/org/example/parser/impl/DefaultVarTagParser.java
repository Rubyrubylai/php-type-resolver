package org.example.parser.impl;

import org.example.model.DocTag;
import org.example.parser.ParsedVarTag;
import org.example.parser.VarTagParser;

public class DefaultVarTagParser implements VarTagParser {
    @Override
    public ParsedVarTag parse(DocTag docTag) {
        if (docTag == null) {
            return null;
        }

        String raw = docTag.getValue();
        if (raw == null) {
            return null;
        }

        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\s+");
        if (parts.length == 0) {
            return null;
        }

        String typeExpression = parts[0].trim();
        if (typeExpression.isEmpty()) {
            return null;
        }

        String targetVariableName = null;
        if (parts.length > 1 && parts[1].startsWith("$")) {
            targetVariableName = parts[1];
        }

        return new ParsedVarTag(typeExpression, targetVariableName);
    }
}
