"use strict";

document.addEventListener('DOMContentLoaded', init);
let allBuildingTiles = JSON.parse(localStorage.getItem("buildingTiles")) || [];
let allPlayers = JSON.parse(localStorage.getItem("allPlayers")) || [];
let scoringBoard = document.querySelector('.alh-scoreboard');
let boardTable = document.querySelector('.alh-board');
let scoreNr = 90;
const array = coins;
let playersOnBoard = [];
let storedPlayersOnBoard = JSON.parse(localStorage.getItem("allPlayers")) || [];
let gameIdentifier = JSON.parse(localStorage.getItem("gameIdentifier")) || [];

function init() {
    loadBoard();
    getBuildingCards();
    shuffleCards(array);
    giveEachPlayerStartingMoney();
    addBeginCardsOfShop();
    addScoringRoundsToArray();
    gameBoardPlayers();
    playersInLobbyBoard();
    gameBoardPlayers();
}

function loadBoard() {
    loadRows(11,11, boardTable, "alh-board__row", "alh-board__field");
    loadRows(10,10, scoringBoard, "alh-score__row", "alh-score__field");
    giveEachSquareAnId();
}

function loadRows(amountRows, amountOfFields, target, classRows, classField) {
    let rows = ``;
    let tableHtml = ``;
    for (let i = 0; i < amountRows; i++) {
        rows += `<div class=` + classRows + `>` + loadFields(amountOfFields, classField) + `</div>`;
        tableHtml += rows;
    }
    target.innerHTML += rows;
}

function loadFields(amountOfFields, classField) {
    let res = ``;
    if (amountOfFields === 10) {
        for (let j = 0; j < amountOfFields; j++) {
            scoreNr++;
            res += `<div class=` + classField + ` id=` + scoreNr + `>` + scoreNr + `</div>`;
        }
        scoreNr -= 20;
    }
    else {
        for (let j = 0; j < amountOfFields; j++) {
            res += `<div class=` + classField + `></div>`;
        }
    }
    return res;
}

function giveEachSquareAnId() {
    let list = document.querySelectorAll(".alh-board__field");
    for (let i = 0; i < list.length; i++) {
        if (i <10){
            list[i].setAttribute("id",  `0${i.toString()}`);
        }
        else{
            list[i].setAttribute("id", i.toString());
        }
    }
}

function getBuildingCards() {
    fetch(`${config.root}buildings`)
        .then(function (response) {
            return response.text();})
        .then(function (text) {
            JSON.parse(text).forEach(function (tile) {
                allBuildingTiles.push(tile);
            })
        });
    localStorage.setItem("buildingTiles", JSON.stringify(allBuildingTiles));
}

function shuffleCards(arr) {
    for(let i = arr.length; i>0; i--){
        let newPosition = Math.floor(Math.random() * (i + 1));
        arr[i] = arr[newPosition];
        arr[newPosition] = arr[i];
    }
}

function giveEachPlayerStartingMoney(){
    let html = '';
/*
    let array2 = [];
    for(let i = 0; i < allPlayers.length ; i++) {
        addMoneyCards(array2);
        allPlayers[i].money = array2;
        allPlayers[i].money.forEach(function (card) {
            html += `<article class="unselected" style="background-color: ${card.type}"><h1 id="${card.type}" >${card.amount}</h1></article>`;
        });
        document.querySelector('.wallet').innerHTML = html;
        }
*/
    fetchFromServer(`${config.root}games/${gameIdentifier}`, 'GET').then(function (response) {
        response.players.forEach(function (player) {
            if (player.name === playerName){
                player.playerDeck.forEach(function (moneyCard) {
                    html += `<article class="unselected" style="background-color: ${moneyCard.currency}"><h1 id="${moneyCard.currency}" >${moneyCard.amount}</h1></article>`;
                })
            }
        })
    });
    document.querySelector('.wallet').innerHTML = html;
}
/*
function addMoneyCards(array2) {
    let i = array.length - 1;
    let randomMoneyCard = Math.floor(Math.random() * (i + 1));
    if (countArray(array2) < 20 || array2 === []) {
        array2.push(array[randomMoneyCard]);
        addMoneyCards(array2);
    }
    array.splice(randomMoneyCard, 1);
}
*/
function addMoneyCard(className) {
    let html = `<article class="shop" style="background-color: ${array[0].type}">
                    <h1 id="${array[0].type}" >${array[0].amount}</h1>
                    </article>`;
    array.splice(0, 1);
    document.querySelector(`div.${className}`).innerHTML += html;
}


function addBeginCardsOfShop() {
    /*if (array.length !== 0) {
        if (document.querySelector('div.first').innerHTML === "") {
            addMoneyCard("first");
        }
        if (document.querySelector('div.second').innerHTML === "") {
            addMoneyCard("second");
        }
        if (document.querySelector('div.third').innerHTML === "") {
            addMoneyCard("third");
        }
        if (document.querySelector('div.fourth').innerHTML === "") {
            addMoneyCard("fourth");
        }
    }*/
    let html = '';
    fetchFromServer(`${config.root}/games/${gameIdentifier}`, 'GET').then(function (response) {
        response.moneyBank.forEach(function (card) {
            html += `<article class="shop" style="background-color: ${card.type}">
                    <h1 id="${card.type}" >${card.amount}</h1>
                    </article>`;
        })
    });
    document.querySelector(`.coinsToTake`).innerHTML += html;
}

function addScoringRoundsToArray() {
    let countOfScorecardA;
    let countOfScorecardB;
    for(let i = 0;i < array.length; i++){
        if(i < (array.length/5)*2 && i > (array.length/5)){
            countOfScorecardA = i;
        }
        else if(i < (array.length/5)*4 && i > (array.length/5)*3){
            countOfScorecardB = i;
        }
    }
    array[countOfScorecardA].scoringRound = true;
    array[countOfScorecardB].scoringRound = true;
}

function countArray(arr) {
    let count = 0;
    for(let i = 0; i < arr.length; i++){
        count += arr[i].amount;
    }
    return count;
}

function playersInLobbyBoard() {
    storedPlayersOnBoard.forEach(function (player) {
        playersOnBoard.push(player);
    });
}

function gameBoardPlayers() {
    document.querySelector(".playersListUl").innerHTML = "";
    playersOnBoard.forEach(setNameBoard);
}

function setNameBoard(name) {
    document.querySelector(".playersListUl").innerHTML += "<li>" + name + "</li>";
}