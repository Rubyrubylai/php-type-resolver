package org.example.parser;

import org.example.model.DocTag;
import org.jetbrains.annotations.Nullable;

public interface VarTagParser {
    @Nullable
    ParsedVarTag parse(@Nullable DocTag docTag);
}
