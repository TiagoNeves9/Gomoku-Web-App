import React from "react";
import { Link } from "react-router-dom";
import { useFetch } from "../custom-hooks/useFetch";


export const ProfileScreen = () => {
    return (
        <div>
            <Link to="/home">Return</Link>
            <h1>Profile</h1>
            <h2>Username</h2>
            <text>Admin</text>
            <h2>Rank</h2>
            <text>Global Elite</text>
        </div>
    );
}