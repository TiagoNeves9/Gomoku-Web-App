import React, { createContext, useContext, useState } from "react";
import { Link } from "react-router-dom";
import '../css/Board.css'
import { GameService } from "../services/GameService";
import { useParams } from 'react-router-dom';
import { Game } from "../domain/Game";
import { useCurrentUser } from "../services/Auth";


type GameType = {
    game: Game | null;
    turn: Boolean,
    play? : (cell) => void;
};

export const GameContext = createContext<GameType>({  
    game: undefined,
    turn: false,
    play: () => {} 
});


function Cell(props) {
    const { play, turn } = useContext(GameContext)
    
    const onCellClick = () => {
        play(props.cellKey)
        console.log("Placed " + props.cellKey)
    }

    //console.log("cell: " + props.cellType + " " + props.cellKey)
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

const cols = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"]
const rows = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"]

export function Board() {
    const { game } = useContext(GameContext)
    
    let board = []
    let cells: Map<String, String> = new Map()

    cells["1A"] = "WHITE_PIECE";
    cells["1B"] = "BLACK_PIECE";
    cells["10D"] = "WHITE_PIECE";
    cells["10F"] = "BLACK_PIECE"

    const [turn, setTurn] = useState("WHITE_PIECE")
    const onClickSwitchTurn = () => {
        console.log("Switched turn! Previous: " + turn)
        if (turn == "BLACK_PIECE") setTurn("WHITE_PIECE")
        else setTurn("BLACK_PIECE")
        console.log("Switched turn! After: " + turn)
        console.log(" ")
    }
    //console.log(game.boardCells)
    for (let i = cols.length - 1; i >= 0; i--) {
        for (let j = 0; j < rows.length; j++) {
            let key: string = (rows[j] + cols[i]).toString()
            console.log(key)
            if (cells.has(key)){
                console.log("here!!!!")
                board.push(<Cell cellKey={key} cellType={cells.get(key)} />)
            }
                
            board.push(<Cell cellKey={key} cellType={"EMPTY"} />)
        }
    }

    return (
        <div id="board">{board}</div>
    )
}

export function Game() {
    let { gid } = useParams(); 
    const [obtainedGame, setObtainedGame] = useState<Game>(null)
    const [turn, setTurn] = useState<Boolean>(false)
    const user = useCurrentUser()
    console.log(user)

    async function loadGame(){
        await GameService.getGame(gid).then((gameObj) => {
            console.log(gameObj)
            setObtainedGame(gameObj)
            //if(user.username == obtainedGame.turn) setTurn(true)
        })
    }
    
    async function doPlay(cell){
        await GameService.play(gid,cell).then((gameObj) => {
            console.log(gameObj)
            setObtainedGame(gameObj.properties)
            //if(user.username == obtainedGame.turn) setTurn(true)
        })
    }
    if(obtainedGame != null){
        return (
        <GameContext.Provider value={{game: obtainedGame, play: doPlay, turn: turn}}>
           <div>
            <h1> GOMOKU </h1>
            <Board />
            <h1><Link to="/home"> Home </Link></h1>
        </div> 
        </GameContext.Provider>
    )
    } else {
        loadGame()
        return(
            <div>
                Loading Game . . .
            </div>
        )
    }
    
}