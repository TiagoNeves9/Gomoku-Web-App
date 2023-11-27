import React, { createContext, useContext, useState, useEffect } from "react";
import { Link } from "react-router-dom";

import '..css/Board.css'
import { GameService } from "../services/GameService";

type ContextType = {
    turn : String,
    setTurnClick? : () => void
}

const TypeContext = createContext<ContextType>({turn: "EMPTY"});

function Cell(props){
    const [type, setType] = useState("EMPTY_PIECE") //BLACK_PIECE, WHITE_PIECE, EMPTY_PIECE
    const {turn, setTurnClick} = useContext(TypeContext)

    //todo: change this to make a request to the services
    const onCellClick = () => {
        GameService.play(1,props.cellKey)
        console.log("Placed " + turn)
        if(turn == "BLACK_PIECE") setType("BLACK_PIECE")
        else if(turn == "WHITE_PIECE") setType("WHITE_PIECE")
        else throw("ERROR | Player turn incorrect!")
        setTurnClick()
    }

    console.log("cell: " + props.cellType + " " + props.cellKey)
    if(props.cellType == "BLACK_PIECE"){
        return(
             <button disabled className="cell black-stone"></button>
        )
    }
    else if(props.cellType == "WHITE_PIECE"){
        return(
            <button disabled className="cell white-stone"></button>
        )
    }
    else {
       return(
        <button 
                onClick = {onCellClick}
                className="cell "></button>
    ) 
    }
    

}


const cols = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"]
const rows = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"]

export function Board() {
    let board = []
    let cells : Map<String, String> = new Map()

    cells["1A"] = "WHITE_PIECE";
    cells["1B"] = "BLACK_PIECE";
    cells["10D"] = "WHITE_PIECE";
    cells["10F"] = "BLACK_PIECE"

    const [turn, setTurn] = useState("WHITE_PIECE")
    const onClickSwitchTurn = () => {
        console.log("Switched turn! Previous: " + turn)
        if(turn == "BLACK_PIECE") setTurn("WHITE_PIECE")
        else setTurn("BLACK_PIECE")
        console.log("Switched turn! After: " + turn)
        console.log(" ")
    };
    console.log(cells)
    for(let i = cols.length-1; i>=0; i--){
        for(let j = 0; j<rows.length; j++){
            let key: string = (rows[j]+cols[i]).toString()
            console.log(key)
            if(cells.has(key)) board.push(<Cell cellKey={key} cellType={cells.get(key)}/>)
            board.push(<Cell cellKey={key} cellType={"EMPTY"}/>)
        }   
    }

    return(
        <TypeContext.Provider value={{turn:turn, setTurnClick:onClickSwitchTurn}}>
            <div id="board">{board}</div>
        </TypeContext.Provider>
    )
}

export function Game() {

    return (
        <div>
            <h1> GOMOKU </h1>
            <Board/>
            <h1><Link to="/home"> Home </Link></h1>
        </div>
    )
}

