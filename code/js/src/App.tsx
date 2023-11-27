import React from "react";
import { Link, useNavigate } from "react-router-dom";


export function App() {
    const navigate = useNavigate();

    function handleOnClick(toUrl: string) {
        navigate(toUrl);
    }

    return (
        <div>
            <h1>App</h1>
            <Link to="/login">Login</Link>
            <text> | </text>
            <Link to="/register">Register</Link>
        </div>
    )
}