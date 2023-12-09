import { getData, reqData } from "./FetchData";


interface Response {
  error?: Error;
  value?: any;
}

interface GameRequestId {
  gameRequestId: String;
}


export const LobbyService = {
  joinLobby: async function (lobbySettings, bearerToken) {
    try {
      const response =
        await reqData("api/games/start", "POST", lobbySettings, bearerToken);

      if (!response.ok)
        throw new Error('Unauthorized: You do not have permission to do this.');

      const contentStr = await response.json();
      if (contentStr.class && contentStr.class === "Lobby") {
        const id = contentStr.properties.lobbyId;
        let lobbyResponse = JSON.parse(id) as GameRequestId;
        return { value: lobbyResponse };
      } else if (contentStr.class && contentStr.class === "Game") {
        const id = contentStr.properties.id;
        let gameResponse = JSON.parse(id) as GameRequestId;
        return { value: gameResponse };
      }
      console.error('Invalid response from backend:', contentStr);
      throw new Error('Unexpected response from server.');
    } catch (error) {
      console.error('Error joining lobby:', error);
      throw error;
    }
  },

  checkGameCreated: async function (requestId): Promise<String> {
    const url = `api/lobbies/${requestId}`;
    const response = await getData(url);
    const contentStr = response.json();
    console.log(contentStr);

    const waitMessage = contentStr.properties?.waitMessage;
    if (waitMessage) return waitMessage;

    //TOAST: ERROR INVALID FORMAT RESPONSE
  },

  leaveLobby: async function (bearerToken) {
    try {
      const response =
        await reqData("api/lobbies/leave", "DELETE", {}, bearerToken);
      if (response.ok) return {}; // Return an empty object for success
      else {
        console.error("Failed to leave lobby. Status:", response.status);
        throw new Error("Failed to leave lobby");
      }
    } catch (error) {
      console.error("Error leaving lobby:", error);
      throw error;
    }
  }
};