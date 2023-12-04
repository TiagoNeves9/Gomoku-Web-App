import { useContext } from "react";
import { AuthContext } from "../services/Auth";
import React from "react";




export function UserHome() {
    const currentUser = useContext(AuthContext);
    const username = currentUser.user?.username;

    return(
        <h1> Welcome {username} </h1>
    )
}