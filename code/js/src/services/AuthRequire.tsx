import * as React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { AuthInContextCookie, useCurrentUser } from "./Auth";


export function RequireAuth({
  children,
}: {
  children: React.ReactNode;
}): React.ReactElement {
    const {loggedInState} = React.useContext(AuthInContextCookie)
    const auth = useCurrentUser();
    const location = useLocation();

    if (loggedInState.auth) {
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