import React from 'react'
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useCurrentUser } from '../services/Auth';
import { Button } from '@material-ui/core';
import { useCookies } from "react-cookie";




function NavBar() {

    const navigate = useNavigate();
    const currentUser = useCurrentUser();
    const [cookies, setCookie, removeCookie] = useCookies(["Token"]);
    
    const handleLogout = () => {
        removeCookie("Token", {path: '/'});
        window.location.reload();
        navigate("/");
    };

    return (
        <div>
            <ul style={{
                listStyleType: 'none', margin: 0, padding: 0,
                overflow: 'hidden', backgroundColor: currentUser ? '#3e73b6' : '#a36615'
            }}>
                {currentUser ? <>
                    <li style={{ float: 'left' }}>
                        <Link to="/home" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Home
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/rankings" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Rankings
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/authors" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Authors
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/about" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> About
                        </Link>
                    </li>
                    <li style={{ float: 'right' }}>
                        <Button onClick={handleLogout} className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Logout
                        </Button>
                    </li>
                    <li style={{ float: 'right' }}>
                        <Link to="/profile" className='active' style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> My Profile
                        </Link>
                    </li>
                </> : <>
                    <li style={{ float: 'left' }}>
                        <Link to="/" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Home
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/rankings" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Rankings
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/authors" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Authors
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/about" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> About
                        </Link>
                    </li>
                    <li style={{ float: 'right' }}>
                        <Link to="/login" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> Login
                        </Link>
                    </li>
                    <li style={{ float: 'right' }}>
                        <Link to="/register" className="active" style={{
                            display: 'block', color: 'white', textAlign: 'center',
                            padding: '14px 16px', textDecoration: 'none'
                        }}> Sign up
                        </Link>
                    </li>
                </>}
            </ul>
        </div>
    );
}

export default NavBar;
