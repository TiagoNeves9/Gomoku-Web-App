BEGIN TRANSACTION;

create schema IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.Users (
    id UUID NOT NULL primary key,
    username varchar(80) NOT NULL UNIQUE,
    password_validation varchar(256) NOT NULL,
    unique(id)
);

CREATE TABLE IF NOT EXISTS dbo.Tokens (
    token_validation VARCHAR(256) primary key,
    user_id uuid references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Games (
    id UUID NOT NULL,
    last_move timestamp NOT NULL CHECK (last_move <= CURRENT_TIMESTAMP),
    game_state VARCHAR(64) NOT NULL CHECK (game_state LIKE 'NEXT_PLAYER0' OR 'NEXT_PLAYER1' OR 'PLAYER0_WON' OR 'PLAYER1_WON' OR 'DRAW'),
    board CHAR(225) NOT NULL,
    score int NOT NULL,
	player1 int references dbo.Users(id),
    player2 int references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Stones (
    
)


COMMIT;