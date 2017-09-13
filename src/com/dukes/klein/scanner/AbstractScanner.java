package com.dukes.klein.scanner;

import com.dukes.klein.scanner.Inputter;
import com.dukes.klein.scanner.LexicalScanningException;
import com.dukes.klein.scanner.AbstractToken;

import java.lang.Object;
import java.lang.String;

public abstract class AbstractScanner<E extends AbstractToken> extends Object
{
  private E nextToken;
  protected Inputter input;
  
  public AbstractScanner(Inputter input)
  {
    this.input = input;
    this.nextToken = null;
  }
  
  public E peek() throws LexicalScanningException
  {
    if(nextToken == null)
    {
      this.nextToken = this.nextToken();
    }
    return this.nextToken;
  }
  
  public E nextToken() throws LexicalScanningException
  {
    if(nextToken != null)
    {
      E currentToken = this.nextToken;
      this.nextToken = null;
      return currentToken;
    }
    else
    {
      return this.generateNextToken();
    }
  }
  
  public abstract E generateNextToken()
                                throws LexicalScanningException;
}