package com.dukes.klein.scanner;

import java.lang.IllegalArgumentException;
import java.lang.String;

//Writing this class felt like plagiarism...

public class IllegalTokenTypeException extends IllegalArgumentException
{
  public IllegalTokenTypeException()
  {
    this("No detailed message provided.");
  }
  
  public IllegalTokenTypeException(String s)
  {
    super(s);
  }
  
  public IllegalTokenTypeException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public IllegalTokenTypeException(Throwable cause)
  {
    super(cause);
  }
}