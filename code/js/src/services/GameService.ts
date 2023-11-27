import { Game } from "../domain/Game";

export async function getData(url) {
    console.log(url);
    
    const response = await fetch(url, {
      method: "GET", 
      headers: {
        "Content-Type": "application/json",
        
      },
    });
    return response.json();
}

async function postData(url, data = {}) {
    const response = await fetch(url, {
      method: "POST", 
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data), 
    });
    return response.json();
  }


export const GameService = {
    getGame: function(id): Promise<any> {
        const url = `/games/${id}`;
        return getData(url)
        .then((response) => {
            if (response.statusCode == 404) {
                throw new Error(response.msg);
            }
            const gameContentString = JSON.stringify(response, null, 2);
            const sub =
                "{\n" +
                gameContentString
                    .substring(
                        gameContentString.indexOf('"properties":'),
                    )
                    .trim();

            const final = sub.substring(0, sub.length - 1);
            let game = JSON.parse(final) as Game;
            return { value: game };
      })
      .catch((error) => {
        return { error: error, value: null };
      });
    },

    play: function(id, cellKey): Promise<any> {
        const url = `/games/${id}`;
        return postData(url, cellKey)
        .then((response) => {
            if (response.status < 500 && response.status >= 400)
                throw new Error(response.msg);
            else{
                //todo: change api response to return only the cells
                const cellsJson = JSON.stringify(response, null, 2);
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
        const url = `/games`;
        return getData(url)
        .then((response) => {
            const gamesJson = JSON.stringify(response, null, 2);
            const games = JSON.parse(gamesJson)
            return {value: games}
        })
        .catch((error) => {
            return {error: error, value: null}
        })
    }

}