package com.dukes.klein.scanner;

import java.lang.Enum;
import java.lang.Object;
import java.lang.String;

public abstract class AbstractToken<E extends Enum<E>> extends Object
{
  protected E tokenType;
  protected Object tokenValue = null;
  
  protected AbstractToken() {}//why the hell this has to be here i'll never know
  
  protected AbstractToken(E tokenType, Object tokenValue)
  {
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
  }
  
  protected AbstractToken(E tokenType)
  {
    this(tokenType, null);
  }
  
  public boolean isTokenType(E tokenType)
  {
    return this.tokenType == tokenType;
  }
  
  public E getTokenType()
  {
    return this.tokenType;
  }
  
  public Object getTokenValue()
  {
    return this.tokenValue;
  }
  
  @Override
  public String toString()
  {
    return "Token Type: " + this.tokenType.toString() + (tokenValue != null ?
           "\tToken Value: " + this.tokenValue.toString() : "");
  }
}