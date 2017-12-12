*
* PROGRAM
*
0: LDA 6,1(7)
1: LDA 7,7(0)
2: OUT 5,0,0
3: HALT 0,0,0
*
* PRINT
*
4: LD 5,512(0)
5: OUT 5,0,0
6: LDA 7,0(6)
*
* Function: MAIN
*
* CALL PRINT 
7: LDC 1,1(0)
8: ST 1,512(0)
9: ST 6,2(0)
10: LDA 6,1(7)
11: LDA 7,4(0)
12: LD 6,2(0)
* END Call to PRINT 
13: LDC 2,1(0)
14: LD 5,2(0)
15: LDA 7,0(6)
* END Funtion MAIN

