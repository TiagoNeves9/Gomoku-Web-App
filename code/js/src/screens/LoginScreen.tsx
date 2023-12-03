import React, { useState, ChangeEvent } from "react";
import { Box, Button, FormControl, InputLabel, OutlinedInput, Typography, makeStyles }
    from "@material-ui/core";
import { useNavigate, Link } from "react-router-dom";
import { _fetch } from "../custom-hooks/useFetch";
import { User } from "../domain/Users";


type ContextType = {
    user: User | null;
    setUser?: (user: User | null) => void;
};

export const AuthContext = React.createContext<ContextType>({ user: null, setUser: () => { } });


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
    const classes = useStyles();
    const [inputs, setInputs] = useState({ username: '', password: '' });
    const [submitting, setSubmitting] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const navigate = useNavigate();

    async function acceptChange(e: ChangeEvent<HTMLInputElement>) {
        const name = e.currentTarget.name;
        setInputs({ ...inputs, [name]: e.currentTarget.value });
    }

    async function acceptSubmit(logIn: boolean) {
        if (!submitting) {
            setSubmitting(true);
            try {
                const user = await login(inputs.username, inputs.password);
                console.log(user.properties);

                console.log("user " + user);
                navigate("/userhome");
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
            <Button variant="contained" disabled={submitting} onClick={() => acceptSubmit(true)}>
                Log in
            </Button>
            <AuthContext.Provider value={{ user, setUser }}>
            </AuthContext.Provider>
        </Box>
    </>

}

export const LoginScreen = () => {
    return (
        <div>
            <Link to="/"> Return </Link> <br /> <br />
            <Typography>Login</Typography>
            <CallLoginScreen />
        </div>
    );
}


