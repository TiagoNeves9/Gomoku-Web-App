import React, { createContext, useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import '../css/Board.css'
import { GameService } from "../services/GameService";
import { useParams } from 'react-router-dom';
import { Game } from "../domain/Game";
import { useCurrentUser } from "../services/Auth";
import { useInterval } from "./LobbyScreen";


type GameType = {
    game: Game | null;
    turn: Boolean,
    setTurn : (boolean) => void,
    play? : (cell) => void;
};

export const GameContext = createContext<GameType>({  
    game: undefined,
    turn: false,
    setTurn: () => {},
    play: () => {} 
});


function Cell(props) {
    const { play, turn } = useContext(GameContext)
    
    const onCellClick = () => {
        
        const playSettings = {
            "row" : props.row,
            "col" : props.col
        }
        play(playSettings)
        console.log("Placed " + props.row + props.col)
    }

    
    if (props.cellType == "BLACK_PIECE")
        return (
            <button disabled className="cell black-stone"></button>
        )
    else if (props.cellType == "WHITE_PIECE")
        return (
            <button disabled className="cell white-stone"></button>
        )
    else if(turn)
        return (
            <button onClick={onCellClick} className="cell "></button>
        )
    else 
        return (
            <button disabled className="cell "></button>
        )
}

const cols = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S"]
const rows = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"]

export function Board() {
    const { game } = useContext(GameContext)
    
    let board = []
    let cells: Map<String, String> = new Map<String, String>(Object.entries(game.boardCells))

    const boardSize = game.rules.boardDim

    const colsStart = 19 - (19 - boardSize)
    const rowsLimit = boardSize

    for (let i = colsStart - 1; i >= 0; i--) {
        for (let j = 0; j < rowsLimit; j++) {
            let key: string = (rows[j] + cols[i]).toString()
            
            if (cells.has(key)){
                board.push(<Cell cellType={cells.get(key)} row={rows[j].toString()} col={cols[i].toString()} />)
            } else{
                board.push(<Cell cellType={"EMPTY"} row={rows[j].toString()} col={cols[i].toString()} />)
            }
        }
    }
    
    

    return (
        <div id="board" style={{"--board-size": boardSize} as React.CSSProperties}>
            {board}
        </div>
    )
}

export function Game() {
    let { gid } = useParams(); 
    const [obtainedGame, setObtainedGame] = useState<Game>(null)
    const [turn, setTurn] = useState<Boolean>(false)
    const POLLING_INTERVAL = 6000;
    const user = useCurrentUser()
    const [isPolling, setIsPolling] = useState(true);

    useEffect(() => {
        const intervalId = setInterval(async () => {
            if (isPolling) {
                let gameObj = await GameService.getGame(gid);

                console.log(gameObj);
                setObtainedGame(gameObj);

                if(gameObj.boardState == "RUNNING"){
                   if(user.username == gameObj.turn){
                        setTurn(true)
                        setIsPolling(false)
                    } 
                    else {
                        setTurn(false)
                        setIsPolling(true)
                    } 
                } else {
                    setIsPolling(false)
                }
                
            }
        }, POLLING_INTERVAL);
    
        return () => clearInterval(intervalId);
    }, [isPolling])
    
    async function doPlay(cell){
        setTurn(false)
        let gameObj = await GameService.play(gid,cell)
        
        console.log("gameObj: ")
        console.log(gameObj)
        setObtainedGame(gameObj)
        setIsPolling(true)
    }

    if(obtainedGame != null){
        return (
        <GameContext.Provider value={{game: obtainedGame, play: doPlay, turn, setTurn}}>
           <div id="game">
            <h1> GOMOKU </h1>
            <Board />
            {obtainedGame.boardState != "RUNNING" 
                ? obtainedGame.turn == user.username 
                    ? <h1> You Won! </h1> 
                    : <h1> You Lost! </h1> 
                : <></>
            }
            <h1><Link to="/home"> Home </Link></h1>
            <h1><Link to="/play"> Lobbies </Link></h1>
        </div> 
        </GameContext.Provider>
    )
    } else {
        
        return(
            <div>
                Loading Game . . .
            </div>
        )
    }
    
}