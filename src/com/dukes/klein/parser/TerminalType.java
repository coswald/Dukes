package com.dukes.klein.parser;

/**
 * @author Daniel J. Holland
 * @version 1.0
 *          Created on 9/28/2017.
 */
public enum TerminalType {
    FUNCTION(0),
    PRINT(1),
    LEFTPAREN(2),
    RIGHTPAREN(3),
    COMMA(4),
    LESSTHAN(5),
    EQUAL(6),
    PLUS(7),
    MINUS(8),
    PRODUCT(9),
    DIVIDE(10),
    AND(11),
    OR(12),
    NOT(13),
    IF(14),
    STRING(15),
    NUMBER(16),
    BOOLEAN(17),
    EMPTY(18),
    $(19);

    private final int id;

    TerminalType(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
