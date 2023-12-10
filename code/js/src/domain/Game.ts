

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

