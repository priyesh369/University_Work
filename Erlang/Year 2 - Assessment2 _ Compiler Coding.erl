-module(expr).
-compile([export_all]).

%
% A suite of functions for handling arithmetical expressions
%

% Expressions are represented like this
%
%     {num, N}
%     {var, A}
%     {condexpr, E1, E2, E3}
%     {add, E1, E2}
%     {mul, E1, E2}
%
% where N is a number, A is an atom,
% and E1, E2 are themselves expressions,

-type expr() :: {'num',integer()}
             |  {'var',atom()}
             |  {'condexpr',expr(),expr(),expr()}
             |  {'add',expr(),expr()}
             |  {'mul',expr(),expr()}.

% For example,
%   {add,{var,a},{mul,{num,2},{var,b}}
% represents the mathematical expression
%   (a+(2*b))

%
% Printing
%

% Turn an expression into a string, so that
%   {add,{var,a},{mul,{num,2},{var,b}}
% is turned into
%   "(a+(2*b))"

-spec print(expr()) -> string().

print({num,N}) ->
    integer_to_list(N);
print({var,A}) ->
    atom_to_list(A);
print({condexpr,E1,E2,E3}) ->
  "<"++ print(E1) ++ "?" ++ print(E2) ++":" ++ print(E3) ++">";
print({add,E1,E2}) ->
    "("++ print(E1) ++ "+" ++ print(E2) ++")";
print({mul,E1,E2}) ->
    "("++ print(E1) ++ "*" ++ print(E2) ++")".

%
% parsing
%

% recognise expressions
% deterministic, recursive descent, parser.

% the function returns two things
%   - an expression recognised at the beginning of the string
%     (in fact, the longers such expression)
%   - whatever of the string is left
%
% for example, parse("(-55*eeee)+1111)") is             
%   {{mul,{num,-55},{var,eeee}} , "+1111)"}


% recognise a fully-bracketed expression, with no spaces etc.

-spec parse(string()) -> {expr(), string()}.

parse([$(|Rest]) ->                            % starts with a '('
      {E1,Rest1}     = parse(Rest),            % then an expression
      [Op|Rest2]    = Rest1,                  % then an operator, '+' or '*'
      {E2,Rest3}     = parse(Rest2),          % then another expression
      [$)|RestFinal] = Rest3,                  % starts with a ')'
      {case Op of
	  $+ -> {add,E1,E2};
	  $* -> {mul,E1,E2}
        end,
       RestFinal};

%condexpr parse  <e1?e2:e3>
parse([$<|Rest]) ->
  {E1,Rest1}     = parse(Rest),
  [$?|Rest2]    = Rest1,
  {E2,Rest3}     = parse(Rest2),
  [$:|Rest4]    = Rest3,
  {E3,Rest5}     = parse(Rest4),
  [$>|RestFinal] = Rest5,
  {{condexpr,E1,E2,E3}, RestFinal};


% recognise an integer, a sequence of digits
% with an optional '-' sign at the start

parse([Ch|Rest]) when ($0 =< Ch andalso Ch =< $9) orelse Ch==$- ->
    {Succeeds,Remainder} = get_while(fun is_digit/1,Rest),
    {{num, list_to_integer([Ch|Succeeds])}, Remainder};

% recognise a variable: an atom built of small letters only.

parse([Ch|Rest])  when $a =< Ch andalso Ch =< $z ->
    {Succeeds,Remainder} = get_while(fun is_alpha/1,Rest),
    {{var, list_to_atom([Ch|Succeeds])}, Remainder}.

% auxiliary functions

% recognise a digit

-spec is_digit(integer()) -> boolean().

is_digit(Ch) ->
    $0 =< Ch andalso Ch =< $9.

% recognise a small letter

-spec is_alpha(integer()) -> boolean().

is_alpha(Ch) ->
    $a =< Ch andalso Ch =< $z.

% the longest initial segment of a list in which all
% elements have property P. Used in parsing integers
% and variables

-spec get_while(fun((T) -> boolean()),[T]) -> {[T],[T]}.    
%-spec get_while(fun((T) -> boolean()),[T]) -> [T].    
			 
get_while(P,[Ch|Rest]) ->
    case P(Ch) of
	true ->
	    {Succeeds,Remainder} = get_while(P,Rest),
	    {[Ch|Succeeds],Remainder};
	false ->
	    {[],[Ch|Rest]}
    end;
get_while(_P,[]) ->
    {[],[]}.

%
% Evaluate an expression
%

% First version commented out.

% eval({num,N}) ->
%     N;
% eval({condexpr,E1,E2,E3}) ->
%     <E1?E2:E3>;
% eval({add,E1,E2}) ->
%     eval(E1) + eval(E2);
% eval({mul,E1,E2}) ->
%     eval(E1) * eval(E2).

-type env() :: [{atom(),integer()}].

-spec eval(env(),expr()) -> integer().

eval(_Env,{num,N}) ->
    N;
eval(Env,{var,A}) ->
    lookup(A,Env);
eval(Env,{condexpr,E1,E2,E3}) ->
  case (eval(Env,E1) == 0) of
    true -> eval(Env,E2);
    false -> eval(Env,E3)
  end;
eval(Env,{add,E1,E2}) ->
    eval(Env,E1) + eval(Env,E2);
eval(Env,{mul,E1,E2}) ->
    eval(Env,E1) * eval(Env,E2).


%
% Compiler and virtual machine
%

% Instructions
%    {push, N} - push integer N onto the stack
%    {fetch, A} - lookup value of variable a and push the result onto the stack
%    {condexpr3} - pop the top three element of the stack, condition check, and push the result
%    {add2} - pop the top two elements of the stack, add, and push the result
%    {mul2} - pop the top two elements of the stack, multiply, and push the result

-type instr() :: {'push',integer()}
              |  {'fetch',atom()}
              |  {'condexpr3'}
              |  {'add2'}
              |  {'mul2'}.

-type program() :: [instr()].

% compiler

-spec compile(expr()) -> program().

compile({num,N}) ->
    [{push, N}];
compile({var,A}) ->
    [{fetch, A}];
compile({condexpr,E1,E2,E3}) ->
  compile(E1) ++ compile(E2) ++ compile(E3) ++[{condexpr3}];
compile({add,E1,E2}) ->
    compile(E1) ++ compile(E2) ++[{add2}];
compile({mul,E1,E2}) ->
    compile(E1) ++ compile(E2) ++[{mul2}].

% run a code sequence in given environment and empty stack

-spec run(program(),env()) -> integer().
   
run(Code,Env) ->
    run(Code,Env,[]).

% execute an instruction, and when the code is exhausted,
% return the top of the stack as result.
% classic tail recursion

-type stack() :: [integer()].

-spec run(program(),env(),stack()) -> integer().

run([{push, N} | Continue], Env, Stack) ->
    run(Continue, Env, [N | Stack]);
run([{fetch, A} | Continue], Env, Stack) ->
    run(Continue, Env, [lookup(A,Env) | Stack]);
run([{condexpr3} | Continue], Env, [N1,N2,N3|Stack]) ->
  run(Continue, Env, [(case (N3 == 0) of
                         true -> N2;
                         false -> N1
                       end) | Stack]);
run([{add2} | Continue], Env, [N1,N2|Stack]) ->
    run(Continue, Env, [(N1+N2) | Stack]);
run([{mul2} | Continue], Env ,[N1,N2|Stack]) ->
    run(Continue, Env, [(N1*N2) | Stack]);
run([],_Env,[N]) ->
    N.

% compile and run ...
% should be identical to eval(Env,Expr)

-spec execute(env(),expr()) -> integer().
     
execute(Env,Expr) ->
    run(compile(Expr),Env).

%
% Simplify an expression
%

% first version commented out

% simplify({add,E1,{num,0}}) ->
%     E1;
% simplify({add,{num,0},E2}) ->
%     E2;
% simplify({mul,E1,{num,1}}) ->
%     E1;
% simplify({mul,{num,1},E2}) ->
%     E2;
% simplify({mul,_,{num,0}}) ->
%     {num,0};
% simplify({mul,{num,0},_}) ->
%     {num,0}.


% second version commented out

% simplify({add,E1,{num,0}}) ->
%     simplify(E1);
% simplify({add,{num,0},E2}) ->
%     simplify(E2);
% simplify({mul,E1,{num,1}}) ->
%     simplify(E1);
% simplify({mul,{num,1},E2}) ->
%     simplify(E2);
% simplify({mul,_,{num,0}}) ->
%     {num,0};
% simplify({mul,{num,0},_}) ->
%     {num,0};
% simplify(E) ->
%     E.

-spec simplify(expr()) -> expr().

simplify({add,E1,{num,0}}) ->
    simplify(E1);
simplify({add,{num,0},E2}) ->
    simplify(E2);
simplify({mul,E1,{num,1}}) ->
    simplify(E1);
simplify({mul,{num,1},E2}) ->
    simplify(E2);
simplify({mul,_,{num,0}}) ->
    {num,0};
simplify({mul,{num,0},_}) ->
    {num,0};
simplify({add,E1,E2}) ->
  SimE1 = simplify(E1),
  SimE2 = simplify(E2),
  case (SimE1 /= E1) or (SimE2 /= E2) of
    true -> simplify({add,SimE1,SimE2});
    false -> {add,SimE1,SimE2}
  end;
simplify({mul,E1,E2}) ->
  SimE1 = simplify(E1),
  SimE2 = simplify(E2),
  case (SimE1 /= E1) or (SimE2 /= E2) of
    true -> simplify({mul,SimE1,SimE2});
    false -> {mul,SimE1,SimE2}
  end;
simplify({condexpr,_,E,E}) ->
  simplify(E);
simplify({condexpr,E1,E2,E3}) ->
  SimE1 = simplify(E1),
  SimE2 = simplify(E2),
  SimE3 = simplify(E3),
  case (SimE1 /= E1) or (SimE2 /= E2) or (SimE3 /= E3) of
    true -> simplify({condexpr,SimE1,SimE2,SimE3});
    false -> {condexpr,SimE1,SimE2,SimE3}
  end;
simplify(E) ->
    E.

% Auxiliary function: lookup a
% key in a list of key-value pairs.
% Fails if the key not present.

-spec lookup(atom(),env()) -> integer().

lookup(A,[{A,V}|_]) ->
    V;
lookup(A,[_|Rest]) ->
    lookup(A,Rest).

% Test data.

-spec env1() -> env().    
env1() ->
    [{a,23},{b,-12}].

-spec expr1() -> expr().    
expr1() ->
    {mul,{var,a},{mul,{num,1},{mul,{num,0},{var,b}}}}.


-spec test1() -> integer().    
test1() ->
    eval(env1(),expr1()).

-spec expr2() -> expr().    
expr2() ->
    {add,{mul,{num,1},{var,b}},{mul,{add,{mul,{num,2},{var,b}},{mul,{num,1},{var,b}}},{num,0}}}.
