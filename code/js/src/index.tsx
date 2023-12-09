import * as React from "react";
import { createRoot } from "react-dom/client";
import App from './Router'


const root = createRoot(document.getElementById("container"));

root.render(
    <CookiesProvider>
      <App />  
    </CookiesProvider>
);