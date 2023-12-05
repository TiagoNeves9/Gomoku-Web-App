import React from "react";
import { Link } from "react-router-dom";
import '../css/General.css'
import NavBar, { Layout } from "./utils";


export function AppScreen() {
    return (
        <Layout>
            <NavBar />
            <div id="app">
                <img src="public/images/img_gomoku_icon.png" alt="Gomoku_image" id="Gomoku_image" />
                <h1 className="h1">Welcome to Gomoku Application</h1>
                <h2 className="h2">Made by G09</h2>
                <text>
                    To get started, click <b><Link to="/login">here</Link></b> to log in
                    or <b><Link to="/register">here</Link></b> to register if you don't have an account.
                </text> <br /> <br />
            </div>
        </Layout>
    )
}
