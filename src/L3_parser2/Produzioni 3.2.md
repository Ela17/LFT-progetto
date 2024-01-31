||||
|---|---|---|
|⟨prog⟩|::=|⟨statlist⟩ EOF|
|⟨statlist⟩|::=|⟨stat⟩ ⟨statlistp⟩|
|⟨statlistp⟩|::=|; ⟨stat⟩ ⟨statlistp⟩ |
||\|| ε|
|⟨stat⟩|::=|assign ⟨assignlist⟩|
||\|| print ( ⟨exprlist⟩ )|
||\|| read ( ⟨idlist⟩ )|
||\|| for ( ⟨statp⟩ ⟨bexpr⟩ ) do ⟨stat⟩|
||\|| if ( ⟨bexpr⟩ ) ⟨stat⟩ ⟨stats⟩ end|
||\|| { ⟨statlist⟩ }|
|⟨statp⟩|::=|ID := ⟨expr⟩;|
||\|| ε|
|⟨stats⟩|::=|else ⟨stat⟩;|
||\|| ε|
|⟨assignlist⟩|::=|[ ⟨expr⟩ to ⟨idlist⟩ ] ⟨assignlistp⟩|
|⟨assignlistp⟩|::=|[ ⟨expr⟩ to ⟨idlist⟩ ] ⟨assignlistp⟩ |
||\|| ε|
|⟨idlist⟩|::=|ID ⟨idlistp⟩|
|⟨idlistp⟩|::=| , ID ⟨idlistp⟩ |
||\|| ε|
|⟨bexpr⟩ |::= |RELOP ⟨expr⟩ ⟨expr⟩|
|⟨expr⟩ |::= |+ ( ⟨exprlist⟩ ) |
||\|| - ⟨expr⟩ ⟨expr⟩|
||\|| * ( ⟨exprlist⟩ ) |
||\|| / ⟨expr⟩ ⟨expr⟩|
||\|| NUM
||\|| ID
|⟨exprlist⟩ |::= |⟨expr⟩ ⟨exprlistp⟩|
|⟨exprlistp⟩| ::=| , ⟨expr⟩ ⟨exprlistp⟩ |
||\|| ε|