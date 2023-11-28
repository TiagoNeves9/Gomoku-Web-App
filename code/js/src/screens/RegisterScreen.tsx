import React, { useState, ChangeEvent } from "react";
import { Box, Button, FormControl, InputLabel, OutlinedInput, Typography, makeStyles } from "@material-ui/core";
import { useNavigate, Link } from "react-router-dom";


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

function CallRegisterScreen() {
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
            <Link to="/"> Return </Link> <br /> <br />
            <Typography>Register</Typography>
            <CallRegisterScreen />
        </div>
    );
}