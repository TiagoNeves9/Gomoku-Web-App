import React, {createContext, useContext, useEffect, useState } from "react";
import { User } from "../domain/Users";
import { _fetch } from "../custom-hooks/useFetch";
import { fetchGetSession } from "./Coockies";

export const tokenCookie = 'cookie'
export const usernameCookie = 'cookie-name'
export const idPlayerCookie = 'cookie-id'

type UserType = {
    user: User | null;
    setUser? : (user: User | null) => void;
};

export const AuthContext = React.createContext<UserType>({  
    user: undefined,
    setUser: () => {} 
});

export type SessionState = {
    state: boolean,
    auth: boolean
}



export const AuthInContextCookie = createContext({
    loggedInState: { state: false, auth: false, token: undefined, id:undefined },
});

export function AuthContainer({ children }: { children: React.ReactNode }) {
    const [auth, setAuth] = useState({state: false, auth: false, token: undefined, id: undefined})

    useEffect(() => {
       fetchGetSession(
        (token: string, id: string) => {
            if(token) setAuth({state: true, auth: true, token: token, id: id})
            else setAuth({state: false, auth: false, token: undefined, id: id})
        }
       )
       return () => {}
    }, [setAuth]
    )
    return (
        <AuthInContextCookie.Provider value={{ loggedInState: auth}}>
            {children}
        </AuthInContextCookie.Provider>
    );
    
}

export function useCurrentUser() {
    return useContext(AuthContext).user;
}

export function useSetUser() {
    return useContext(AuthContext).setUser;
}
