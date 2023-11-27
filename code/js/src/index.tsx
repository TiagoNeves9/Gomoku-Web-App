import * as React from "react";
import { CookiesProvider } from "react-cookie";
import { createRoot } from "react-dom/client";
import  App  from './Router'

//import "./index.css";

const root = createRoot(document.getElementById("the-div")); //index.html div id might be different

root.render(
    <App />
);

