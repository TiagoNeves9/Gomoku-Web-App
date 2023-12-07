import React, { useState } from "react";
import { Link, redirect, useNavigate } from "react-router-dom";
import { useFetch } from "../custom-hooks/useFetch";


export const PlayScreen = () => {
    const navigate = useNavigate();

    const { data: content, loading, error } = useFetch<{
        properties: {
            lobbyList: {
                lobbyId: string;
                hostUserId: string;
                boardDim: number;
                opening: string;
                variant: string;
            }[]
        }
    }>({ uri: "api/lobbies" });

    const handleCreateLobby = () => {
        navigate("/lobby");
    }

    const [selectedLobby, setSelectedLobby] = useState<string | null>(null);
    const handleJoinLobby = (lobbyId: string) => {
        setSelectedLobby(lobbyId);
        // Perform the action to join the lobby here
        // Example: Redirect to the game page
    };

    if (loading) return <div>Loading...</div>;

    if (error || !content || !content.properties || !content.properties.lobbyList)
        return <div>Error fetching data...</div>;

    const { lobbyList } = content.properties;
    if (lobbyList.length === 0) {
        return (
            <div>
                <Link to="/home">Return</Link>
                <h1>Play</h1>
                <p>No lobbies are available; you can create yours.</p>
            </div>
        );
    }

    return (
        <div>
            <Link to="/home">Return</Link>
            <ul style={{ listStyleType: 'none', paddingLeft: 100 }}>
                <h1>Play</h1>
            </ul>
            <ul style={{ listStyleType: 'none', paddingLeft: 10 }}>
                <p>You can create your own lobby: <>    </>
                    <button onClick={
                        () => handleCreateLobby()}> Create lobby
                    </button>
                </p>
            </ul>
            <ul style={{ listStyleType: 'none', paddingLeft: 10 }}>
                <p>Available lobbies:</p>
            </ul>
            <ul style={{ listStyleType: 'none', paddingLeft: 25 }}>
                {lobbyList.map((lobby, index) => (
                    <li key={index} style={{ marginBottom: '10px' }}>
                        <span
                            style={{ display: 'inline-block', width: '500px' }}>Lobby ID: {lobby.lobbyId}
                        </span> <br />
                        <span
                            style={{ display: 'inline-block', width: '500px' }}>Host ID: {lobby.hostUserId}
                        </span> <br />
                        <span
                            style={{ display: 'inline-block' }}>Board dimension: {lobby.boardDim} x {lobby.boardDim};
                        </span> <> </>
                        <span
                            style={{ display: 'inline-block' }}>Opening: {lobby.opening};
                        </span> <> </>
                        <span
                            style={{ display: 'inline-block' }}>Variant: {lobby.variant}.
                        </span> <br />
                        <button
                            onClick={
                                () => handleJoinLobby(lobby.lobbyId)} style={{ marginTop: '10px', marginLeft: '100px' }
                                }> Join
                        </button> <br />
                        <br />
                    </li>
                ))}
            </ul>
            {selectedLobby && <p>Joining lobby '{selectedLobby}'...</p>}
        </div>
    );
};