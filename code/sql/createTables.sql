BEGIN TRANSACTION;


create schema IF NOT EXISTS dbo;

CREATE TABLE IF NOT EXISTS dbo.Users (
    user_id uuid primary key,
    username varchar(80) not null unique,
    encoded_password varchar(255) not null
);

CREATE TABLE IF NOT EXISTS dbo.Lobbies (
	lobby_id uuid primary key,
	host uuid references dbo.Users(user_id)
);

CREATE TABLE IF NOT EXISTS dbo.Tokens (
    encoded_token varchar(255) primary key,
    generated_at timestamp not null CHECK (generated_at <= CURRENT_TIMESTAMP),
    user_id uuid references dbo.Users(user_id)
);

CREATE TABLE IF NOT EXISTS dbo.Games (
    game_id uuid primary key,
    user1_id uuid references dbo.Users(user_id),
    user2_id uuid references dbo.Users(user_id),
    board_positions text not null,
	board_type varchar(10) not null, 
    current_turn varchar(15),
    score int,
    now timestamp not null CHECK (now <= CURRENT_TIMESTAMP)
);

CREATE TABLE IF NOT EXISTS dbo.Statistics (
    user_id uuid not null references dbo.Users(user_id) primary key,
    played_games int default 0 not null,
    score int default 0 not null
);


COMMIT;