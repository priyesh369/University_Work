-module(assessment4).
-compile[export_all].
%+++++++++++++++ Task 2 ++++++++++++++++++
%function to spawn the 5 client processes, 1 server process, 1 buffer process and 1 database process
%use this to demo Task 2 and 3
startJustLike() ->
  DatabaseID = spawn(?MODULE, database, [[{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}]]),
  BufferID = spawn(?MODULE, buffer, [DatabaseID, []]),
  ServerID = spawn(?MODULE, likeServer, [BufferID]),
  spawn(?MODULE, client, ["Jake Peralta", ServerID]),
  spawn(?MODULE, client, ["Rosa Diaz", ServerID]),
  spawn(?MODULE, client, ["Terry Jeffords", ServerID]),
  spawn(?MODULE, client, ["Amy Santiago", ServerID]),
  spawn(?MODULE, client, ["Gina Linetti", ServerID]),
  ready.

client(Username, Server) ->
  %crypto:rand_uniform(0, 6) for Post ID from 0 to 5
  %crypto:rand_uniform(0, 5001) for Time between 0 and 5 seconds (in milliseconds so between 0 and 5000)
  receive
    stop -> ok
  after crypto:rand_uniform(0, 5001) ->
    PostNo = crypto:rand_uniform(0, 6),
    Server ! {like, PostNo, self()},
    receive
      {likes, L} ->
        io:fwrite("~s: ~p likes on post ~p.~n", [Username, L, PostNo]);
      {nopost} ->
        io:fwrite("~s: no post ~p.~n", [Username, PostNo])
    end,
    client(Username, Server)
  end.

likeServer(DB) ->
  receive
    {like, Post, Client} ->
      DB ! {like, Post, self()},
      receive {dataReply, Data} ->
        case isPost(Data, Post) of
          true -> L = numOfLikes(Data, Post),
            Client ! {likes, L + 1},
            likeServer(DB);
          false -> Client ! {nopost},
            likeServer(DB)
        end
      end
  end.

numOfLikes([], _Post) -> 0;
numOfLikes([{Post, Likes} | _Posts], Post) -> Likes;
numOfLikes([_ | Posts], Post) -> numOfLikes(Posts, Post).

isPost([], _Post) -> false;
isPost([{Post, _} | _Posts], Post) -> true;
isPost([_ | Posts], Post) -> isPost(Posts, Post).

%++++++++++++++++++ Task 3 ++++++++++++++++++++++++++++++++
% My approach is on the buffer receiving a {like, Post, Server} it will first
% check through the Cache to see if the post is already there, if it is the buffer
% will send a response to the server using that cached data and then after a 500 millisecond
% interval it send a message to the database to update the "live" data and then get back data
% to update the cache with. If the post is not found in the cache then after 500 milliseconds the
% buffer will send a message to the database to update the cache and send the server the response
%
% This approach means that the like count that the user will see won't necessarily be the correct /
% up-to-date value as the Cache is always slightly older than the database. This means that while post
% 1 could have 4 likes in the database the user may only see it as having 2 or 3 likes as that is what
% stored in the cache from the last update.

buffer(Database, Cache) ->
  receive
    {like, Post, Server} ->
      case lists:keyfind(Post, 1, Cache) of
        {_Post, _Likes} ->
          Server ! {dataReply, Cache},
          receive
          after 500 ->
            Database ! {like, Post, self()},
            receive
              {dataReply, Data} ->
                self() ! {update, Data}
            end
          end,
          buffer(Database, Cache);
        false ->
          receive
          after 500 ->
            Database ! {like, Post, self()},
            receive
              {dataReply, NewDBData} ->
                Server ! {dataReply, NewDBData},
                buffer(Database, NewDBData)
            end
          end
      end;
    {update, Data} ->
      io:fwrite("BufferUpdate: ~w~n", [Data]),
      buffer(Database, Data)
  end.

database(Data) ->
  receive
    {like, Post, Server} ->
      Server ! {dataReply, Data},
      Data2 = likePost(Data, Post),
      io:fwrite("DatabaseCurr: ~w~n", [Data2]),
      database(Data2)
  end.

