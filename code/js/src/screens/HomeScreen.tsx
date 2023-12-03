import React from "react";
import { Link } from "react-router-dom";


export const HomeScreen = () => {
    return (
        <div>
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
    );
}

/* 
<Link to="/profile">
                <button>Profile</button>
            </Link>
*/