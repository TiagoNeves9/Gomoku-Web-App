import React from "react";
import { Link, Navigate } from "react-router-dom";
import { useCurrentUser } from "../services/Auth";
import '../css/General.css';


export function AppScreen() {
    const currentUser = useCurrentUser();

    if (currentUser) {
        return (
            <Navigate
                to="/home"
                state={{ source: location.pathname }}
                replace={true}
            />
        );
    }

    return (
        <div id="app">
            <h1 className="h1">Welcome to Gomoku Application</h1>
            <h2 className="h2">Made by G09</h2>
            <p style={{ paddingBottom: 200 }}>
                To get started, click <b><Link to="/login">here</Link> </b>
                to log in or <b><Link to="/register">here</Link> </b>
                to register if you don't have an account.
            </p> <br /> <br />
        </div>
    )
}