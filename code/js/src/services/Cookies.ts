import { _fetch } from "../custom-hooks/useFetch";


export type cookieParams = {
    name: string,
    value: string,
    expire: string | undefined,
    path: string | undefined,
};

export function doCookie(cookieParams: cookieParams) {
    document.cookie =
        `${cookieParams.name}=${cookieParams.value}; 
        expire=${cookieParams.expire ? cookieParams.expire : Date.now() + 24 * 60 * 60 * 1000}; 
        path=${cookieParams.path ? cookieParams.path : '/'}`;
}

export function deleteCookie(cookieName: string) {
    document.cookie = `${cookieName}=;expire=${Date.now()}`;
}

export async function fetchGetSession(onSuccess: (token: string, id: string) => void) {
    try {
        const resp = await fetch('/api/users/cookie', { credentials: 'include' })
        const response = await resp.json()
        const token = response.find(item => item.name === "token")
        const id = response.find(it => it.name === "id")
        onSuccess(token.value, id.value)
    } catch (error) {
        console.error(error)
    }
}