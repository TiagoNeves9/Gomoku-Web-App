import React, { useState, ChangeEvent, useContext } from "react";
import { makeStyles } from "@material-ui/core";
import { useNavigate, useLocation, Navigate } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
import { useCurrentUser, useSetUser } from "../services/Auth";
import { useCookies } from "react-cookie";


const useStyles = makeStyles(
    (theme) => ({
        formControl: {
            marginRight: theme.spacing(1)
        },
        labelSpacing: {
            marginLeft: theme.spacing(1)
        }
    })
);

async function login(
    username: string, password: string
): Promise<{ properties: { username: string; id: string; token: string } }> {
    return await _fetch(
        "api/users/login", "POST", { name: username, password: password }
    );
}

function CallLoginScreen() {
    const currentUser = useCurrentUser();
    const setUser = useSetUser();

    const [inputs, setInputs] =
        useState({ username: '', password: '' });
    const [redirect, setRedirect] = useState(false);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState(undefined)
    const [cookies, setCookie] = useCookies(["Token"]);
    const location = useLocation();
    const navigate = useNavigate();

    if (redirect)
        return <Navigate
            to={location.state?.source?.pathname || "/home"} replace={true}
        />

    async function acceptChange(e: ChangeEvent<HTMLInputElement>) {
        const name = e.currentTarget.name;
        setInputs({ ...inputs, [name]: e.currentTarget.value });
    }

    async function acceptSubmit(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault();
        setSubmitting(true);
        try {
            const resp = await login(inputs.username, inputs.password);
            console.log(resp);
            if (resp)   {
                console.log(resp.properties);
                const user = {
                    username: resp.properties.username,
                    id: resp.properties.id,
                    token: resp.properties.token
                };
                setCookie("Token", resp.properties.token, 
                {
                    path: '/'
                });
                setUser(user);
                setRedirect(true);
            } else setError(<p>Login failed. Please check your credentials.</p>);
        } catch (error) {
            setError(<p>An error occurred during login. </p>);
        } finally {
            setSubmitting(false);
        }
    }

    return <>
            {!submitting ?
                <form
                    onSubmit={acceptSubmit}
                    style={{
                        display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40
                    }}
                >
                    <fieldset disabled={submitting}>
                        <h1 style={{ textAlign: 'center' }}>Login</h1>
                        <div>
                            <label htmlFor="username">Username</label>
                            <input
                                id="username" type="text" name="username"
                                value={inputs.username} onChange={acceptChange}
                            />
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input
                                id="password" type="password" name="password"
                                value={inputs.password} onChange={acceptChange}
                            />
                        </div>
                        <div>
                            <button
                                type="submit"
                                disabled={!inputs.username || !inputs.password}
                            > Login
                            </button> <br />
                            <a>Don't have an account? </a>
                            <a href="/register">Sign up</a>
                        </div>
                    </fieldset>
                    {error}
                </form> :
                null
            }
    </>
}

export const LoginScreen = () => {
    return (
        <div>
            <CallLoginScreen />
        </div>
    );
}
