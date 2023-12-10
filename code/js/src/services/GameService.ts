import { Game } from "../domain/Game";
import { getData, postData } from "./FetchData";

export const GameService = {
    getGame: async function(gameId): Promise<any> {
        const url = `/api/games/${gameId}`;
        console.log(url)
        const response = await getData(url);
        console.log(response);
        
        const gameProperties = response.properties;
        console.log("INSIDE GAME SERVICE: GAME properties -> ") 
        console.log(gameProperties)
            
        return gameProperties;
    },

    play: async function(id, playSettings): Promise<any> {
        const url = `/api/games/${id}`;
        
        
        console.log(playSettings)
        let response = await postData(url, playSettings);
        console.log(response)
        
        const gameProperties = response.properties

        return gameProperties;
    },

    getGames: function() : Promise<any> {
        const url = `/api/games`;
        return getData(url)
        .then((response) => {
            const games = JSON.parse(response)
            return {value: games}
        })
        .catch((error) => {
            return {error: error, value: null}
        })
    }

}