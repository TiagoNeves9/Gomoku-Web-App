import React from "react";
import { createRoot } from "react-dom/client";
import { useCounter } from "./useCounter";

function CallCounter() {
    const [counter, inc, dec] = useCounter(0);

    return (
        <div>
            <p>Counter: {counter}</p>
            <button onClick={inc}>+</button>
            <button onClick={dec}>-</button>
        </div>
    );
}

export function demo() {
    const container = document.getElementById("container")
    if (container !== null) {
        const root = createRoot(container)
        root.render(<CallCounter />)
    }
    else console.log("container not found")
}