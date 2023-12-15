import React, { createContext, useContext, useEffect, useState } from "react";
import { User } from "../domain/Users";
import { _fetch } from "../custom-hooks/useFetch";


export const tokenCookie = 'cookie'
export const usernameCookie = 'cookie-name'
export const idPlayerCookie = 'cookie-id'

type UserType = {
  user: User | null;
  setUser?: (user: User | null) => void;
};

export const AuthContext =
  createContext<UserType>({
    user: undefined,
    setUser: () => { }
  });

export function AuthContainer({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState(undefined);

  const userContent = useFetch("/api/user");
  if (!userContent) return <div>Fetching user....</div>;
  if (userContent && user == null && userContent != "401") {
    try {
      const contentStr = JSON.stringify(userContent, null, 2);
      const sub =
        "{\n" +
        contentStr
          .substring(
            contentStr.indexOf('"username":'), contentStr.indexOf('"links"')
          )
          .trim();

      const final = sub.substring(0, sub.length - 1);
      let objUser = JSON.parse(final) as User;
      setUser(objUser);
    } catch (error) { }
  }

  return (
    <AuthContext.Provider value={{ user: user, setUser: setUser }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useCurrentUser() {
  return useContext(AuthContext).user;
}

export function useSetUser() {
  return useContext(AuthContext).setUser;
}

function useFetch(url: string, refreshFlag?: Boolean): string | undefined {
  const [content, setContent] = useState(undefined);

  useEffect(() => {
    let cancelled = false;
    async function doFetch() {
      try {
        const resp = await fetch(url);
        //console.log(`resp is = ${resp}`);
        if (resp.status >= 400 && resp.status < 500) throw resp.status;
        const body = await resp.json();
        //console.log(`resp is = ${body}`);
        if (!cancelled) setContent(body);
      } catch (error) {
        setContent(error);
      }
    }
    setContent(undefined);
    doFetch();
    return () => { cancelled = true; };
  }, [url, refreshFlag]);

  return content;
}