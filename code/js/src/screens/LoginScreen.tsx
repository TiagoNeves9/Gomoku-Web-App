import React, { useState, ChangeEvent } from "react";
import { Box, Button, FormControl, InputLabel, OutlinedInput, Typography, makeStyles } from "@material-ui/core";
import { useNavigate } from "react-router-dom";


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

function CallLoginScreen() {
    const classes = useStyles();
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
                    id="password" name="password" type="password" label="Password"
                    value={inputs.password} onChange={acceptChange}
                />
            </FormControl>
            <Button variant="contained" disabled={submitting} onClick={() => acceptSubmit(true)}>Log in</Button>
        </Box>
    </>
}

export const LoginScreen = () => {
    return (
        <div>
            <Typography>Login</Typography>
            <CallLoginScreen />
        </div>
    );
}