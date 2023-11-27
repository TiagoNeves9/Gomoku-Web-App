

export enum State {
    PLACING_SHIPS = "PLACING_SHIPS",
    NEXT_PLAYER_1 = "NEXT_PLAYER_1",
    NEXT_PLAYER_2 = "NEXT_PLAYER_2",
    PLAYER_1_WON = "PLAYER_1_WON",
    PLAYER_2_WON = "PLAYER_2_WON",
  }

export interface BoardRules {
    boardDim: number,
    opening: string, 
    variant: string
}

export interface Game {
    id: String;
    usernameB: String,
    usernameW: String
    turn: String;
    rules: BoardRules;
    boardCells: Map<String, String>;
    boardState: String;
  }

