BEGIN TRANSACTION;


create schema IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.Users (
    id UUID NOT NULL primary key,
    username varchar(80) NOT NULL UNIQUE,
    encoded_password varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS dbo.Tokens (
    encoded_token VARCHAR(256) primary key,
    user_id uuid references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Games (
    id UUID NOT NULL,
    last_move timestamp NOT NULL CHECK (last_move <= CURRENT_TIMESTAMP),
    game_state VARCHAR(64) NOT NULL
        CHECK (game_state LIKE 'NEXT_PLAYERX' OR 'NEXT_PLAYERO' OR 'PLAYERX_WON' OR 'PLAYERO_WON' OR 'DRAW'),
    board CHAR(225) NOT NULL,
    score int NOT NULL,
	playerX int references dbo.Users(id),
    playerO int references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Stones (
    game_id UUID references dbo.Games(id),
    playLine int not null,
    playCol int not null,
    color varchar(16) NOT NULL CHECK (color LIKE 'white' or 'black')
);


COMMIT;