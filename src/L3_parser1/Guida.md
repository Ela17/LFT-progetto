$$
    G_{expr} = ({⟨start⟩, ⟨expr ⟩, ⟨exprp⟩, ⟨term⟩, ⟨termp⟩, ⟨fact⟩}, {+, -, *, /, (, ), NUM, EOF}, P, ⟨start⟩)
$$
---

| | GUIDA |
|---|---|
|*\<start> $\rightarrow$ \<expr> EOF*|$\{$ '(', *NUM* $\}$|
|*\<expr> $\rightarrow$ \<term>\<exprp>*|$\{$ '(', *NUM* $\}$|
|*\<exprp> $\rightarrow$ $+$\<term>\<exprp>*|$\{$ '$+$' $\}$|
|*\<exprp> $\rightarrow$ -\<term>\<exprp>*|$\{ $ '$-$' $\}$|
|*\<exprp> $\rightarrow$ $\varepsilon$*|$\{$ ')', *EOF* $\}$|
|*\<term> $\rightarrow$ \<fact>\<termp>*|$\{$ '(', *NUM* $\}$|
|*\<termp> $\rightarrow$ $*$\<fact>\<termp>*|$\{$ '$*$' $\}$|
|*\<termp> $\rightarrow$ $/$\<fact>\<termp>*|$\{$ '/ ' $\}$|
|*\<termp> $\rightarrow$ $\varepsilon$*|$\{$ '$+$', '$-$', ')', *EOF* $\}$|
|*\<fact> $\rightarrow$ $($\<expr> $)$*|$\{$ '(' $\}$|
|*\<fact> $\rightarrow$ NUM*|$\{$ *NUM* $\}$|
