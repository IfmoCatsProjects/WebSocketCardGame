import React from "react";
import {removeCard} from "./cardManager";
import {send} from "./connection";
import {MoveSubscriptions} from "./subscriptions";

class Events {
    state
    setState

    constructor(state, setState) {
        this.state = state
        this.setState = setState
        MoveSubscriptions.state = state
    }

    mouseEnterOnCardDeck(el) {
        if (!this.state.clicked && this.state.current === this.state.position) {
            el.target.style.border = "2px solid blue"
            el.target.style.top = "26vh"
            el.target.style.cursor = "pointer"
        }
    }

    mouseLeaveFromCardDeck(el) {
        if (!this.state.clicked && this.state.current === this.state.position) {
            el.target.style.border = "1px solid black"
            el.target.style.top = "27vh"
            el.target.style.cursor = "default"
        }
    }

    click(cardId) {
        let id = cardId.target.id
        if (!this.state.clicked && this.state.current === this.state.position)
            send({cardPos: id.substring(4)}, "click")
    }

    put(frame) {
        if (this.state.clicked && this.state.current === this.state.position) {
            let id = frame.target.id.substring(5)
            send({player: id, card: this.state.clickedCard}, "put")
            console.log(this.state)
        }
    }

    take() {
        if (!this.state.clicked && this.state.current === this.state.position) {
            send({}, "take")
        }
    }
}

export function Frame(props) {
    if (props.state.clicked && props.state.current === props.state.position) {
        return (<div id={props.id} className={"card frame"} style={{top: String(props.top), left: String(props.left)}}
                     onClick={props.onClick}></div>)
    }
}

export default Events