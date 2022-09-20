"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("#readyUp").addEventListener('click', readyUp);
}

function readyUp(e) {
    if(e.target.innerText === "Ready up"){
        document.querySelector("#yourReadyTd").innerHTML = "| Ready";
        document.querySelector("#readyUp").innerHTML = "Unready";
    }
    else {
        document.querySelector("#yourReadyTd").innerHTML = "| Not ready";
        document.querySelector("#readyUp").innerHTML = "Ready up";
    }
}