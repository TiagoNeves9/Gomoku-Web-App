## Gomoku API Documentation

------------------------------------------------------------------------------------------

### User

#### Login - Obtaining a new token for the user attempting to login

<details>
 <summary><code>POST</code> <code><b>/users/login</b></code> <code>(obtain/create a new token)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	 |
|-------------|--------------------|---------------|
| 200       	 | application/json 	 | Response #1 	 |
| 401       	 | application/json 	 | Response #2 	 |                                           

> ###### Response 1 Example:
 
```
{
    "class": [
        "user login"
    ],
    "properties": {
        "username": "afonso1",
        "id": "41298e21-3b42-4ae6-9b2f-c5ac5252e908",
        "token": "9b726487-deb0-4098-b55f-fbb47baed0b8"
    },
    "links": [
        {
            "rel": [
                "home"
            ],
            "href": "/"
        },
        {
            "rel": [
                "lobby"
            ],
            "href": "/games/start"
        }
    ],
    "entities": [],
    "actions": []
}
```
> ###### Response 2 Example:
```
{
    "class": [],
    "properties": {
        "statusCode": 401,
        "msg": "User or Password are incorrect!"
    },
    "links": [],
    "entities": [],
    "actions": []
}
```

</details>

#### Signup - Register a new user

<details>
 <summary><code>POST</code> <code><b>/users/signup</b></code> <code>(register a new user info)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	         |
|-------------|--------------------|-----------------------|
| 201       	 | application/json 	 | Similar to login 	    |
| 400      	  | application/json 	 | Error creating user 	 |                                           
| 409      	  | application/json 	 | Response #3 	         |

> ###### Response 3 Example:
```
{
    "class": [],
    "properties": {
        "statusCode": 409,
        "msg": "Username afonso1 already in use!"
    },
    "links": [],
    "entities": [],
    "actions": []
}
```

</details>

#### User - Obtain user info

<details>
 <summary><code>Get</code> <code><b>/user</b></code> <code>(Obtains user info with the token provided from the request)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	               |
|-------------|--------------------|-----------------------------|
| 200       	 | application/json 	 | Similar to login response 	 |
| 401      	  | application/json 	 | User not authenticated 	    |                                           
| 404      	  | application/json 	 | User not Found Response 	   |

</details>

------------------------------------------------------------------------------------------

### Statistic

#### Rankings - Obtain the game leaderboard

