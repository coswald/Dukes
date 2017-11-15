*
*
  0:    LDA  6,1(7)   ; store return address
  1:    LDA  7,6(0)   ; branch to MAIN, at [r0] + 6
  2:    OUT  5,0,0    ; print return value
  3:   HALT  0,0,0 
*
* PRINT     expects argument in r5, and prints it out.
*
  4:    OUT  5,0,0    ; output r5
  5:    LDA  7,0(6)   ; return to address [r6]+0
*
* MAIN       expects no arguments, puts result in r5
*
  6:    LDC  5,1(0)   ; load constant 1 into r5
  7:    ST   6,2(0)   ; save return address in DMEM[ [r0] + 2 ]
  8:    LDA  6,1(7)   ; store return address
  9:    LDA  7,4(0)   ; branch to PRINT, at [r0] + 4
*      We are human. We know that r5 already contains the return value of main.
*                                 However, our compiler is not that smart. Yet.
 10:    LDC  5,1(0)   ; load return value of main into r5.
 11:    LD   7,2(0)   ; return to address in DMEM[ [r0] + 2 ]