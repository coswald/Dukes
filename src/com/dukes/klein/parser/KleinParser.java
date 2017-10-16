package com.dukes.klein.parser;

import com.dukes.klein.parser.node.SemanticActionType;
import com.dukes.klein.parser.node.TerminalNode;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.dukes.lang.parser.AbstractTableParser;
import com.dukes.lang.parser.ParsingException;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.NullNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class KleinParser
    extends AbstractTableParser<KleinScanner, KleinToken, KleinParseTable> {

  private Stack<Enum> stack;
  private Stack<AbstractSyntaxNode> semanticStack;

  public KleinParser(KleinScanner ks) {
    super(new KleinParseTable(), ks);
    this.stack = new Stack<Enum>();
    this.semanticStack = new Stack<AbstractSyntaxNode>();
  }

  private AbstractSyntaxNode parseProgram() {
    this.hasParsed = true;
    Enum stackTop = null;
    KleinToken scannerToken;
    KleinRule kRule;
    this.stack.push(TerminalType.EOF);
    this.stack.push(NonTerminalType.PROGRAM);

    while(!this.stack.empty()) {
      stackTop = this.stack.peek();
      if(stackTop instanceof TerminalType) {
        scannerToken = this.scanner.next();
        if(scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          continue;
        }
        if(stackTop == scannerToken.getTerminal()) {
          this.stack.pop();
          if(scannerToken.getTokenType() == KleinTokenType.BOOLEAN ||
              scannerToken.getTokenType() == KleinTokenType.INTEGER ||
              scannerToken.getTokenType() == KleinTokenType.IDENTIFIER ||
              scannerToken.getTokenType() == KleinTokenType.TYPE ||
              scannerToken.getTokenType() == KleinTokenType.SYMBOL) {
            this.semanticStack.push(
                new TerminalNode(scannerToken.getTokenValue()));
          }
        }
        else {
          throw new ParsingException(String.format(
              "Token mismatch! Expected %s but got %s: %s",
              stackTop.name(), scannerToken.getTokenType().name(),
              scannerToken.getTerminal().name()));
        }
      }
      else if(stackTop instanceof NonTerminalType) {
        scannerToken = this.scanner.peek();
        if(scannerToken.getTokenType().equals(KleinTokenType.STARTCOMMENT) ||
            scannerToken.getTokenType().equals(KleinTokenType.ENDCOMMENT)) {
          this.scanner.next();
          continue;
        }
        kRule = (KleinRule)this.PARSETABLE.getRule((NonTerminalType) stackTop,
            scannerToken.getTerminal());
        if(kRule.exists()) {
          this.stack.pop();
          kRule.pushRule(this.stack);
        }
        else {
          throw new ParsingException(String.format(
              "Invalid item '%s' found on the stack when handling %s: %s",
              stackTop.name(), scannerToken.getTokenType().name(),
              scannerToken.getTerminal().name()));
        }
      }
      else if(stackTop instanceof SemanticActionType) {
        AbstractSyntaxNode ast =
            ((SemanticActionType) stackTop).run(this.semanticStack);
        this.semanticStack.push(ast);
        this.stack.pop();
      }
    }
    if(stackTop != null && !stackTop.equals(TerminalType.EOF)) {
      throw new ParsingException(String.format(
          "Unexpected token '%s' at end of file", stackTop.name()));
    }
    if(this.semanticStack.size() != 1) {
      throw new ParsingException("Extra Tokens are in semantic stack: " +
          this.semanticStack.toString() + "!");
    }
    this.ast = this.semanticStack.pop();

    return ast;
  }

  @Override
  public AbstractSyntaxNode generateAST() {
    if(this.ast instanceof NullNode && !this.hasParsed) {
      return this.parseProgram();
    }
    else {
      return this.ast;
    }
  }
}
