import React from "react";
import {removeCard} from "./cardManager";
import {send} from "./connection";

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
    if (!app.state.clicked) {
        let id = cardId.target.id
        send({"number": id.substring(4)}, "click", true, (card) => {
            removeCard(cardId.target.id)
            app.setState({clicked: true, clickedCard: card})
        })
    }
}

export function Frame(props) {
    if (props.app.state.clicked) {
        return (<div id={props.id} className={"card frame"} style={{top: String(props.top), left: String(props.left)}}
                     onClick={props.onClick}></div>)
    }
}

export function put(app, frame) {
    if (app.state.clicked) {
        app.setState({clicked: false})
        let id = frame.target.id.substring(5)
        send({"number": id, "data": app.state.clickedCard}, "put", true, () => {
            let playerCard = document.getElementById(id)
            playerCard.style.display = ""
            playerCard.src = `../../images/${app.state.clickedCard}.png`
        })
    }
}

export function take(app) {
    if (!app.state.clicked) {
        send({}, "take", true, (msg) => {
            let takenCard = msg.split(" ")[0]
            let subCard = msg.split(" ")[1]
            app.setState({clicked: true, clickedCard: takenCard})

            if(subCard === "none") {
                document.getElementById("0").style.display = "none"
            } else {
                document.getElementById("0").src = `../../images/${subCard}.png`
            }
        })
    }
}