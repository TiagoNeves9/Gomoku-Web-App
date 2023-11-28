import React, { StrictMode } from "react";
import { createBrowserRouter, Outlet, RouterProvider } from "react-router-dom";
import { Link } from "react-router-dom";
import { Game } from "./screens/GameScreen";
import { AppScreen } from "./screens/AppScreen";
import { RegisterScreen } from "./screens/RegisterScreen";
import { ProfileScreen } from "./screens/ProfileScreen";
import RankingScreen from "./screens/RankingScreen";
import { LoginScreen } from "./screens/LoginScreen";


export const router = createBrowserRouter(
    [
        {
            path: "/",
            errorElement: <div>
                <Link to="/"> Return </Link>
                <h1 style={{ color: 'red' }}>Error Page</h1>
            </div>,
            element: <AppScreen /> //Switch to navbar?
        },/*
        {
            path: "game",
            element: <Game />
        },*/
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
            path: "ranking",
            element: <RankingScreen />
        }
    ]
)

export default function App() {
    return (
        <StrictMode>
            <RouterProvider router={router} />
        </StrictMode>
    )
}