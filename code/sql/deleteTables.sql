BEGIN TRANSACTION;


DELETE FROM dbo.Statistics;
DELETE FROM dbo.Games;
DELETE FROM dbo.Tokens;
DELETE FROM dbo.Lobbies;
DELETE FROM dbo.Users;


COMMIT;