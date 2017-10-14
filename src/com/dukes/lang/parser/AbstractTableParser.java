package com.dukes.lang.parser;

import com.dukes.klein.parser.ParseTable;
import com.dukes.klein.parser.Parser;
import com.dukes.klein.parser.node.NullNode;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.scanner.AbstractScanner;
import com.dukes.lang.scanner.AbstractToken;

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
    if(!hasParsed) {
      isValidProgram = this.generateAST() != null;
    }
    return isValidProgram;
  }

  @Override
  public abstract AbstractSyntaxNode generateAST();
}
