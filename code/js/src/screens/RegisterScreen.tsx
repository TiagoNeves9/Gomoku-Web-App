import React, { useState, ChangeEvent, useContext } from "react";
import { Box, Button, FormControl, InputLabel, OutlinedInput, Typography, makeStyles }
    from "@material-ui/core";
import { useNavigate, Link } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
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

async function signup(
    username: string, password: string
): Promise<{ properties: { username: string; id: string; token: string } }> {
    return await _fetch("api/users/signup", "POST", { name: username, password: password });
}

function CallRegisterScreen() {
    const currentUser = useContext(AuthContext);
    const [error, setError] = useState(undefined)
    const classes = useStyles();
    const [inputs, setInputs] = useState({ username: '', password: '', confirmPassword: '' });
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();

    async function acceptChange(e: ChangeEvent<HTMLInputElement>) {
        const name = e.currentTarget.name;
        setInputs({ ...inputs, [name]: e.currentTarget.value });
    }

    async function acceptSubmit(register: boolean) {
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

            if (resp) {
                currentUser.user = { username: resp.properties.username, id: resp.properties.id, token: resp.properties.token };
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
    }

    return <>
        <Box component="form">
            <FormControl className={classes.formControl}>
                <InputLabel htmlFor="username" className={classes.labelSpacing}>Username</InputLabel>
                <OutlinedInput
                    id="username" name="username" label="Username"
                    value={inputs.username} onChange={acceptChange}
                />
            </FormControl>
            <FormControl className={classes.formControl}>
                <InputLabel htmlFor="password" className={classes.labelSpacing}>Password</InputLabel>
                <OutlinedInput
                    id="password" name="password"
                    type="password" label="Password"
                    value={inputs.password} onChange={acceptChange}
                />
            </FormControl>
            <FormControl className={classes.formControl}>
                <InputLabel htmlFor="confirmPassword" className={classes.labelSpacing}>
                    Confirm Password
                </InputLabel>
                <OutlinedInput
                    id="confirmPassword" name="confirmPassword"
                    type="password" label="Confirm Password"
                    value={inputs.confirmPassword} onChange={acceptChange}
                />
            </FormControl>
            <Button variant="contained" disabled={submitting} onClick={() => acceptSubmit(true)}>
                Register
            </Button>
        </Box>
    </>
}

export const RegisterScreen = () => {
    return (
        <div>
            <Link to="/"> Return Home </Link> <br /> <br />
            <Typography>Register</Typography>
            <CallRegisterScreen />
        </div>
    );
}