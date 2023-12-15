import { getData, postData } from "./FetchData";


export const GameService = {
    getGame: async function (gameId): Promise<any> {
        const url = `/api/games/${gameId}`;
        const response = await getData(url);
        const gameProperties = response.properties;
        //console.log(gameProperties)
        return gameProperties;
    },

    play: async function (id, playSettings): Promise<any> {
        const url = `/api/games/${id}`;
        let response = await postData(url, playSettings);

        const gameProperties = response.properties
        //console.log(gameProperties)
        return gameProperties;
    },

    getGames: function (): Promise<any> {
        const url = `/api/games`;
        return getData(url)
            .then((response) => {
                const games = JSON.parse(response)
                return { value: games }
            })
            .catch((error) => {
                return { error: error, value: null }
            })
    }
}