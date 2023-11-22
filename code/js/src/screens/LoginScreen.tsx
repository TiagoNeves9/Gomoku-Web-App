import React, { useState, ChangeEvent } from "react";
import { Box, Button, FormControl, Input, InputLabel, OutlinedInput, TextField, Typography } from "@material-ui/core";
import { useNavigate } from "react-router-dom";


function CallLoginScreen() {
    console.log("CallLoginScreen"); //to comment

    const [inputs, setInputs] = useState({ username: '', password: '' });
    const [submitting, setSubmitting] = useState(false);
    const navigate = useNavigate();

    async function acceptChange(e: ChangeEvent<HTMLInputElement>) {
        const name = e.currentTarget.name;
        setInputs({ ...inputs, [name]: e.currentTarget.value });
    }

    async function acceptSubmit(logIn: boolean) {
        if (!submitting) {
            setSubmitting(true);
            const { username, password } = inputs;
            //TODO: send to server
            navigate('/home');
        }
    }

    return <>
        <Box component="form">
            <FormControl>
                <InputLabel htmlFor="username">Username</InputLabel>
                <OutlinedInput
                    id="username" name="username" label="Username"
                    value={inputs.username} onChange={acceptChange}
                />
                <InputLabel htmlFor="password">Password</InputLabel>
                <OutlinedInput
                    id="password" name="password" type="password" label="Password"
                    value={inputs.password} onChange={acceptChange}
                />
            </FormControl>
            <Button variant="contained" disabled={submitting} onClick={() => acceptSubmit(true)}>Log in</Button>
        </Box>
    </>
}

export function LoginScreen() {
    return <>
        <Typography>Login</Typography>
        <CallLoginScreen />
    </>;
}