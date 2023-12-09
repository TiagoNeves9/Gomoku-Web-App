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
import { Game } from "./screens/GameScreen";
import { AuthContainer } from "./services/Auth";
import { RequireAuth } from "./services/AuthRequire";


export const router = createBrowserRouter(
    [
        {
            path: "/",
            element: (
                <AuthContainer>
                    <Outlet/>
                </AuthContainer>
            ),
            children: [
                {
                    path: "/",
                    errorElement: <div>
                        <Link to="/"> Return </Link>
                        <h1 style={{ color: 'red' }}>Error Page</h1>
                    </div>,
                    element: <AppScreen /> 
                },
                {
                    path: "home",
                    element: (
                        <RequireAuth>
                            <HomeScreen /> 
                        </RequireAuth>
                    )
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
                    element: (
                        <RequireAuth>
                            <ProfileScreen /> 
                        </RequireAuth>
                    )
                },
                {
                    path: "play",
                    element: (
                        <RequireAuth>
                            <PlayScreen /> 
                        </RequireAuth>
                    )
                },
                {
                    path: "lobby",
                    element: (
                        <RequireAuth>
                            <LobbyScreen /> 
                        </RequireAuth>
                    )
                },
                {
                    path: "game",
                    element: (
                        <RequireAuth>
                            <Game /> 
                        </RequireAuth>
                    )
                },
            ]
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