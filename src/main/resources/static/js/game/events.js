import React, {useEffect, useState} from "react";
import Card, {cardMouseMove, removeCard} from "./cardManager";

let clicked = false

export function mouseEnterOnCardDeck(app, el) {
    if (!app.state.clicked) {
        el.target.style.border = "2px solid blue"
        el.target.style.top = "26vh"
        el.target.style.cursor = "pointer"
    }
}

export function mouseLeaveFromCardDeck(app, el) {
    if (!app.state.clicked) {
        el.target.style.border = "1px solid black"
        el.target.style.top = "27vh"
        el.target.style.cursor = "default"
    }
}

export function click(cardId, app) {
    let id = cardId.target.id
    removeCard(cardId.target.id)
    app.setState({clicked: true})
    // send({"number": id.substring(4)}, "click", true, (card) => {
    //     removeCard(cardId.target.id)
    //     app.setState({clicked: true, clickedCard: card})
    // })
}

export function Frame(props) {
    if (props.app.state.clicked) {
        return (<div id={props.id} className={"card frame"} style={{top: String(props.top), left: String(props.left)}}
                     onClick={el => put(props.app)}></div>)
    }
}

function put(app, frame) {
    app.setState({clicked: false}) 
    // send({"number": frame.target.id.substring(5), "data": app.state.clickedCard}, "put", true, () => {
    //     app.setState({clicked: false})
    // })
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