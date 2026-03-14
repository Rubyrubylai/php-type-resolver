package org.example.parser;

import org.example.model.DocTag;

public interface VarTagParser {
    ParsedVarTag parse(DocTag docTag);
}
