import React, { useEffect, useRef, useState } from "react";
import { LobbyService } from "../services/LobbyService";
import { Button, Radio, RadioGroup, FormControlLabel, FormControl } from "@material-ui/core";
import { useNavigate } from "react-router-dom";


export function LobbyScreen() {
  const [waiting, setIsWaiting] = useState(false);
  const [matched, setMatched] = useState(false);
  const [gameID, setGameId] = useState(null);
  const [requestId, setRequestId] = useState(null);
  const [selectedOpening, setSelectedOpening] = useState("freestyle");
  const [selectedVariant, setSelectedVariant] = useState("freestyle");
  const [selectedBoardSize, setSelectedBoardSize] = useState("15");
  const [isCreating, setIsCreating] = useState(false);
  let navigate = useNavigate();
  const POLLING_INTERVAL = 10000;

  useEffect(() => {
    leaveLobby(); //leave lobby on refresh
    return () => {
      leaveLobby(); //when leave lobby page remove request from server
    };
  }, []);

  //used for polling whether the game has been created
  useInterval(async () => {
    if (waiting) {
      let gameId = await LobbyService.checkgameCreated(requestId);
      if (gameId) {
        setGameId(gameId);
        setMatched(true);
        setIsWaiting(false);
      }
    }
  }, POLLING_INTERVAL);

  //add endpoint to leave lobby on server
  async function leaveLobby() {
    try {
      const response = await LobbyService.leaveLobby();
      if (response && !response.error) {
        setIsWaiting(false);
        setRequestId(null);
      }
    } catch (error) {
      //TODO: handle error (is it necessary?)
      console.error("Error ocurred while leaving lobby", error);
    };
  }

  async function handleCreateLobby() {
    setIsCreating(true);
    let props = { selectedBoardSize, selectedOpening, selectedVariant }
    await LobbyService.joinLobby(props).then((response) => {
      setRequestId(response.value.gameRequestId);
      setIsWaiting(true);
    });
    // Implement the logic for creating the lobby based on the selected values
    // Use selectedOpening, selectedVariant, and selectedBoardSize
  }

  let submitButton;
  let gameButton = null;

  if (waiting) {
    submitButton = (
      <Button
        type="submit" variant="contained" color="secondary"
        onClick={() => { leaveLobby(); }}
      >Cancel
      </Button>
    );
  } else if (matched) {
    submitButton = (
      <Button
        type="submit" variant="contained"
        onClick={() => { navigate(`/home`); }}
      > Go to User Home
      </Button>
    );
    gameButton = (
      <Button
        type="submit" variant="contained" color="primary"
        onClick={() => { navigate(`/game/${gameID}`); }}
      >
        Go to Game
      </Button>
    );
  } else {
    submitButton = (
      <Button
        type="submit" variant="contained" color="primary"
        onClick={() => { handleCreateLobby(); }}
        disabled={isCreating}
      >
        {isCreating ? "Creating..." : "Create Lobby"}
      </Button>
    );
  }

  return (
    <div>
      <h1>Create your own lobby:</h1>

      <FormControl component="fieldset" disabled={waiting}>
        <p>Opening:</p>
        <RadioGroup
          aria-label="opening" name="opening" value={selectedOpening}
          onChange={(e) => setSelectedOpening(e.target.value)}
        >
          <FormControlLabel value="freestyle" control={<Radio />} label="Freestyle" />
          <FormControlLabel value="pro" control={<Radio />} label="Pro" />
        </RadioGroup>
      </FormControl>

      <FormControl component="fieldset" disabled={waiting}>
        <p>Variant:</p>
        <RadioGroup
          aria-label="variant" name="variant" value={selectedVariant}
          onChange={(e) => setSelectedVariant(e.target.value)}
        >
          <FormControlLabel value="freestyle" control={<Radio />} label="Freestyle" />
          <FormControlLabel value="swap_after_first" control={<Radio />} label="Swap after first" />
        </RadioGroup>
      </FormControl>

      <FormControl component="fieldset" disabled={waiting}>
        <p>Board Size:</p>
        <RadioGroup
          aria-label="board-size" name="board-size" value={selectedBoardSize}
          onChange={(e) => setSelectedBoardSize(e.target.value)}
        >
          <FormControlLabel value="15" control={<Radio />} label="15x15" />
          <FormControlLabel value="19" control={<Radio />} label="19x19" />
        </RadioGroup>
      </FormControl>

      {submitButton}

      {gameButton}
    </div>
  );
}

export function useInterval(callback, delay) {
  useEffect(() => {
    function tick() {
      callback();
    }
    if (delay != null) {
      const id = setInterval(tick, delay);
      return () => {
        clearInterval(id);
      };
    }
  }, [callback, delay]);
}