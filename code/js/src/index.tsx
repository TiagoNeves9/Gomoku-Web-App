import * as React from "react";
import { createRoot } from "react-dom/client";
import App from './Router'
import { CookiesProvider } from "react-cookie";


const root = createRoot(document.getElementById("container"));

// render the app with CookiesProvider
root.render(
  <CookiesProvider>
    <App />
  </CookiesProvider>
);