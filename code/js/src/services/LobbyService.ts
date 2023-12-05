import { getData, postData } from "./FetchData";

  
  interface Response {
    error?: Error;
    value?: any;
  }
  
  interface GameRequestId {
    gameRequestId: String;
  }
  
  //todo refactor responses <- changes were made to backend
export const LobbyService = {
    joinLobby: function (lobbySettings): Promise<Response> {
      return postData("api/games/start", lobbySettings).then((response) => {
        const contentStr = JSON.stringify(response, null, 2);
        const sub =
          "{" +
          contentStr.substring(
            contentStr.indexOf('"id":'),
            contentStr.indexOf("}") + 1
          );
  
        let gameRequestId = JSON.parse(sub) as GameRequestId;
        return { value: gameRequestId };
      });
    },

    checkgameCreated: async function (requestId): Promise<String> {
      const url = `api/lobbies/${requestId}`;
      const response = await getData(url);
      const contentStr = JSON.stringify(response, null, 2);
      console.log(contentStr);
      
      return contentStr;
    },

    leaveLobby: function (): Promise<Response> {
      return postData("api/lobbies/leave").then((response) => {
        if (response.statusCode == 200) {
          return {};
        }
      });
    },
};
  