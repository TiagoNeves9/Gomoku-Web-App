import { Typography } from "@material-ui/core";
import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext, useCurrentUser } from "../services/Auth";


export function UserHome() {
const user = useCurrentUser(); 

    return (
        <div>
            <h1> Welcome to the user home page {user.username || "NAO FUNCIONA"}! </h1>
        </div>
    );
}