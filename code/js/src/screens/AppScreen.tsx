import React from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../services/Auth";
import NavBar, { Layout } from "./utils";




export function AppScreen() {
    
    return (
        <Layout>
            <NavBar />
        <div>
            <title>Welcome to Gomoku application</title>
            <h1>Made by G09</h1>
            <text>
                To get started, click <b><Link to="/login">here</Link></b> to log in
                or <b><Link to="/register">here</Link></b> to register if you don't have an account.
            </text> <br /> <br />
        </div>
        </Layout>
    )
}

function useContext(AuthContext: any) {
    throw new Error("Function not implemented.");
}
