'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
    document.querySelector(".open-menu").addEventListener('click', openMenu);
    document.querySelector(".close-menu-button").addEventListener('click', closeMenu);

    document.querySelector(".open-alhambras").addEventListener('click', openAlhambras);
    document.querySelector(".close-alhambras-button").addEventListener('click', closeAlhambras);
}


function openMenu(e) {
    e.preventDefault();
    document.querySelector("#popup-menu").classList.toggle("hidden");
}
function closeMenu(e) {
    e.preventDefault();
    document.querySelector('#popup-menu').classList.toggle("hidden");
}


function openAlhambras(e) {
    e.preventDefault();
    document.querySelector("#popup-otherAlhambras").classList.toggle("hidden");
}
function closeAlhambras(e) {
    e.preventDefault();
    document.querySelector("#popup-otherAlhambras").classList.toggle("hidden");
}
