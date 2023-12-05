import React, { StrictMode } from "react";
import { createBrowserRouter, Outlet, RouterProvider } from "react-router-dom";
import { Link } from "react-router-dom";
import { AppScreen } from "./screens/AppScreen";
import { HomeScreen } from "./screens/HomeScreen";
import { RankingsScreen } from "./screens/RankingsScreen";
import { AuthorsScreen } from "./screens/AuthorsScreen";
import { AboutScreen } from "./screens/AboutScreen";
import { LoginScreen } from "./screens/LoginScreen";
import { RegisterScreen } from "./screens/RegisterScreen";
import { ProfileScreen } from "./screens/ProfileScreen";
import { PlayScreen } from "./screens/PlayScreen";
import { LobbyScreen } from "./screens/LobbyScreen";


export const router = createBrowserRouter(
    [
        {
            path: "/",
            errorElement: <div>
                <Link to="/"> Return </Link>
                <h1 style={{ color: 'red' }}>Error Page</h1>
            </div>,
            element: <AppScreen /> //Switch to navbar?
        },
        {
            path: "home",
            element: <HomeScreen />
        },
        {
            path: "rankings",
            element: <RankingsScreen />
        },
        {
            path: "authors",
            element: <AuthorsScreen />
        },
        {
            path: "about",
            element: <AboutScreen />
        },
        {
            path: "login",
            element: <LoginScreen />
        },
        {
            path: "register",
            element: <RegisterScreen />
        },
        {
            path: "profile",
            element: <ProfileScreen />
        },
        {
            path: "play",
            element: <PlayScreen />
        },
        {
            path: "lobby",
            element: <LobbyScreen />
        }
        /*,
        {
            path: "game",
            element: <Game />
        },*/
    ]
)

export default function App() {
    return (
        <StrictMode>
            <RouterProvider router={router} />
        </StrictMode>
    )
}