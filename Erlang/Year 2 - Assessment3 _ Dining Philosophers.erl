%%%-------------------------------------------------------------------
%%% @author Priyesh Patel
%%% Created : 08. Mar 2017 01:18 PM
%%%-------------------------------------------------------------------
-module(prp6A3).
-compile[export_all].

%=======================PART 1=======================%
% Table Model ( Circular so Edmund joins to Fork1)
% Fork1 | Aristotle | Fork2 | Bertrand | Fork3 | Confucius | Fork4 | Diogenes | Fork5 | Edmund

college() ->
  F1id = spawn(?MODULE, fork, ["Fork1"]),
  F2id = spawn(?MODULE, fork, ["Fork2"]),
  F3id = spawn(?MODULE, fork, ["Fork3"]),
  F4id = spawn(?MODULE, fork, ["Fork4"]),
  F5id = spawn(?MODULE, fork, ["Fork5"]),
  spawn(?MODULE, philosopher, ["Aristotle", F1id, F2id])!think,
  spawn(?MODULE, philosopher, ["Bertrand", F2id, F3id])!think,
  spawn(?MODULE, philosopher, ["Confucius", F3id, F4id])!think,
  spawn(?MODULE, philosopher, ["Diogenes", F4id, F5id])!think,
  spawn(?MODULE, philosopher, ["Edmund", F5id, F1id])!think,
  ready.

fork(Name) ->
  receive
    {pickup, Philosopher} -> io:fwrite(Name ++ ": in use~n"),
      Philosopher ! gotFork,
      receive
        drop -> io:fwrite(Name ++ ": on table~n"),
          fork(Name) %reset the fork to be ready to receive a pickup
      end
  end
.

philosopher(Name, LeftFork, RightFork) ->
  receive
    hungry -> io:fwrite(Name ++ ": hungry~n"),
      philosopherHungry(Name, LeftFork, RightFork, self()),
      philosopher(Name, LeftFork, RightFork); %reset the philosopher to be ready to receive next "action"
    eat -> io:fwrite(Name ++ ": eating~n"),
      philosopherEating(self(), LeftFork, RightFork),
      philosopher(Name, LeftFork, RightFork); %reset the philosopher to be ready to receive next "action"
    think -> io:fwrite(Name ++ ": thinking~n"),
      philosopherThinking(self()),
      philosopher(Name, LeftFork, RightFork) %reset the philosopher to be ready to receive next "action"
  end.

% (rand:uniform(9) * 100) gets a random number between 100 and 900 in increments of 100
% (rand:uniform(9) * 10) gets a random number between 10 and 90 in increments of 10
% (rand:uniform(10)) gets a random number between 1 and 10 in increments of 1
% added together to get a number between 100 and 1000

% After a random time the philosopher drops both forks and goes back to thinking
philosopherEating(PID, LeftFork, RightFork) ->
  receive
  after (rand:uniform(9) * 100) + (rand:uniform(9) * 10) + (rand:uniform(10)) -> LeftFork ! drop,
    RightFork ! drop,
    PID ! think
  end.

% After a random time the philosopher goes from thinking to being hungry
philosopherThinking(PID) ->
  receive
  after (rand:uniform(9) * 100) + (rand:uniform(9) * 10) + (rand:uniform(10)) -> PID ! hungry
  end.

% The philosopher tries to pick up the left fork and on success
% tries to pick up the right fork, on success of that the
% philosopher can start eating.
% If fork cannot be picked up the philosopher waits until it can be
philosopherHungry(Name, LeftFork, RightFork, Philosopher) ->
  LeftFork ! {pickup, self()},
  receive
    gotFork -> io:fwrite(Name ++ ": got left fork~n"),
      RightFork ! {pickup, self()},
      receive
        gotFork -> io:fwrite(Name ++ ": got right fork~n"),
          Philosopher ! eat
      end
  end.

%=======================PART 2=======================%
% Qu 1
%   There is a remote possibility that deadlock could occur, this
%   would come about if the the timeouts for all five philosophers
%   either synced up or if they initiated with the same about of
%   time in their think state. This can be simulated by removing the
%   two timers hence the scenario of them being initiated with the
%   same thinking time. In this scenario all philosophers go for their
%   left fork and when they go for the right their are no more forks
%   on the table hence locked.

% Qu 2
%   a)
%     deadlock could be avoided by adding mutual exclusions to the
%     forks when a philosopher gets to hungry state, this way the
%     forks are prevented from being taken by an adjacent philosopher
%     until the philosopher has eaten and unlocked the forks.
%   b)
%     deadlock could be detected by adding a timeout to the picking
%     up of forks so that if all philosophers have picked up their
%     left fork and can't get the right they would drop the left
%     after a certain time and try again.


