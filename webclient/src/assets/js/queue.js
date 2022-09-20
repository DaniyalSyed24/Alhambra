"use strict";

document.addEventListener("DOMContentLoaded", init);
let playerName = JSON.parse(localStorage.getItem("playerName")) || [];
function init(){
    document.querySelector("#joinGame").addEventListener('click', saveUsername);
}

function saveUsername() {
    let textField = document.querySelector("#username").value;
    playerName.push(textField);
    localStorage.setItem("playerName", JSON.stringify(playerName));
}