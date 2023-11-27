import React, { StrictMode } from "react";
import { createBrowserRouter, Outlet, RouterProvider } from "react-router-dom";
import { Link } from "react-router-dom";
import {  Game } from "./screens/GameScreen";
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
                <Link to="/"> Home </Link>
                <h1 style={{color: 'red'}}>Error Page</h1>
            </div>,
            element:
            <div> 
                <AppScreen/> //Switch board to navbar?
            </div>
            /*  use navigation bar here <-
            children: [
                {
                    path: "home",
                    element: <HomeScreen />
                },
                {
                    path: "login",
                    element: <LoginScreen />
                },
                {
                    path: "game",
                    element: <Game />
                }
                
                {
                    path: "register",
                    element: <RegisterScreen />
                },
                {
                    path: "about",
                    element: <AboutScreen />
                }
                
            ]*/
        },
        {
            path: "app",
            element: <AppScreen />
        },
        {
            path: "game",
            element: <Game/>
        },
        {
            path: "app",
            element: <AppScreen />
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