<details>
 <summary><code>Get</code> <code><b>/user</b></code> <code>(Obtains a list of the player's scores)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	 |
|-------------|--------------------|---------------|
| 200       	 | application/json 	 | Response #4	  |

> ###### Response 4 Example:
```
{
    "class": [
        "Rankings"
    ],
    "properties": {
        "rankingList": [
            {
                "user": "teste123",
                "score": 600,
                "ngames": 7
            },
            {
                "user": "userdemo",
                "score": 400,
                "ngames": 7
            },
            {
                "user": "afonsox",
                "score": 200,
                "ngames": 5
            },
            {
                "user": "testeafonso2",
                "score": 200,
                "ngames": 2
            }
        ]
    },
    "links": [],
    "entities": [],
    "actions": []
}
```

</details>

------------------------------------------------------------------------------------------

### Game and Lobby

#### Start - Join or create a lobby

<details>
 <summary><code>POST</code> <code><b>/games/start</b></code> <code>(Create a new game or join an existing lobby with same settings)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	              |
|-------------|--------------------|----------------------------|
| 201       	 | application/json 	 | Response #5 	              |
| 401      	  | application/json 	 | Not authenticated 	        |                                           
| 405      	  | application/json 	 | Attempt to join own lobby	 |

> ###### Response 5 Example:
> Either a new lobby is created or a new game is created (there was already an existing lobby, another player waiting for someone to play with.)
```
If a game is created: (Join a lobby.)
{
    "class": [
        "Game"
    ],
    "properties": {
        "id": "d5159b65-855d-4e59-a073-404f66c5e121",
        "userB": {
            "userId": "a469181a-2b33-4783-a19d-83b39422470b",
            "username": "afonso2",
            "encodedPassword": "$2a$10$6nckusCulCdDon4ZrW3eWunU0g0kFCayMh50yRqGBgaVkx.1cDbJW"
        },
        "userW": {
            "userId": "41298e21-3b42-4ae6-9b2f-c5ac5252e908",
            "username": "afonso1",
            "encodedPassword": "$2a$10$kXPHosbEZbwY.zSxn7YXfOOb1TyacMp/8SW7yXB8Mox0EmzZ/42cm"
        },
        "turn": "afonso2",
        "rules": {
            "boardDim": 19,
            "opening": "FREESTYLE",
            "variant": "FREESTYLE"
        },
        "boardCells": {},
        "boardState": "RUNNING"
    },
    "links": [],
    "entities": [],
    "actions": [
        {
            "name": "create game and delete lobby",
            "href": "/games/d5159b65-855d-4e59-a073-404f66c5e121",
            "method": "POST",
            "type": "application/x-www-form-urlencoded",
            "fields": [
                {
                    "name": "create game and delete lobby",
                    "type": "text",
                    "value": null
                }
            ]
        }
    ]
}

If a lobby is created:
{
    "class": [
        "Lobby"
    ],
    "properties": {
        "lobbyId": "d5159b65-855d-4e59-a073-404f66c5e121",
        "hostUserId": "a469181a-2b33-4783-a19d-83b39422470b",
        "boardDim": 19,
        "opening": "FREESTYLE",
        "variant": "FREESTYLE"
    },
    "links": [],
    "entities": [],
    "actions": [
        {
            "name": "create lobby",
            "href": "/lobbies/join",
            "method": "POST",
            "type": "application/x-www-form-urlencoded",
            "fields": [
                {
                    "name": "create lobby",
                    "type": "text",
                    "value": null
                }
            ]
        }
    ]
}
```

</details>

#### Leave a lobby

<details>
 <summary><code>DELETE</code> <code><b>/lobbies/leave</b></code> <code>(Leaves the current lobby the player is in)</code></summary>

##### Parameters

> None

##### Responses

| http code 	 | content-type     	 | response    	                              |
|-------------|--------------------|--------------------------------------------|
| 200       	 | application/json 	 | Left Lobby 	                               |
| 400         | application/json   | Error during db operation                  |
| 401      	  | application/json 	 | Not authenticated 	                        |                                           
| 404      	  | application/json 	 | Lobby not found (user wasn't in a lobby) 	 |

> Responses are pretty simple with a simple message about the success of the operation

</details>

#### Get game with given ID

<details>
 <summary><code>GET</code> <code><b>/games/{id}</b></code> <code>(Obtain game info with ID)</code></summary>

##### Parameters

| name     |  type      | data type | description     |
|----------|------------|-----------|-----------------|
| `gameId` |  required  | UUID      | Game identifier |

##### Responses

| http code 	 | content-type     	 | response    	               |
|-------------|--------------------|-----------------------------|
| 200       	 | application/json 	 | Response #6 	               |
| 401      	  | application/json 	 | Not authenticated 	         |                                           
| 404      	  | application/json 	 | Game not found for gameId 	 |

> Response 6 Example:
```
{
    "class": [
        "Game"
    ],
    "properties": {
        "id": "d5159b65-855d-4e59-a073-404f66c5e121",
        "userB": {
            "userId": "a469181a-2b33-4783-a19d-83b39422470b",
            "username": "afonso2",
            "encodedPassword": "$2a$10$6nckusCulCdDon4ZrW3eWunU0g0kFCayMh50yRqGBgaVkx.1cDbJW"
        },
        "userW": {
            "userId": "41298e21-3b42-4ae6-9b2f-c5ac5252e908",
            "username": "afonso1",
            "encodedPassword": "$2a$10$kXPHosbEZbwY.zSxn7YXfOOb1TyacMp/8SW7yXB8Mox0EmzZ/42cm"
        },
        "turn": "afonso2",
        "rules": {
            "boardDim": 19,
            "opening": "FREESTYLE",
            "variant": "FREESTYLE"
        },
        "boardCells": {},
        "boardState": "RUNNING"
    },
    "links": [],
    "entities": [],
    "actions": [
        {
            "name": "create game and delete lobby",
            "href": "/games/d5159b65-855d-4e59-a073-404f66c5e121",
            "method": "POST",
            "type": "application/x-www-form-urlencoded",
            "fields": [
                {
                    "name": "create game and delete lobby",
                    "type": "text",
                    "value": null
                }
            ]
        }
    ]
}
```

</details>
