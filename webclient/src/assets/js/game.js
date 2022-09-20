"use strict";

document.addEventListener('DOMContentLoaded', init);
const buildings = allBuildingTiles;
let playerName = JSON.parse(localStorage.getItem("playerName")) || [];
let personalGameId = JSON.parse(localStorage.getItem("gameIdentifier"));

const pay = {
    currency: "",
    money: []
};
const money = {
    type: "none",
    amount: 0,
    div: ""
};
let pawn = ``;
let data = {
    oldLocation: {
        oldRow: 0,
        oldCol: 0
    },
    newLocation: {
        newRow: 0,
        newCol: 0
    }
};

function init() {
    document.querySelector('.open').addEventListener('click', openBuildingTiles);
    document.querySelector('.close').addEventListener('click', closeBuildingTiles);
    document.querySelector('.clicked').addEventListener('click', makeYourMove);

    shuffleCards(buildings);
    setInterval(addBuildingTileToMarket, 2000);
}

function openBuildingTiles(e) {
    e.preventDefault();
    document.querySelector("#popup-buildings").classList.toggle("hidden");
}

function closeBuildingTiles(e){
    e.preventDefault();
    document.querySelector("#popup-buildings").classList.toggle("hidden");
}

function addBuildingTileToMarket() {
/*
    if (buildings.length !== 0) {
        if (document.querySelector('div.blue').innerHTML === "") {
            randomBuildingTile("blue");
        }
        if (document.querySelector('div.green').innerHTML === "") {
            randomBuildingTile("green");
        }
        if (document.querySelector('div.orange').innerHTML === "") {
            randomBuildingTile("orange");
        }
        if (document.querySelector('div.yellow').innerHTML === "") {
            randomBuildingTile("yellow");
        }
    }
*/
    fetchFromServer(`${config.root}games/${personalGameId}`, 'GET').then(function (response) {
        document.querySelector('div.blue').innerHTML = `<img src="images/buildings/${response.market.blue.type}.jpg" alt="${response.market.blue.type} " class="blue">
                <h1>${response.market.blue.amount}</h1>`;
        document.querySelector('div.green').innerHTML = `<img src="images/buildings/${response.market.green.type}.jpg" alt="${response.market.green.type} " class="green">
                <h1>${response.market.green.amount}</h1>`;
        document.querySelector('div.yellow').innerHTML = `<img src="images/buildings/${response.market.yellow.type}.jpg" alt="${response.market.yellow.type} " class="yellow">
                <h1>${response.market.yellow.amount}</h1>`;
        document.querySelector('div.orange').innerHTML = `<img src="images/buildings/${response.market.orange.type}.jpg" alt="${response.market.orange.type} " class="orange">
                <h1>${response.market.orange.amount}</h1>`;
    });
}

/*
function randomBuildingTile(color) {
    let randomPosition = Math.floor(Math.random() * (buildings.length + 1));
    let html = `<img src="images/buildings/${buildings[randomPosition].type}.jpg" alt="${buildings[randomPosition].type} " class="${color}">
                <h1>${buildings[randomPosition].cost}</h1>`;

    buildings.splice(randomPosition, 1);

    let counter = buildings.length;
    document.querySelector(".counter").innerHTML = `${counter}`;


    document.querySelector(`div.${color}`).innerHTML += html;

}
*/

function makeYourMove(e) {
    e.preventDefault();
    if (e.target.getAttribute('class') === "build") {
        if(document.querySelector('#popup-buildings__tiles').innerHTML !== "\n" +
            "    " || checkIfThereAreBuildingsToMove() === true){
            if(checkIfThereAreBuildingsToMove() === true){
                document.querySelector('#move').classList.toggle('move');
                document.querySelector('#move').addEventListener('click', moveBuilding);
            }
            document.querySelector('#popup-buildings').classList.toggle('hidden');
            document.querySelector('#popup-buildings__tiles').addEventListener('click', buildYourAlhambra);
        }
        else{
            window.alert("Je hebt geen gebouwen");
        }
        e.stopPropagation();
    } else if (e.target.getAttribute('class') === "buy") {
        document.querySelector(".buildingMarket-buildings").addEventListener('click', buyBuildingTile);
    } else if (e.target.getAttribute('class') === "earn") {
        document.querySelector('.moneyBank').addEventListener('click', collectMoney);
    }
}

function moveBuilding() {
    document.querySelector('#popup-buildings').classList.toggle('hidden');
    if(checkIfThereAreBuildingsToMove() === false){
        document.querySelector('#move').classList.toggle('move');
    }
    document.querySelector('.alh-board').addEventListener('click', selectBuildingToMove);
}


function selectBuildingToMove(e) {
    let pawnToMove = e.target;
    console.log(pawnToMove);
    if (pawnToMove.parentElement.id !== "60"){
        data.oldRow = pawnToMove.parentElement.id.toString()[0];
        data.oldCol = pawnToMove.parentElement.id.toString()[1];
        document.querySelector('.alh-board').addEventListener('click', moveBuildingOnBoard);
    }
    e.stopPropagation();
}

function moveBuildingOnBoard(e) {
    let place = e.target;
    if (place.innerHTML === "") {
        data.newRow = parseInt(place.id[0]);
        data.newCol = parseInt(place.id[1]);
        movePawn();
    }
    e.stopPropagation();
}

