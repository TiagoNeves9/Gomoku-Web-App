import React, { StrictMode } from "react";
import { createBrowserRouter, Outlet, RouterProvider } from "react-router-dom";
import { Link } from "react-router-dom";
import { AppScreen } from "./screens/AppScreen";
import { LoginScreen } from "./screens/LoginScreen";
import { RegisterScreen } from "./screens/RegisterScreen";
import { HomeScreen } from "./screens/HomeScreen";
import { PlayScreen } from "./screens/PlayScreen";
import { RankingsScreen } from "./screens/RankingsScreen";
import { AuthorsScreen } from "./screens/AuthorsScreen";
import { AboutScreen } from "./screens/AboutScreen";
import { ProfileScreen } from "./screens/ProfileScreen";
import { UserHome } from "./screens/UserHomeScreen";


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
            path: "login",
            element: <LoginScreen />
        },
        {
            path: "register",
            element: <RegisterScreen />
        },
        {
            path: "home",
            element: <HomeScreen />
        },
        {
            path: "play",
            element: <PlayScreen />
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
            path: "profile",
            element: <ProfileScreen />
        },
        {
            path: "about",
            element: <AboutScreen />
        },
        {
            path: "/userhome/{username}",
            element: <UserHome />
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