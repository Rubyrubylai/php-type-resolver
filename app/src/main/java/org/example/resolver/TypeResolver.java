package org.example.resolver;

import java.util.ArrayList;
import java.util.List;

import org.example.model.DocTag;
import org.example.model.PhpDocBlock;
import org.example.model.PhpVariable;
import org.example.parser.ParsedVarTag;
import org.example.parser.VarTagParser;
import org.example.type.PhpType;
import org.example.type.TypeFactory;
import org.jetbrains.annotations.NotNull;

public class TypeResolver {
    private final TypeFactory typeFactory;
    private final VarTagParser varTagParser;

    public TypeResolver(@NotNull TypeFactory typeFactory, @NotNull VarTagParser varTagParser) {
        this.typeFactory = typeFactory;
        this.varTagParser = varTagParser;
    }

    public @NotNull PhpType inferTypeFromDoc(@NotNull PhpVariable variable) {
        PhpType mixed = typeFactory.createType("mixed");
        PhpDocBlock docBlock = variable.docBlock();
        if (docBlock == null) {
            return mixed;
        }

        List<DocTag> varTags = docBlock.getTagsByName("var");
        if (varTags.isEmpty()) {
            return mixed;
        }

        List<ParsedVarTag> parsedTags = new ArrayList<>();
        for (DocTag varTag : varTags) {
            ParsedVarTag parsedTag = varTagParser.parse(varTag);
            if (parsedTag != null) {
                parsedTags.add(parsedTag);
            }
        }

        String variableName = variable.name();
        List<ParsedVarTag> matchingNamedTags = new ArrayList<>();
        List<ParsedVarTag> unnamedTags = new ArrayList<>();
        for (ParsedVarTag parsedTag : parsedTags) {
            if (parsedTag.targetVariableName() == null) {
                unnamedTags.add(parsedTag);
            } else if (parsedTag.targetVariableName().equals(variableName)) {
                matchingNamedTags.add(parsedTag);
            }
        }

        if (matchingNamedTags.size() == 1) {
            return toType(matchingNamedTags.get(0).typeExpression());
        }
        if (matchingNamedTags.size() > 1) {
            return mixed;
        }

        if (unnamedTags.size() == 1) {
            return toType(unnamedTags.get(0).typeExpression());
        }
        if (unnamedTags.size() > 1) {
            return mixed;
        }

        return mixed;
    }

    private @NotNull PhpType toType(@NotNull String typeExpression) {
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
}
