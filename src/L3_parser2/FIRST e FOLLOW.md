||FIRST|
|---|---|
|⟨prog⟩|assign print read for if {|
|⟨statlist⟩|assign print read for if {|
|⟨statlistp⟩|;|
|⟨stat⟩|assign print read for if {|
|⟨statp⟩|ID|
|⟨stats⟩|else|
|⟨assignlist⟩|[|
|⟨assignlistp⟩|[|
|⟨idlist⟩|ID|
|⟨idlistp⟩|, |
|⟨bexpr⟩ |RELOP|
|⟨expr⟩ |+ - \* / NUM ID |
|⟨exprlist⟩ |+ - \* / NUM ID|
|⟨exprlistp⟩| ,|


||FOLLOW|
|---|---|
|⟨prog⟩|\$|
|⟨statlist⟩|EOF }|
|⟨statlistp⟩|EOF }|
|⟨stat⟩|; else EOF }|
|⟨statp⟩|RELOP|
|⟨stats⟩|end|
|⟨assignlist⟩||; else EOF }|
|⟨assignlistp⟩|; else EOF }|
|⟨idlist⟩|) ]|
|⟨idlistp⟩|) ] |
|⟨bexpr⟩ |)|
|⟨expr⟩ |; + - \* / NUM ID to ) ,|
|⟨exprlist⟩ |)|
|⟨exprlistp⟩|)|