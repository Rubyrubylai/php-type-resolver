package org.example.resolver;

import java.util.ArrayList;
import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.example.type.PhpType;
import org.example.type.TypeFactory;

public class TypeResolver {
    private final TypeFactory typeFactory;

    public TypeResolver(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }

    public PhpType inferTypeFromDoc(PhpVariable variable) {
        PhpType mixed = typeFactory.createType("mixed");
        PhpDocBlock docBlock = variable.getDocBlock();
        if (docBlock == null) {
            return mixed;
        }

        List<DocTag> varTags = docBlock.getTagsByName("var");
        if (varTags == null || varTags.isEmpty()) {
            return mixed;
        }

        List<ParsedTag> parsedTags = new ArrayList<>();
        for (DocTag varTag : varTags) {
            ParsedTag parsedTag = parseVarTag(varTag);
            if (parsedTag != null) {
                parsedTags.add(parsedTag);
            }
        }

        String variableName = variable.getName();
        List<ParsedTag> matchingNamedTags = new ArrayList<>();
        List<ParsedTag> unnamedTags = new ArrayList<>();
        for (ParsedTag parsedTag : parsedTags) {
            if (parsedTag.targetVariableName == null) {
                unnamedTags.add(parsedTag);
            } else if (parsedTag.targetVariableName.equals(variableName)) {
                matchingNamedTags.add(parsedTag);
            }
        }

        if (matchingNamedTags.size() == 1) {
            return toType(matchingNamedTags.get(0).typeExpression);
        }

        if (unnamedTags.size() == 1) {
            return toType(unnamedTags.get(0).typeExpression);
        }

        return mixed;
    }

    private PhpType toType(String typeExpression) {
        String[] rawTypes = typeExpression.split("\\|");
        List<PhpType> types = new ArrayList<>();
        for (String rawType : rawTypes) {
            String normalized = rawType.trim();
            if (!normalized.isEmpty()) {
                types.add(typeFactory.createType(normalized));
            }
        }

        if (types.isEmpty()) {
            return typeFactory.createType("mixed");
        }
        if (types.size() == 1) {
            return types.get(0);
        }
        return typeFactory.createUnionType(types);
    }

    private ParsedTag parseVarTag(DocTag docTag) {
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

        return new ParsedTag(typeExpression, targetVariableName);
    }

    private static final class ParsedTag {
        private final String typeExpression;
        private final String targetVariableName;

        private ParsedTag(String typeExpression, String targetVariableName) {
            this.typeExpression = typeExpression;
            this.targetVariableName = targetVariableName;
        }
    }
}
