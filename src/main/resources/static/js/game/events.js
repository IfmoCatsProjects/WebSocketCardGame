import React, {useEffect, useState} from "react";
import Card, {cardMouseMove, removeCard} from "./cardManager";

let clicked = false

export function mouseEnterOnCardDeck(app, el) {
    if (!app.state.clicked) {
        el.target.style.border = "2px solid blue"
        el.target.style.top = "15vh"
        el.target.style.cursor = "pointer"
    }
}

export function mouseLeaveFromCardDeck(app, el) {
    if (!app.state.clicked) {
        el.target.style.border = "1px solid black"
        el.target.style.top = "16vh"
        el.target.style.cursor = "default"
    }
}

export function click(app) {
    send({"number": app.state.clickedCard.substring(4)}, "click", true, (card) => {
        app.setState({clicked: true, clickedCard: card})
    })
}

function framesOn() {
    document.querySelectorAll(".frame").forEach(e => e.style.display = "")
}

function framesOff() {
    document.querySelectorAll(".frame").forEach(e => e.style.display = "none")
}

function put(app) {
    for (let frame of document.querySelectorAll(".frame")) {
        frame.addEventListener('click', () => {
            let player = frame.className.split(" ")[1]
            if (app.state.clicked) {
                let card = document.getElementById("move-card").firstElementChild
                send({"number": player, "data": card.id}, "put", true, () => {
                    app.setState({clicked: false})
                    removeCardFromCursor()
                    removeCard(card.id)
                    addCardToDeck(player, card.id)
                    framesOff()
                })
            }
        })
    }
}
function take() {
    document.getElementById("0").addEventListener('click', () => {
        if(!clicked) {
            send({}, "take", true, (msg) => {
                clicked = true
                let takenCard = msg.split(" ")[0]
                let subCard = msg.split(" ")[1]
                addCardToCursor(takenCard)
                framesOn()
                console.log(msg)
                if(subCard === "none") {
                    document.getElementById("0").style.display = "none"
                } else {
                    document.getElementById("0").src = `images/${subCard}.png`
                }
            })
        }
    })
}