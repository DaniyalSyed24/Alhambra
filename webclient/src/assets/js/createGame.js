"use strict";
document.addEventListener('DOMContentLoaded',init);

let playerName = JSON.parse(localStorage.getItem("playerName")) || [];
let gameIdentifier = JSON.parse(localStorage.getItem("gameIdentifier")) || [];
let _gameId = "";

function init(){
    document.querySelector("#createLobby").addEventListener("click", createLobby);
}

function createLobby() {
    let textField = document.querySelector("#username").value;
    fetchFromServer(
        `${config.root}games`,
        'POST',
        {prefix: `group${config.groupnumber}`})

        .then(function (response) {
            //joinGame(response, textField);
            _gameId = response;
        });

    localStorage.setItem("gameIdentifier", JSON.stringify(_gameId));
    localStorage.setItem("playerName", JSON.stringify(textField));

}


