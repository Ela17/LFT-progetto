GUIDA(<em>\<prog> -> \<statlist></em>) = { assign, print, read, for, if, '{' }

GUIDA(<em>\<statlist> -> \<stat>\<statlistp></em>) = { assign, print, read, for, if, '{' }

GUIDA(<em>\<statlistp> -> ;\<stat>\<statlistp></em>) = { ';' }

GUIDA(<em>\<statlistp> -> $\varepsilon$</em>) = { EOF, '}' }

GUIDA(<em>\<stat> -> assign\<expr>to\<idlist></em>) = { assign }

GUIDA(<em>\<stat> -> print[\<exprlist>]</em>) = { print }

GUIDA(<em>\<stat> -> read[\<idlist>]</em>) = { read }

GUIDA(<em>\<stat> -> for(\<statp>) do \<stat></em>) = { for }

GUIDA(<em>\<stat> -> if(\<bexpr>)\<stat>\<stats></em>) = { if }

GUIDA(<em>\<stat> -> {\<statlist>}</em>) = { '{' }

GUIDA(<em>\<statp> -> ID:=\<expr>;\<bexpr></em>) = { ID }

GUIDA(<em>\<statp> -> <bexpr></em>) = { RELOP }

GUIDA(<em>\<stats> -> else\<stat>end</em>) = { else }

GUIDA(<em>\<stats> -> end</em>) = { end }

GUIDA(<em>\<assignlist> -> [\<expr>to\<idlist>]\<assignlistp></em>) = { '[' }

GUIDA(<em>\<assignlist> -> [\<expr>to\<idlist>]\<assignlistp></em>) = { '[' }

GUIDA(<em>\<assignlist> -> $\varepsilon$</em>) = { EOF, }, ';', else, end }

GUIDA(<em>\<idlist> -> ID\<idlistp></em>) = { ID }

GUIDA(<em>\<idlistp> -> , ID\<idlistp></em>) = { ',' }

GUIDA(<em>\<idlistp> -> $\varepsilon$</em>) = { ')', '}' }

GUIDA(<em>\<bexpr> -> RELOP\<expr>\<expr></em>) = { RELOP }

GUIDA(<em>\<expr> -> +(\<exprlist>)</em>) = { '+' }

GUIDA(<em>\<expr> -> -\<expr>\<expr></em>) = { '-' }

GUIDA(<em>\<expr> -> *(\<exprlist>)</em>) = { '\*' }

GUIDA(<em>\<expr> -> /\<expr>\<expr></em>) = { '/' }

GUIDA(<em>\<expr> -> NUM</em>) = { 'NUM' }

GUIDA(<em>\<expr> -> ID</em>) = { 'ID' }

GUIDA(<em>\<exprlist> -> \<expr>\<exprlistp></em>) = { '+', '-', '*', '/', NUM, ID }

GUIDA(<em>\<exprlistp> -> , \<expr>\<exprlistp></em>) = { ',' }

GUIDA(<em>\<exprlistp> ->  $\varepsilon$</em>) = { ')' }