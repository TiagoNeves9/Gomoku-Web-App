import React, { StrictMode } from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { createRoot } from "react-dom/client";
import { App } from "./App";
import { HomeScreen } from "./screens/HomeScreen";
import { LoginScreen } from "./screens/LoginScreen";
import { RegisterScreen } from "./screens/RegisterScreen";
import { AppScreen } from "./screens/AppScreen";
import { ProfileScreen } from "./screens/ProfileScreen";


export const router = createBrowserRouter(
    [
        {
            path: "/",
            element: <App />,
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
        }
    ]
)

export function RenderApp() {
    const container = document.getElementById("container")!;
    const root = createRoot(container);
    root.render(
        <StrictMode>
            <RouterProvider router={router} />
        </StrictMode>
    );
}