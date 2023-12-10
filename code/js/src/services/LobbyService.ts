import { deleteData, getData, postData } from "./FetchData";


interface Response {
  error?: Error;
  value?: any;
}

export const LobbyService = {
  startLobby: function (lobbySettings): Promise<Response> {
    return postData("api/games/start", lobbySettings).then((response) => {
      console.log(response)
      if (response.class && response.class.includes("Lobby")) {
        const id = response.properties.lobbyId;
        console.log(id)
        return { value: id };
      } else if (response.class && response.class.includes("Game")) {
        const id = response.properties.id;
        console.log(id)
        return { value: id };
      }

      //TOAST: ERROR INVALID FORMAT RESPONSE 
    });
  },

  joinLobby: function (lobby): Promise<Response> {
    return postData("api/lobbies/join", lobby).then((response) => {
      if (response.class && response.class.includes("Lobby")) {
        const gameId = response.properties.gameId;
        return { value: gameId };
      }

      //TOAST: ERROR INVALID FORMAT RESPONSE 
    });
  },

  checkGameCreated: async function (requestId): Promise<String> {
    const url = `api/lobbies/${requestId}`;
    const response = await getData(url);
    console.log(response);

    const waitMessage = response.properties?.waitMessage;
    if (waitMessage) return waitMessage;

    //TOAST: ERROR INVALID FORMAT RESPONSE
  },

  //do we need a reply to the user here? or do we just change to another "page" ?
  leaveLobby: function (): Promise<Response> {
    return deleteData("api/lobbies/leave").then((response) => {
      if (response.ok) return {}; // Return an empty object for success
      else {
        console.error("Failed to leave lobby. Status:", response.status);
        throw new Error("Failed to leave lobby");
      }
    });
  },
};