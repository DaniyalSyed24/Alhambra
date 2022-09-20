"use strict";

document.addEventListener('DOMContentLoaded',init);

let players = [];


function init(){
    playersInLobby();
    showPlayers();
}

function playersInLobby() {
    fetchFromServer(`${config.root}/games/${gameIdentifier}/players`, 'GET').then(function (response) {
        players = response;
    });
}

function showPlayers(){
    document.querySelector("#listplayers").innerHTML = "";
    players.forEach(setName);
    document.querySelector(".players-grid-container").innerHTML += '<div class="playerbox"></div>';  /* otherPlayersAlhambra's */
}

function setName(name) {
    document.querySelector("#listplayers").innerHTML += "<li>" + name + "</li>";
}
