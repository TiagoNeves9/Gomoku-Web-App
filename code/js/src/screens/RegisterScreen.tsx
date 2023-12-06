import React, { useState, ChangeEvent, useContext } from "react";
import { makeStyles } from "@material-ui/core";
import { useNavigate } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
import { AuthContext } from "../services/Auth";
import NavBar, { Layout } from "./utils";


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

async function signup(
    username: string, password: string
): Promise<{ properties: { username: string; id: string; token: string } }> {
    return await _fetch(
        "api/users/signup", "POST", { name: username, password: password }
    );
}

function CallRegisterScreen() {
    const currentUser = useContext(AuthContext);
    const [error, setError] = useState(undefined)
    //const classes = useStyles();
    const [inputs, setInputs] =
        useState({ username: '', password: '', confirmPassword: '' });
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();

    async function acceptChange(e: ChangeEvent<HTMLInputElement>) {
        const name = e.currentTarget.name;
        setInputs({ ...inputs, [name]: e.currentTarget.value });
    }

    async function acceptSubmit(ev: React.FormEvent<HTMLFormElement>) {
        ev.preventDefault();
        setSubmitting(true);

        if (!submitting) {
            setSubmitting(true);
            const { username, password, confirmPassword } = inputs;

            if (password !== confirmPassword) {
                alert("Passwords do not match!");
                setSubmitting(false); // Release the button
                return;
            }
            try {
                const resp = await signup(username, password);
                if (resp && resp.properties.username && resp.properties.id && resp.properties.token) {
                    currentUser.user = {
                        username: resp.properties.username,
                        id: resp.properties.id,
                        token: resp.properties.token
                    };
                    console.log(currentUser.user);
                    navigate("/home");
                } else
                    setError(<p>Sign up failed. Please check your credentials, try a different username.</p>);
            } catch (error) {
                setError(<p>An error occurred during sign up.</p>);
            } finally {
                setSubmitting(false);
            }
        }
    }

    return <>
        <Layout>
            <NavBar />
            {!submitting ?
                <form
                    onSubmit={acceptSubmit}
                    style={{
                        display: 'flex', flexDirection: 'column', alignItems: 'center', padding: 40
                    }}
                >
                    <fieldset disabled={submitting}>
                        <h1>Sign up</h1>
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
                            <label htmlFor="confirmPassword">Confirm password</label>
                            <input
                                id="confirmPassword" type="password" name="confirmPassword"
                                value={inputs.confirmPassword} onChange={acceptChange}
                            />
                        </div>
                        <div>
                            <button
                                type="submit"
                                disabled={!inputs.username || inputs.password.length < 8}
                            > Sign up
                            </button> <br />
                            <a>Already have an account? </a>
                            <a href="/login">Login</a>
                        </div>
                    </fieldset>
                    {error}
                </form> :
                null
            }
        </Layout>
    </>
}

export const RegisterScreen = () => {
    return (
        <div>
            <CallRegisterScreen />
        </div>
    );
}