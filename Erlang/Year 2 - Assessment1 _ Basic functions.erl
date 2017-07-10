%%%-------------------------------------------------------------------
%%% @author Priyesh Patel
%%%
%%% Created : 08. Feb 2017 04:29 AM
%%%-------------------------------------------------------------------
-module(prp6A1).
-author("Priyesh Patel").

-export([fib/1, fib2/1, perimeter/1, occurs/2, odd/1, parity/1, game_score/1, overlap/2]).

%================================================== TASK 1 ==================================================%
  % define function fib
  % gives the Nth fibonacci number
  fib(0) -> 0;
  fib(1) -> 1;
  fib(N) ->
    fib(N-2) + fib(N-1).

  % Hand Evaluation for fib(4)
  % fib(2) + fib(3)
  % fib(0) + fib(1) + fib(1) + fib(2)
  % 0 + 1 + 1 + fib(0) + fib(1)
  % 0 + 1 + 1 + 0 + 1
  % 3

  % define function fib2
  % uses elements of previous fibonacci tuple to produce the new one
  fib2(0) ->
    {0, 1};
  fib2(N) ->
    {element(2,fib2(N-1)),element(1,fib2(N-1)) + element(2,fib2(N-1))}.

  % Proof of concept (just in case it is needed)
  % tuple element 1 is last element from previous tuple
  % element 2 is the sum of of the last two elements in previous tuple
  % eg: fib2(1)
  % {element(2,fib2(0)), element(1,fib2(0)) + element(2,fib2(0))}
  % {1, 0 + 1}
  % {1,1} hence idea works


  % Why is this the better way of calculating the fibonacci numbers?
  % this style of calculating allows both elements of the tuple to be calculated in parallel
  % which should make it more efficient than the alternative of calculating both elements separately.

%================================================== TASK 2 ==================================================%
  % define perimeter using the area example
  % if its a circle then uses the formula of 2*pi*R and a rectangle is
  % just sum of 2 widths and heights
  perimeter({circle, {_X,_Y}, R}) ->
    2 * math:pi() * R;
  perimeter({rectangle, {_X,_Y}, H, W}) ->
    (2*H) + (2*W).

  % define overlap to indicate if two circles overlap or not
  % uses the circle formula of sqrt((x0-x1)^2 + (y0-y1)^2) = radius^2
  % if sum of radius (or difference for circle in a cirle) is equal to distance
  % the circles touch (minimum overlap), so it follows less than sum and
  % greater than difference also overlap
  overlap({circle, {X,Y}, R},{circle, {X1,Y1}, R2}) ->
    D = math:sqrt(((X1-X)*(X1-X))+((Y1-Y)*(Y1-Y))),
    case abs(R-R2) =< D andalso D =< R+R2 of
      true -> 'the circles overlap.';
      false -> 'the circles do not overlap.'
    end.

%================================================== TASK 3 ==================================================%
  % define occurs which takes two arguements and sees how many times arg 2 occurs
  % the list from arg 1.
  occurs([],_N) -> 0;
  occurs([X|Xs], N) when X==N ->
    1 + occurs(Xs,N);
  occurs([_X|Xs], N) ->
    occurs(Xs,N).

  % define odd which takes a list and returns only the odd values
  odd([]) -> [];
  odd([X|Xs]) when X rem 2 /= 0 ->
    [X|odd(Xs)];
  odd([_|Xs]) ->
    odd(Xs).

  % define parity which gives a number that is the total of 1's and 0's based
  % of if a number is odd or even, eg [1] is 1 and [2] is 0 and [1,1,2] is 2
  parity([]) -> 0;
  parity([X|Xs]) when X rem 2 /= 0 ->
    1 + parity(Xs);
  parity([_X|Xs]) ->
    0 + parity(Xs).

%================================================== TASK 4 ==================================================%
  % define game_score which takes list of rps games and returns socre for player
  % 1 (lhs). giving 1 for a win 0 for draw and -1 for a loss.
  game_score([]) -> 0;
  game_score([X|Xs]) -> hasWon(X) + game_score(Xs).
  hasWon({rock,paper}) -> -1;
  hasWon({rock,scissors}) -> 1;
  hasWon({scissors,rock}) -> -1;
  hasWon({scissors,paper}) -> 1;
  hasWon({paper,scissors}) -> -1;
  hasWon({paper,rock}) -> 1;
  hasWon({_,_}) -> 0.
