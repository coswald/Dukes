package com.dukes.klein.scanner;

import java.lang.Object;

public abstract class Inputter extends Object
{
  private char currentChar;
  protected int position;
  
  public char currentChar()
  {
    return this.currentChar;
  }
  
  public void next()
  {
    this.currentChar = this.lookAhead();
    position++;
  }
  
  public abstract boolean hasNext();
  
  protected abstract char lookAhead();
}