package com.dukes.lang.parser;

import com.dukes.lang.scanner.AbstractToken;

import java.util.List;

public abstract class AbstractRule<E extends AbstractToken> {
  protected List<E> rule;

  public AbstractRule(List<E> rule) {
    this.rule = rule;
  }

  public List<E> getRule() {
    return this.rule;
  }
}
