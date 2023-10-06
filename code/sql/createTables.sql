BEGIN TRANSACTION;


create schema IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.Users (
    id uuid not null primary key,
    username varchar(80) not null unique,
    encoded_password varchar(256) not null
);

CREATE TABLE IF NOT EXISTS dbo.Tokens (
    encoded_token varchar(256) primary key,
    generated_at timestamp not null CHECK (generated_at <= CURRENT_TIMESTAMP),
    user_id uuid references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Games (
    id uuid not null primary key,
    last_move timestamp not null CHECK (last_move <= CURRENT_TIMESTAMP),
    game_state VARCHAR(64) not null
        CHECK (
            game_state in (
                'NEXT_PLAYERX',
                'NEXT_PLAYERO',
                'PLAYERX_WON',
                'PLAYERO_WON',
                'DRAW'
            )
        ),
    board jsonb not null,
    score int not null,
	player_x UUID references dbo.Users(id),
    player_o UUID references dbo.Users(id)
);

CREATE TABLE IF NOT EXISTS dbo.Statistics (
    user_id uuid not null references dbo.Users(id) primary key,
    played_games int default 0 not null,
    score int default 0 not null
);


COMMIT;