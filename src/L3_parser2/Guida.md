| | GUIDA |
|---|---|
|*\<prog> $\rightarrow$ \<statlist> EOF*|$\{$ *assign*, *print*, *read*, *for*, *if*, '{' $\}$|
|*\<statlist> $\rightarrow$ \<stat>\<statlistp>*|$\{$ *assign*, *print*, *read*, *for*, *if*, '{' $\}$|
|*\<statlistp> $\rightarrow$ $;$\<stat>\<statlistp>*|$\{$ ' $;$ ' $\}$|
|*\<statlistp> $\rightarrow$ $\varepsilon$*|$\{$ *EOF*, '}' $\}$|
|*\<stat> $\rightarrow$ assign \<assignlist>*|$\{$ *assign* $\}$|
|*\<stat> $\rightarrow$ *print* $($\<exprlist> $)$*|$\{$ *print* $\}$|
|*\<stat> $\rightarrow$ *read* $($\<idlist> $)$* |$\{$ *read* $\}$|
|*\<stat> $\rightarrow$ *for* $($\<statp>$)$ do \<stat>*|$\{$ *for* $\}$|
|*\<stat> $\rightarrow$ *if* $($\<bexpr>$)$ \<stat>\<stats>*|$\{$ *if* $\}$|
|*\<stat> $\rightarrow$ $\{$\<statlist> $\}$*|$\{$ '{' $\}$|
|*\<statp> $\rightarrow$ ID $:=$ \<expr> $;$ \<bexpr>*|$\{$ *ID* $\}$|
|*\<statp> $\rightarrow$ \<bexpr>*|$\{$ *RELOP* $\}$|
|*\<stats> $\rightarrow$ else \<stat> end*|$\{$ *else* $\}$|
|*\<stats> $\rightarrow$ end*|$\{$ *end* $\}$|
|*\<assignlist> $\rightarrow$ $[$\<expr> to \<idlist>$]$ \<assignlistp>*|$\{$ '[' $\}$|
|*\<assignlistp> $\rightarrow$ $[$\<expr> to \<idlist>$]$ \<assignlistp>*|$\{$ '[' $\}$|
|*\<assignlistp> $\rightarrow$ $\varepsilon$*|$\{$ *EOF*, '}', '$;$', *else*, *end* $\}$|
|*\<idlist> $\rightarrow$ ID \<idlistp>*|$\{$ *ID* $\}$|
|*\<idlistp> $\rightarrow$ $,$ ID \<idlistp>*|$\{$ '$,$' $\}$|
|*\<idlistp> $\rightarrow$ $\varepsilon$*|$\{$ ')', ']' $\}$|
|*\<bexpr> $\rightarrow$ RELOP \<expr>\<expr>*|$\{$ *RELOP* $\}$|
|*\<expr> $\rightarrow$ $+($\<exprlist> $)$*|$\{$ '$+$' $\}$|
|*\<expr> $\rightarrow$ $*($\<exprlist> $)$*|$\{$ '$*$' $\}$|
|*\<expr> $\rightarrow$ $-$ \<expr>\<expr>*|$\{$ '$-$' $\}$|
|*\<expr> $\rightarrow$ $/$ \<expr>\<expr>*|$\{$ '$/$' $\}$|
|*\<expr> $\rightarrow$ NUM*|$\{$ *NUM* $\}$|
|*\<expr> $\rightarrow$ ID*|$\{$ *ID* $\}$|
|*\<exprlist> $\rightarrow$ \<expr>\<exprlistp>*|$\{$ '$+$', '$-$', '$*$', '$/$', *NUM*, *ID* $\}$|
|*\<exprlistp> $\rightarrow$ $,$ \<expr>\<exprlistp>*|$\{$ '$,$' $\}$|
|*\<exprlistp> $\rightarrow$ $\varepsilon$*|$\{$ ')' $\}$|