import * as React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useCurrentUser } from "./Auth";
import { ReactElement, ReactNode } from "react";


export function RequireAuth({
  children,
}: {
  children: ReactNode;
}): ReactElement {
    const currentUser = useCurrentUser();
    const location = useLocation();

    if (currentUser) {
        return <>{children}</>;
    } else {
        return (
        <Navigate
            to="/login"
            state={{ source: location.pathname }}
            replace={true}
        />
        );
    }
}