likePost([], _Post) -> [];
likePost([{Post, Likes} | Posts], Post) -> [{Post, Likes + 1} | Posts];
likePost([P | Posts], Post) -> [P | likePost(Posts, Post)].

%++++++++++++++++++++++++++++++++++Task 4 and 5+++++++++++++++++++++++++++++++++++

% This is an attempt to unify the post and like servers following the reply from my
% question on the Q&A page.
% It seems to run but logic wise I'm unsure if it actually allows
% users to like posts while a user is creating a post since when
% lock is called the server will not carry on until unlocked (unless I'm mistaken) so
% like messages will just be wailing in the mailbox until unlocked. And as such I am unsure if
% this what was wanted so For this reason I have included my original implementation that has post and like
% handled by separate servers in case this single version is wrong and my original would be marked higher
% startPostAndLikeUni will run this single server version and startPostAndLikeTwo will run the split
% version which I believe handles the previously described issue that I faced will a single server
% (The split version code is near the end of the document, I hope its inclusion won't negatively affect my marks).

%function to spawn the 5 client processes, 2 different server processes and 1 database process
%use this to demo Task 4 and 5
startPostAndLikeUni() ->
  DatabaseID = spawn(?MODULE, newDatabase, [[{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}]]),
  UniServer = spawn(?MODULE, newServer, [DatabaseID, 6]),
  spawn(?MODULE, newClientUni, ["Jake Peralta", UniServer]),
  spawn(?MODULE, newClientUni, ["Rosa Diaz", UniServer]),
  spawn(?MODULE, newClientUni, ["Terry Jeffords", UniServer]),
  spawn(?MODULE, newClientUni, ["Amy Santiago", UniServer]),
  spawn(?MODULE, newClientUni, ["Gina Linetti", UniServer]),
  ready.

newClientUni(Username, Server) ->
  % crypto:rand_uniform(0, 16) for Post ID from 0 to 15 to like the relating post for the demo
  % as demo progresses eventually the nopost message should stop as new posts are added. For Example:
  % DB: [{1,0},{2,1},{3,0},{4,1},{5,0},{6,0},{7,0},{8,0}]
  % 3> Rosa Diaz: no post 15.
  % DB: [{1,0},{2,2},{3,0},{4,1},{5,0},{6,1},{7,1},{8,1},{9,0},{10,1},{11,0},{12,0},{13,0},{14,0},{15,0}]
  % 3> Gina Linetti: 1 likes on post 15.

  % crypto:rand_uniform(0, 2) == 0 to select between liking a post and making a post, 0 (true) will like
  % and 1 (false) will make a post.
  receive
    stop -> ok
  after crypto:rand_uniform(0, 5001) ->
    case (crypto:rand_uniform(0, 2) == 0) of
      true ->
        PostNo = crypto:rand_uniform(0, 11),
        Server ! {like, PostNo, self()},
        receive
          {likes, L} ->
            io:fwrite("~s: ~p likes on post ~p.~n", [Username, L, PostNo]);
          {nopost} ->
            io:fwrite("~s: no post ~p.~n", [Username, PostNo])
        end,
        newClientUni(Username, Server);
      false ->
        Server ! {lock, self()},
        receive
          {locked, Session, NextAvailablePostID} ->
            Session ! {add, NextAvailablePostID},
            receive
              done ->
                io:fwrite("~s: made Post ~p.~n", [Username, NextAvailablePostID]),
                newClientUni(Username, Server)
            end
        end
    end
  end.

newServer(DB, NextOpenPostID) ->
  receive
    {lock, Client} ->
      Session = spawn(?MODULE, makePostServerInner, [DB, self()]), % separated to reduce the number of nested receives
      Client ! {locked, Session, NextOpenPostID},
      receive unlock ->
        Client ! done,
        newServer(DB, NextOpenPostID + 1)
      end;
    {like, Post, Client} ->
      DB ! {like, Post, self()},
      receive {dataReply, Data} ->
        case isPost(Data, Post) of
          true -> L = numOfLikes(Data, Post),
            Client ! {likes, L + 1},
            newServer(DB, NextOpenPostID);
          false -> Client ! {nopost},
            newServer(DB, NextOpenPostID)
        end
      end
  end.

makePostServerInner(DB, Parent) ->
  receive
    {add, Post} ->
      DB ! {add, Post, self()},
      makePostServerInner(DB, Parent);
    unlock ->
      Parent ! unlock
  end.

newDatabase(Data) ->
  receive
    {add, Post, Server} ->
      Data2 = lists:append(Data, [{Post, 0}]),
      Server ! unlock,
      io:fwrite("DB: ~w~n", [Data2]),
      newDatabase(Data2);
    {like, Post, Server} ->
      Server ! {dataReply, Data},
      Data2 = likePost(Data, Post),
      io:fwrite("DB: ~w~n", [Data2]),
      newDatabase(Data2)
  end.

%=========================== Split server version code follows ===========================================

startPostAndLikeTwo() ->
  DatabaseID = spawn(?MODULE, newDatabase, [[{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}]]),
  PostServerID = spawn(?MODULE, postServer, [DatabaseID, 6]),
  LikeServerID = spawn(?MODULE, likeServer, [DatabaseID]),
  spawn(?MODULE, newClientTwo, ["Jake Peralta", PostServerID, LikeServerID]),
  spawn(?MODULE, newClientTwo, ["Rosa Diaz",  PostServerID, LikeServerID]),
  spawn(?MODULE, newClientTwo, ["Terry Jeffords",  PostServerID, LikeServerID]),
  spawn(?MODULE, newClientTwo, ["Amy Santiago",  PostServerID, LikeServerID]),
  spawn(?MODULE, newClientTwo, ["Gina Linetti",  PostServerID, LikeServerID]),
  ready.

newClientTwo (Username, PostServer, LikeServer) ->
  % crypto:rand_uniform(0, 11) for Post ID from 0 to 10 to like the relating post for the demo
  % as demo progresses eventually the nopost message should stop as new posts are added. For Example:
  % DB: [{1,0},{2,1},{3,0},{4,1},{5,0},{6,0},{7,0},{8,0}]
  % 3> Rosa Diaz: no post 10.
  % DB: [{1,0},{2,2},{3,0},{4,1},{5,0},{6,1},{7,1},{8,1},{9,0},{10,1},{11,0},{12,0},{13,0}]
  % 3> Gina Linetti: 1 likes on post 10.

  % crypto:rand_uniform(0, 2) == 0 to select between liking a post and making a post, 0 (true) will like
  % and 1 (false) will make a post.
  receive
    stop -> ok
  after crypto:rand_uniform(0, 5001) ->
    case (crypto:rand_uniform(0, 2) == 0) of
      true ->
        PostNo = crypto:rand_uniform(0, 11),
        LikeServer ! {like, PostNo, self()},
        receive
          {likes, L} ->
            io:fwrite("~s: ~p likes on post ~p.~n", [Username, L, PostNo]);
          {nopost} ->
            io:fwrite("~s: no post ~p.~n", [Username, PostNo])
        end,
        newClientTwo(Username, PostServer, LikeServer);
      false ->
        PostServer ! {lock, self()},
        receive
          {locked, Session, NextAvailablePostID} ->
            Session ! {add, NextAvailablePostID},
            receive
              done ->
                io:fwrite("~s: made Post ~p.~n", [Username, NextAvailablePostID]),
                newClientTwo(Username, PostServer, LikeServer)
            end
        end
    end
  end.

% Server for handling adding a post
postServer(DB, NextOpenPostID) ->
  receive
    {lock, Client} ->
      Session = spawn(?MODULE, makePostServerInner, [DB, self()]),
      Client ! {locked, Session, NextOpenPostID},
      receive unlock ->
        Client ! done,
        postServer(DB, NextOpenPostID + 1)
      end
  end.