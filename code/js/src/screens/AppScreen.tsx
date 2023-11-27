import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Link } from "react-router-dom";

export function AppScreen() {
    return (
        <div>
            <Link to="/rankings">
                <button>Rankings</button>
            </Link>
            <Link to="/play">
                <button>Play</button>
            </Link>
            <Link to="/profile">
                <button>Profile</button>
            </Link>
            <Link to="/game">
                Game
            </Link>
        </div>
    );
}