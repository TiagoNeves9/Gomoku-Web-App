import React, { useState, ChangeEvent } from "react";
import { useNavigate } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
import { useSetUser } from "../services/Auth";
import { useCookies } from "react-cookie";


async function signup(
    username: string, password: string
): Promise<{ properties: { username: string; id: string; token: string } }> {
    return await _fetch(
        "api/users/signup", "POST", { name: username, password: password }
    );
}

function CallRegisterScreen() {
    const setUser = useSetUser();
    const navigate = useNavigate();

    const [inputs, setInputs] =
        useState({ username: '', password: '', confirmPassword: '' });
    const [error, setError] = useState(undefined)
    const [cookies, setCookie] = useCookies(["Token"]);
    const [submitting, setSubmitting] = useState(false);

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
                if (
                    resp && resp.properties.username &&
                    resp.properties.id && resp.properties.token
                ) {
                    const user = {
                        username: resp.properties.username,
                        id: resp.properties.id,
                        token: resp.properties.token
                    };
                    setCookie(
                        "Token", resp.properties.token,
                        { path: '/' });
                    setUser(user);
                    navigate("/home");
                } else setError(<p>
                    Sign up failed. Please check your credentials,
                    try a different username.
                </p>);
            } catch (error) {
                setError(<p>An error occurred during sign up.</p>);
            } finally {
                setSubmitting(false);
            }
        }
    }

    return <>
        {!submitting ?
            <form
                onSubmit={acceptSubmit}
                style={{
                    display: 'flex', flexDirection: 'column',
                    alignItems: 'center', padding: 40
                }}
            >
                <fieldset disabled={submitting}>
                    <h1 style={{ textAlign: 'center' }}>Sign up</h1>
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
            </form> : null
        }
    </>
}

export const RegisterScreen = () => {
    return (<div><CallRegisterScreen /></div>);
}