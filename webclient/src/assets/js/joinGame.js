"use strict";

document.addEventListener('DOMContentLoaded',init);

let gameList = [];
let gameId = '';


function init(){
    getGames();

}

function getGames() {
    document.querySelector('#serverlist').innerHTML = '';
    fetchFromServer(`${config.root}games?details=true&prefix=group${config.groupnumber}`,'GET').then(function(response){

        for (let i = 0; i < response.length; i++) {
            gameList.push([response[i].id, response[i].playerCount, response[i].readyCount]);

            document.querySelector('#serverlist').innerHTML += `<tr id="${gameList[i][0]}">
    <td>${gameList[i][0]}</td>
    <td>${gameList[i][1]}/6</td>
    <td>${gameList[i][2]}/6</td>
    <td>public</td>
    <td>
    <a id="joinGame" href="queueSearchingForPlayers.html" class="button" class="join">Join Game!</a>
    </td>
    </tr>`
        }
        document.querySelectorAll('.join').addEventListener("click", join);
    });

}

function join(e) {
    let playerName = JSON.parse(localStorage.getItem(playerName)) || [];
    allPlayers.push(playerName);
    localStorage.setItem("allPlayers", JSON.stringify(allPlayers));

    gameId = e.target.parentElement.id;
    saveGameId();
    joinGame(gameId, playerName)

}

function saveGameId() {
    //localstorage
}