function movePawn(e) {
    e.preventDefault();
    fetch('/games/{personalGameId}/players/{playerName}/money', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
        }
    }).then(function (response) {
    }).catch(error => console.error('Error:', error));


    //document.querySelector(`#${selectedBuildingToMove[1]}`).innerHTML = "";
    //document.querySelector(`#${data.newRow}${data.newCol}`).innerHTML = `<img src="images/buildings/${selectedBuildingToMove[0]}.jpg" alt="${selectedBuildingToMove[0]}">`;

    e.stopPropagation();
}


function buildYourAlhambra(e) {
    e.preventDefault();
    pawn = e.target;
    document.querySelector('#popup-buildings').classList.toggle('hidden');
    document.querySelector('.alh-board').addEventListener('click', placeBuildingOnBoard);
    e.stopPropagation();
}

function placeBuildingOnBoard(e) {
    let placeOnBoard = e.target;

    let dataForBuild = {
        building: {
            type: pawn.type,
            cost: pawn.cost,
            walls: pawn.walls
        },
        location: {
            row: placeOnBoard.id.toString()[0],
            col: placeOnBoard.id.toString()[1],
        }
    };

    if (["59", "60", "61", "50", "70"].indexOf(placeOnBoard.id) >= -1) {
        if(placeOnBoard.className === "alh-board__field"){
            fetchFromServer(`${config.root}games/${personalGameId}/players/${playerName}/city`, 'POST', dataForBuild).then(function (response) {
                    console.log(response);
            });
            pawn.parentElement.removeChild(pawn);
            //placeOnBoard.innerHTML += `<img src="images/buildings/${pawn.alt}.jpg" alt="${pawn.alt}">`
        }
    }
    else{
        window.alert("Neem een geldige plaats");
    }
    e.stopPropagation();
}


function buyBuildingTile(e) {
    pay.currency = `${e.target.getAttribute('class')}`;
    document.querySelector("#pay").innerHTML = "pay";
    document.querySelector("#pay").classList.toggle("pay");
    document.querySelector('.wallet').addEventListener('click', chooseMoneyToPay);
}

function chooseMoneyToPay(element) {
    const moneyCard = {
        amount: element.target.innerText,
        color: element.target.id
    };
    if(containsObject(moneyCard, pay.money) === false && moneyCard.color.toLowerCase() === pay.currency.toLowerCase()){

        element.target.parentElement.setAttribute('class', 'selected');
        pay.money.push(moneyCard);
    }
    else{
        element.target.parentElement.setAttribute('class', 'unselected');
        pay.money.splice(findIndexInArray(moneyCard, pay.money), 1);
    }
    document.querySelector('#pay').addEventListener('click', finishPayment);
}

function finishPayment() {
/*
    document.querySelectorAll('.selected').forEach(function (node) {
        node.parentElement.removeChild(node);
    });
    document.querySelector("#pay").classList.toggle("pay");
    document.querySelector('#popup-buildings__tiles').innerHTML +=
        `<img src="images/buildings/${pay.type}.jpg" alt="${pay.type}" about="">
             <h1>${pay.amount}</h1>`;
*/

    pay.money = [];
    document.querySelector(`div.${pay.currency}`).innerHTML = "";
    fetchFromServer(`${config.root}games/${personalGameId}/players/${playerName}/buildings-in-hand`, 'POST', pay).then(function (response) {
       console.log(response);
       let html = "";
       response.players.forEach(function (player) {
            if(player.name === playerName){
                player.buildingReserve.foreach(function(tile){
                    html += `<img src="images/buildings/${tile.type}.jpg" alt="${tile.type}" about="${tile.walls}">
                    <h1>${pay.amount}</h1>`;
                 })
            }
       });
        document.querySelector('#popup-buildings__tiles').innerHTML = html
    });
}

function collectMoney(e) {
    document.querySelector("#pay").innerHTML = "Collect";
    document.querySelector("#pay").classList.toggle("pay");
    e.target.setAttribute('class', "selected");

    let takeMoney = {
          currency: e.target.getAttribute('id'),
          amount: e.target.innerText
    };

    fetchFromServer(`${config.root}games/${personalGameId}/players/${playerName}/money`, 'POST', takeMoney).then(function (response) {
       console.log(response);
    });

    money.type = e.target.getAttribute('id');
    money.amount = e.target.innerText;
    money.div = e.target.parentElement.parentElement.getAttribute('class');
    document.querySelector('#pay').addEventListener('click', finishEarning);
}

function finishEarning(e) {
    e.preventDefault();
    console.log(money.div);
    document.querySelector(`div.${money.div}`).innerHTML = "";
    document.querySelector('.wallet').innerHTML +=`<article class="unselected" style="background-color: ${money.type}"><h1 id="${money.type}" >${money.amount}</h1></article>`;
    document.querySelector("#pay").classList.toggle("pay");
    addBeginCardsOfShop();
}

function containsObject(obj, list) {
    for (let i =0; i < list.length; i++) {
        if (list[i].amount === obj.amount && list[i].color === obj.color) {
            return true;
        }
    }
    return false;
}

function findIndexInArray(obj, list) {
    for (let i =0; i<list.length; i++) {
        if (list[i].amount === obj.amount && list[i].color === obj.color) {
            return i;
        }
    }
}

function checkIfThereAreBuildingsToMove() {
    const arr = document.querySelectorAll('.alh-board__field');
    let bool = false;
    arr.forEach(function (building) {
        if (building.id !== "60" && building.innerHTML !== ""){
            bool = true;
        }
    });
    return bool;
}
