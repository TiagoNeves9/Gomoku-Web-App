import React, { useState, ChangeEvent, useContext } from "react";
import { Box, Button, FormControl, InputLabel, OutlinedInput, Typography, makeStyles }
    from "@material-ui/core";
import { useNavigate, Link, useLocation, Navigate } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
import { User } from "../domain/Users";
import NavBar, { Layout } from "./utils";
import { AuthContext } from "../services/Auth";




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
    return await _fetch("api/users/login", "POST", { name: username, password: password });
}

function CallLoginScreen() {
    const currentUser = useContext(AuthContext);
    const classes = useStyles();
    const [inputs, setInputs] = useState({ username: '', password: '' });
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState(undefined)
    const [redirect, setRedirect] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();

    if(redirect) {
        return <Navigate to={location.state?.source?.pathname || "/userhome"} replace={true}/>
    }

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
            if (resp) {
                
                console.log(resp.properties)
                currentUser.user = { username: resp.properties.username, id: resp.properties.id, token: resp.properties.token };
                console.log(currentUser.user)
                navigate("/home");
            } else {
                setError(<p>Login failed. Please check your credentials.</p>);
            }
        } catch (error) {
            setError(<p>An error occurred during login.</p>);
        } finally {
            setSubmitting(false);
        }
    }

    return <>
        <Layout>
            <NavBar/>
            {!submitting?
                <form onSubmit={acceptSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40 }}>
                    <fieldset disabled={submitting}>
                        <h1 style ={{textAlign: 'center'}}>Login</h1>
                        <div>
                            <label htmlFor="username">Username</label>
                            <input id="username" type="text" name="username" value={inputs.username} onChange={acceptChange} />
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input id="password" type="password" name="password" value={inputs.password} onChange={acceptChange} />
                        </div>
                        <div>
                            <p></p>
                            <div style={{ display: 'flex', justifyContent: 'center', width: '100%' }}>
                            <button  type="submit" disabled={!inputs.username || !inputs.password}>Login</button>
                            </div>
                            <p></p>
                            <a>Don't have an account?</a> <a href="/players">Signup</a>
                        </div>
                    </fieldset>
                    {error}
                </form>:
                null
            }
        </Layout>
    </>
}


export const LoginScreen = () => {
    return (
        <div>
            <Link to="/"> Return Home </Link> <br /> <br />
            <Typography>Login</Typography>
            <CallLoginScreen />
        </div>
    );
}


