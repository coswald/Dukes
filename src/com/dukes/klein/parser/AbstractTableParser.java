package com.dukes.klein.parser;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.NullNode;
import com.dukes.klein.scanner.AbstractScanner;
import com.dukes.klein.scanner.AbstractToken;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTableParser<E extends AbstractScanner<F>,
    F extends AbstractToken>
    implements Parser {
  protected ParseTable PARSETABLE;

  protected E scanner;
  protected boolean hasParsed = false;
  protected AbstractSyntaxNode ast = new NullNode();
  private boolean isValidProgram;

  @Override
  public boolean isValid() {
    if (!hasParsed) {
      isValidProgram = this.generateAST() != null;
    }
    return isValidProgram;
  }

  @Override
  public abstract AbstractSyntaxNode generateAST();
}
