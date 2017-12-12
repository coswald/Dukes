*
* PROGRAM
*
0: IN 1,0,0
1: ST 1,2(0)
2: LDA 6,1(7)
3: LDA 7,16(0)
4: OUT 5,0,0
5: HALT 0,0,0
*
* Function: SQUARE
*
6: LD 1,513(0)
7: LD 2,513(0)
8: LDC 3,1(0)
*
* Operator +
*
9: ADD 2,3,2
10: ST 2,3(0)
* END Operator +
*
* Operator *
*
11: LD 4,3(0)
12: MUL 1,4,1
13: ST 1,4(0)
* END Operator *
14: LD 5,4(0)
15: LDA 7,0(6)
* END Funtion SQUARE
*
* Function: MAIN
*
* CALL SQUARE
16: LD 4,2(0)
17: ST 4,513(0)
18: ST 6,6(0)
19: LDA 6,1(7)
20: LDA 7,6(0)
21: LD 6,6(0)
* END Call to SQUARE
22: LDA 7,0(6)
* END Funtion MAIN

