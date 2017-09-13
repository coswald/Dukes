package com.dukes.klein.scanner;

import java.lang.RuntimeException;
import java.lang.String;

public class LexicalScanningException extends RuntimeException
{
  public LexicalScanningException()
  {
    this("No detailed message provided.");
  }
  
  public LexicalScanningException(String s)
  {
    super(s);
  }
  
  public LexicalScanningException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public LexicalScanningException(Throwable cause)
  {
    super(cause);
  }
}