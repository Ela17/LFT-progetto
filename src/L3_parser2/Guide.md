||||GUIDA|
|---|---|---|---|
|⟨prog⟩|::=|⟨statlist⟩ EOF|assign print read for if {|
|⟨statlist⟩|::=|⟨stat⟩ ⟨statlistp⟩|assign print read for if {|
|⟨statlistp⟩|::=|; ⟨stat⟩ ⟨statlistp⟩ |;|
||\|| ε|EOF }|
|⟨stat⟩|::=|assign ⟨assignlist⟩|assign|
||\|| print ( ⟨exprlist⟩ )|print|
||\|| read ( ⟨idlist⟩ )|read|
||\|| for ( ⟨statp⟩ ⟨bexpr⟩ ) do ⟨stat⟩|for|
||\|| if ( ⟨bexpr⟩ ) ⟨stat⟩ ⟨stats⟩ end|if|
||\|| { ⟨statlist⟩ }|{|
|⟨statp⟩|::=|ID := ⟨expr⟩;|ID|
||\|| ε|RELOP|
|⟨stats⟩|::=|else ⟨stat⟩;|else|
||\|| ε|end|
|⟨assignlist⟩|::=|\[ ⟨expr⟩ to ⟨idlist⟩ ] ⟨assignlistp⟩|\[|
|⟨assignlistp⟩|::=|\[ ⟨expr⟩ to ⟨idlist⟩ ] ⟨assignlistp⟩ |\[|
||\|| ε|; else EOF }|
|⟨idlist⟩|::=|ID ⟨idlistp⟩|ID|
|⟨idlistp⟩|::=| , ID ⟨idlistp⟩ |,|
||\|| ε|) ]|
|⟨bexpr⟩ |::= |RELOP ⟨expr⟩ ⟨expr⟩|RELOP|
|⟨expr⟩ |::= |+ ( ⟨exprlist⟩ ) |+|
||\|| - ⟨expr⟩ ⟨expr⟩|-|
||\|| * ( ⟨exprlist⟩ ) |\*|
||\|| / ⟨expr⟩ ⟨expr⟩|/|
||\|| NUM|NUM|
||\|| ID|ID|
|⟨exprlist⟩ |::= |⟨expr⟩ ⟨exprlistp⟩|+ - * / NUM ID|
|⟨exprlistp⟩| ::=| , ⟨expr⟩ ⟨exprlistp⟩ |,|
||\|| ε|)|