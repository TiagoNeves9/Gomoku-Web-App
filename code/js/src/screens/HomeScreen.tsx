import React from "react";
import { Link } from "react-router-dom";


export const HomeScreen = () => {
    return (
        <div>
            <title>Welcome to Gomoku application</title>
            <h1>Made by G09</h1>
            <text>
                To get started, click <Link to="/login"> to log in</Link> or
                <Link to="/register"> to register if you don't have an account</Link>
            </text>
            <Link to="/about">About</Link>
        </div>
    )
}