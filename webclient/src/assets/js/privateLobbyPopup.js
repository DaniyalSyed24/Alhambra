'use strict';

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector(".button").addEventListener('click', viewPopup);
    document.querySelector(".close-button").addEventListener('click', closePopup);
}

function viewPopup(e){
        e.preventDefault();
        document.querySelector("#popup").classList.toggle("hidden");
}

function closePopup(e) {
    e.preventDefault();
    document.querySelector("#popup").classList.toggle("hidden");
}

