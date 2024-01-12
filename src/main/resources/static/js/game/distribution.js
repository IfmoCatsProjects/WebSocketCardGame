import React, {useEffect, useState} from "react";
import {Card, ClickOnCardDeck} from "./cardManager";
import Events, {Frame} from "./events";
import {send} from "./connection";
import {MoveSubscriptions} from "./subscriptions";

export function Distribution({common, move, pos, count}) {
    const [state, setState] = useState({
        clicked: false,
        clickedCard: null,
        current: move,
        position: pos
    })
    const events = new Events(state, setState)

    const createDeck = () => {
        let cards = []
        let offset = 21
        for (let i = 0; i < 35; i++) {
            cards.push(<Card id={"card" + i} image={"shirt"} top={"27vh"} left={`${offset}vw`}
             onClick={(el) => events.click(el)}
             onMouseEnter={(el) => events.mouseEnterOnCardDeck(el)}
             onMouseLeave={(el) => events.mouseLeaveFromCardDeck(el)}/>)
            offset = offset + 1.57
        }
        return cards
    }

    useEffect(() => MoveSubscriptions.setSubscriptions(setState, count), [])

    return (
        <div id={"main"}>
            <div id={"top"}>
                <button onClick={(el) => {send({}, "close", true, () => {}); el.target.disabled = true}}>Stop</button>

                <Card id={"1"} image={"shirt"} top={"12vh"} left={"50vw"} display={"none"} />
                <Frame id={"frame1"} top={"12vh"} left={"50vw"} clicked={state.clicked} onClick={(frame) => events.put(frame)} state={state}/>
            </div>
            <div id={"center"}>
                {count === 3 ? <Card id={"2"} image={"shirt"} top={"27vh"} left={"5vw"} display={"none"} /> : ""}
                {count === 3 ? <Frame id={"frame2"} top={"27vh"} left={"5vw"} clicked={state.clicked} onClick={(frame) => events.put(frame)}
                                      state={state}/> : ""}
                {createDeck().map((e) => e)}

                <Card id={"3"} image={common} top={"27vh"} left={"84vw"} />
                <Frame id={"frame3"} top={"27vh"} left={"84vw"} clicked={state.clicked} onClick={(frame) => events.put(frame)} state={state}/>
            </div>
            <div id={"bottom"}>
                <Card id={"0"} image={"shirt"} top={"10vh"} left={"50vw"} display={"none"} onClick={() => events.take()}/>
                <Frame id={"frame0"} top={"10vh"} left={"50vw"} clicked={state.clicked} onClick={(frame) => events.put(frame)} state={state}/>
            </div>
            <ClickOnCardDeck state={state}/>
        </div>
        )
}