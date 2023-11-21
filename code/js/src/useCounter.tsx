/*
Realize a função useCounter(initial: number): [observed: number, inc: () => void, dec: () => void] 
para ser usado como hook em componentes para a biblioteca React. Este hook serve para gerir um contador, 
retornando a função um array com três elementos: o valor atual do contador, 
uma função para incrementar o contador, e uma função para decrementar o contador.
*/

import { useState } from "react";

export function useCounter(initial: number): [observed: number, inc: () => void, dec: () => void] {
    const [counter, setCounter] = useState(initial);

    const inc = () => setCounter(counter + 1);
    const dec = () => setCounter(counter - 1);

    return [counter, inc, dec];
}