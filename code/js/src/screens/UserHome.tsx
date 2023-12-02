import { Typography } from "@material-ui/core";
import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "./LoginScreen";

export function UserHome() {
    const auth = useContext(AuthContext)
    
    return(
        <div>
            <h1>
                Welcome {auth.user?.username}!
            </h1>
        </div>
    )
}