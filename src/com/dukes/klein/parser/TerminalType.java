package com.dukes.klein.parser;

/**
 * @author Daniel J. Holland
 * @version 1.0
 * Created on 9/28/2017.
 */
public enum TerminalType {
    FUNCTION(0),
    MAIN(1),
    PRINT(2),
    IF(3),
    THEN(4),
    ELSE(5),
    LEFTPAREN(6),
    RIGHTPAREN(7),
    COMMA(8),
    COLON(9),
    LESSTHAN(10),
    EQUAL(11),
    PLUS(12),
    MINUS(13),
    PRODUCT(14),
    DIVIDE(15),
    AND(16),
    OR(17),
    NOT(18),
    INTEGERSTR(19),
    BOOLEANSTR(20),
    STRING(21),
    NUMBER(22),
    BOOLEAN(23),
    EOF(24),
    EMPTY(25);

    private final int id;

    TerminalType(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
