"use strict";

let _playerToken = null;


function joinGame(gameId, username) {
    fetchFromServer(`${config.root}games/${gameId}/players`,'POST',{playerName: `${username.toLowerCase()}` })
        .then(function(response){
            _playerToken = response;
        });
    localStorage.setItem('token', _playerToken);
}