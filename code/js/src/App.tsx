import React from "react";
import { useNavigate } from "react-router-dom";


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
}