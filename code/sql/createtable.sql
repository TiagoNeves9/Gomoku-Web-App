BEGIN TRANSACTION;

create schema IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.Users (
    id UUID NOT NULL primary key,
    username varchar(80) NOT NULL UNIQUE,
    encoded_password varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS dbo.Tokens (
    encoded_token VARCHAR(256) primary key,
    generated_at timestamp NOT NULL CHECK (generated_at <= CURRENT_TIMESTAMP),
    user_id uuid references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Games (
    id UUID NOT NULL primary key ,
    last_move timestamp NOT NULL CHECK (last_move <= CURRENT_TIMESTAMP),
    game_state VARCHAR(64) NOT NULL
        CHECK
            (
                game_state in (
                    'NEXT_PLAYERX',
                    'NEXT_PLAYERO',
                    'PLAYERX_WON',
                    'PLAYERO_WON',
                    'DRAW'
                )
            ),

    board jsonb NOT NULL,
    score int NOT NULL,
	player_x UUID references dbo.Users(id),
    player_o UUID references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Statistics (
    username varchar(80) NOT NULL references dbo.Users(username) primary key,
    played_games int default 0,
    score int default 0
);



COMMIT;