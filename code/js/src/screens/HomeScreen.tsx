import React from "react";
import { Link } from "react-router-dom";


export const HomeScreen = () => {
    return (
        <div>
            <Link to="/ranking">
                <button>Rankings</button>
            </Link>
            <Link to="/game">
                <button>Play</button>
            </Link>
            <Link to="/profile">
                <button>Profile</button>
            </Link>
        </div>
    );
}