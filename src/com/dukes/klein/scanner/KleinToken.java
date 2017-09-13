package com.dukes.klein.scanner;

import com.dukes.klein.scanner.AbstractToken;
import com.dukes.klein.scanner.IllegalTokenTypeException;
import com.dukes.klein.scanner.KleinTokenType;

public class KleinToken extends AbstractToken<KleinTokenType>
{
  public KleinToken(KleinTokenType tokenType, Object tokenValue)
                      throws IllegalTokenTypeException
  {
    boolean defined = (tokenValue != null);
    boolean throwException = false;
    
    switch(tokenType)
    {
      case LEFTPARENTHESIS:
      case RIGHTPARENTHESIS:
      case EOF:
        throwException = defined;
        break;
      default:
        throwException = !defined;
    }
    
    if(throwException)
    {
      if(defined)
      {
        throw new IllegalTokenTypeException("The given token type, "+tokenType +
                                            " does not need the value " +
                                            tokenValue + "!");
      }
      else
      {
        throw new IllegalTokenTypeException("The given token type, "+tokenType +
                                            " is not given a necessary value!");
      }
    }
    
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
  }
  
  public KleinToken(KleinTokenType tokenType)
  {
    this(tokenType, null);
  }
}