import React from 'react'
import { useNavigate } from "react-router-dom";
import { Board } from './screens/GameScreen'
import './App.css'

export default function App(){
    return(
        <div id="app">
            <Board />
        </div>
    )
}


/*
export function App() {
    const navigate = useNavigate();

    function handleOnClick(toUrl: string) {
        navigate(toUrl);
    }

    return (
        <div>
            <h1>App</h1>
            <button onClick={() => handleOnClick("login")}>Login</button>
            <button onClick={() => handleOnClick("register")}>Register</button>
        </div>
    )
}*/