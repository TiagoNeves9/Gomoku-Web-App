import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { AuthContext } from "../services/Auth";
import { useFetch } from "../custom-hooks/useFetch";


export const ProfileScreen = () => {
    const currentUser = useContext(AuthContext);
    console.log(currentUser.user);

    if (!currentUser || !currentUser.user) {
        // Display a message guiding the user to log in again
        return (
            <div>
                <Link to="/home">Return</Link>
                <p>Your session has expired or you are not logged in.</p>
                <p>Please <Link to="/login">login</Link> to view this page.</p>
            </div>
        );
    }

    const userRankingUri = "api/rankings/" + currentUser.user.username;
    const { data: content, loading, error } = useFetch<{
        properties: { userRanking: { user: string; score: number; ngames: number } }
    }>({ uri: userRankingUri });

    if (loading)
        return <div>Loading...</div>;

    if (error || !content || !content.properties || !content.properties.userRanking)
        return <div>Error fetching data...</div>;

    return (
        <div>
            <Link to="/home">Return</Link>
            <h1>Profile</h1>
            <h2>Username:</h2>
            <p style={{ paddingLeft: '20px' }}>{currentUser.user.username}</p>
            <h2>Rank:</h2>
            <p style={{ paddingLeft: '20px' }}>
                Total score: {content.properties.userRanking.score} <br />
                Games played: {content.properties.userRanking.ngames}
            </p>
        </div>
    );
}
