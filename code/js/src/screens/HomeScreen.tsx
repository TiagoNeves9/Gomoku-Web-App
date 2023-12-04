import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../services/Auth";
import NavBar, { Layout } from "./utils";


export const HomeScreen = () => {

    const currentUser = useContext(AuthContext);
    console.log(currentUser.user)

    return (
        <Layout>
            <NavBar />
        <div>
            <h1>Welcome to Gomoku application {currentUser.user ? currentUser.user.username : ""}</h1>
            <Link to="/play">
                <button>Play</button>
            </Link>
            <Link to="/rankings">
                <button>Rankings</button>
            </Link>
            <Link to="/authors">
                <button>Authors</button>
            </Link>
            <Link to="/about">
                <button>About</button>
            </Link>
        </div>
        </Layout>
    );
}

/* 
<Link to="/profile">
                <button>Profile</button>
            </Link>
*/