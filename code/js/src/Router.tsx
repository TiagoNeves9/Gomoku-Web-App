import React, { StrictMode } from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { createRoot } from "react-dom/client";
import { App } from "./App";
import { HomeScreen } from "./screens/HomeScreen";
import { LoginScreen } from "./screens/LoginScreen";


export const router = createBrowserRouter(
    [
        {
            path: "/",
            element: <App />,
            children: [
                {
                    path: "home",
                    element: <HomeScreen />
                },
                {
                    path: "login",
                    element: <LoginScreen />
                },
                /*
                {
                    path: "register",
                    element: <RegisterScreen />
                },
                {
                    path: "about",
                    element: <AboutScreen />
                }
                */
            ]
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