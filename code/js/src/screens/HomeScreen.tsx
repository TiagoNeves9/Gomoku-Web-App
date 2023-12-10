import React from "react";
import { Link } from "react-router-dom";
import { useCurrentUser } from "../services/Auth";


export const HomeScreen = () => {
    const currentUser = useCurrentUser();
    console.log(currentUser)

    return (
        <div>
            <h1>Welcome to Gomoku application {currentUser.username}</h1>
            <Link to="/play">
                <button>Play</button>
            </Link>
        </div>
    );
}