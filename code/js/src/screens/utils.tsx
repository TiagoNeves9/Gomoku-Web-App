import React from 'react'
import { Link, useLocation } from "react-router-dom";
import { AuthContainer, AuthContext, AuthInContextCookie } from '../services/Auth';


export function Layout(props) {
    return (
        <AuthContainer>
            {props.children}
        </AuthContainer>
    )
}

function NavBar() {
    const check = React.useContext(AuthInContextCookie).loggedInState.state
    const location = useLocation()
    const currentUser = React.useContext(AuthContext)
    return (
        <AuthContainer>
            <div>
                {!check ?
                    <>
                        <ul style={{
                            listStyleType: 'none', margin: 0, padding: 0,
                            overflow: 'hidden', backgroundColor: '#a36615'
                        }}>
                            <li style={{ float: 'left' }}>
                                <Link to={"/home"} state={{ source: location.pathname }}
                                    style={{
                                        display: 'block', color: 'white', textAlign: 'center',
                                        padding: '14px 16px', textDecoration: 'none'
                                    }} replace={true}> Home
                                </Link>
                            </li>
                            {!currentUser.user ?
                                <>
                                    <li style={{ float: 'right' }}>
                                        <Link to="/register" className="active"
                                            style={{
                                                display: 'block', color: 'white', textAlign: 'center',
                                                padding: '14px 16px', textDecoration: 'none'
                                            }}> Sign up
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
                                </> :
                                <>
                                    <li style={{ float: 'right' }}>
                                        <Link to="/profile" className='active'
                                            style={{
                                                display: 'block', color: 'white', textAlign: 'center',
                                                padding: '14px 16px', textDecoration: 'none'
                                            }}> My profile
                                        </Link>
                                    </li>
                                </>
                            }
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
                                    }}> About App
                                </Link>
                            </li>
                        </ul>
                    </> :
                    <>
                        <ul style={{
                            listStyleType: 'none', margin: 0, padding: 0,
                            overflow: 'hidden', backgroundColor: '#3e73b6'
                        }}>
                            <li style={{ float: 'left' }}>
                                <Link to="/" className="active"
                                    style={{
                                        display: 'block', color: 'white', textAlign: 'center',
                                        padding: '14px 16px', textDecoration: 'none'
                                    }}> Home
                                </Link>
                            </li>
                            <li style={{ float: 'left' }}>
                                <Link to="/about_us" className="active"
                                    style={{
                                        display: 'block', color: 'white', textAlign: 'center',
                                        padding: '14px 16px', textDecoration: 'none'
                                    }}> About us
                                </Link>
                            </li>
                            <li style={{ float: 'left' }}>
                                <Link to="/ranking" className="active"
                                    style={{
                                        display: 'block', color: 'white', textAlign: 'center',
                                        padding: '14px 16px', textDecoration: 'none'
                                    }}> Ranking
                                </Link>
                            </li>
                        </ul>
                    </>
                }
            </div>
        </AuthContainer>
    );
}

export default NavBar;