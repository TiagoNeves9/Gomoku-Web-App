import { Game } from "../domain/Game";
import { getData, postData } from "./FetchData";

export const GameService = {
    getGame: function(id): Promise<any> {
        const url = `/api/games/${id}`;
        return getData(url)
        .then((response) => {
            if (response.statusCode == 404) {
                throw new Error(response.msg);
            }
            const gameProperties = response.properties;
            let game = JSON.parse(gameProperties) as Game;
            return { value: game };
      })
      .catch((error) => {
        return { error: error, value: null };
      });
    },

    play: function(id, cellKey): Promise<any> {
        const url = `/api/games/${id}`;

        return postData(url, cellKey)
        .then((response) => {
            if (response.status < 500 && response.status >= 400)
                throw new Error(response.msg);
            else{
                //todo: change api response to return only the cells?
                const cellsJson = response.properties.boardCells;
                let cells = JSON.parse(cellsJson) as Map<String, String>;
                if(response.status == 200) {
                    return {value: cells, wasPlayed: false }
                }
                else if(response.status == 201) {
                    return {value: cells, wasPlayed: true }
                }
            }
      })
      .catch((error) => {
        console.log(error);
        return { error: error, value: null };
      });
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