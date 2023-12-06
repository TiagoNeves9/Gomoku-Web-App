import { getData, postData } from "./FetchData";


  interface Response {
    error?: Error;
    value?: any;
  }

  interface GameRequestId {
    gameRequestId: String;
  }

 
export const LobbyService = {
    joinLobby: function (lobbySettings): Promise<Response> {
      return postData("api/games/start", lobbySettings).then((response) => {
        const contentStr = response.json();
        if (contentStr.class && contentStr.class.includes("Lobby")) {
            const id = contentStr.properties.lobbyId;
            let lobbyResponse = JSON.parse(id) as GameRequestId;
            return { value: lobbyResponse };
        }else if (contentStr.class && contentStr.class.includes("Game")) {
            const id = contentStr.properties.id;
            let gameResponse = JSON.parse(id) as GameRequestId;
            return { value: gameResponse };
          }

          //TOAST: ERROR INVALID FORMAT RESPONSE 
      });
    },


    checkgameCreated: async function (requestId): Promise<String> {
      const url = `api/lobbies/${requestId}`;
      const response = await getData(url);
      const contentStr = response.json();
      console.log(contentStr);

      const waitMessage = contentStr.properties?.waitMessage;

      if (waitMessage) return waitMessage;

      //TOAST: ERROR INVALID FORMAT RESPONSE

    },

    //do we need a reply to the user here? or do we just change to another "page" ?
    leaveLobby: function (): Promise<Response> {
      return postData("api/lobbies/leave").then((response) => {
        if (response.statusCode == 200) {
          return {};
        }
      });
    },
};