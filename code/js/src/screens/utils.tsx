import React, { useContext } from 'react'
import { Link, useLocation } from "react-router-dom";
import { useCurrentUser } from '../services/Auth';




function NavBar() {

    const location = useLocation()
    const currentUser = useCurrentUser();

    return (
            <div>
                <ul style={{
                    listStyleType: 'none', margin: 0, padding: 0,
                    overflow: 'hidden', backgroundColor: currentUser ? '#3e73b6' : '#a36615'
                }}>
                    {currentUser
                    ?
                    <>
                    <li style={{ float: 'left' }}>
                        <Link to="/home" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> Home
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/rankings" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> Rankings
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/authors" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> Authors
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/about" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> About
                        </Link>
                    </li>
                    <li style={{ float: 'left' }}>
                        <Link to="/profile" className='active'
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> My Profile
                        </Link>
                    </li>
                    <li style={{ float: 'right' }}>
                        <Link to="/logout" className="active"
                            style={{
                                display: 'block', color: 'white', textAlign: 'center',
                                padding: '14px 16px', textDecoration: 'none'
                            }}> Logout
                        </Link>
                    </li>
                </>
                    :
                    <>
            <li style={{ float: 'left' }}>
                <Link to="/" className="active"
                    style={{
                        display: 'block', color: 'white', textAlign: 'center',
                        padding: '14px 16px', textDecoration: 'none'
                    }}> Home
                </Link>
            </li>
            <li style={{ float: 'left' }}>
                <Link to="/rankings" className="active"
                    style={{
                        display: 'block', color: 'white', textAlign: 'center',
                        padding: '14px 16px', textDecoration: 'none'
                    }}> Rankings
                </Link>
            </li>
            <li style={{ float: 'left' }}>
                <Link to="/authors" className="active"
                    style={{
                        display: 'block', color: 'white', textAlign: 'center',
                        padding: '14px 16px', textDecoration: 'none'
                    }}> Authors
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
                <Link to="/register" className="active"
                    style={{
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