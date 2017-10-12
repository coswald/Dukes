package com.dukes.klein.parser;

import com.dukes.klein.parser.node.AbstractSyntaxNode;

public interface Parser {

  public boolean isValid();

  public AbstractSyntaxNode generateAST();

}
