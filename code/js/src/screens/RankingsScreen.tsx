import React from "react";
import { useFetch } from "../custom-hooks/useFetch";


export const RankingsScreen = () => {
    const { data: content, loading, error } = useFetch<{
        properties: { rankingList: { user: string; score: number; ngames: number }[] }
    }>({ uri: "api/rankings" });

    if (loading) return <div>Loading...</div>;

    if (error || !content || !content.properties || !content.properties.rankingList)
        return <div>Error fetching data...</div>;

    const { rankingList } = content.properties;

    return (
        <div id="rankings">
            <h1 className="h1">Rankings</h1>
            <ul style={{ listStyleType: 'none', paddingBottom: 200 }}>
                {rankingList.map((user, index) => (
                    <li key={index} style={{ marginBottom: '10px' }} className="ranking">
                        <span style={{ display: 'inline-block', width: '100px' }}>
                            {user.user}
                        </span>
                        <span style={{ display: 'inline-block', width: '100px', textAlign: 'right' }}>
                            {user.score} points
                        </span>
                        <span style={{ display: 'inline-block', width: '150px', textAlign: 'right' }}>
                            {user.ngames} games played
                        </span>
                    </li>
                ))}
            </ul>
        </div>
    );
};