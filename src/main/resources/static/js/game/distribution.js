import React from "react";
import {Card, ClickOnCardDeck} from "./cardManager";
import {createRoot} from "react-dom/client";
import {mouseEnterOnCardDeck, mouseLeaveFromCardDeck, click, Frame, put, take} from "./events";

class App extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            clicked: false,
            clickedCard: null,
            cursorX: 0,
            cursorY: 0
        }
    }
    createDeck() {
        let cards = []
        let offset = 21;
        for (let i = 0; i < 35; i++) {
            cards.push(<Card id={"card" + i} image={"shirt"} top={"27vh"} left={`${offset}vw`}
             onClick={(el) => click(el, this)}
             onMouseEnter={(el) => mouseEnterOnCardDeck(this, el)}
             onMouseLeave={(el) => mouseLeaveFromCardDeck(this, el)}/>)
            offset = offset + 1.57
        }
        return cards
    }

    render() {
        let deck = this.createDeck()

        return (<div id={"main"}>
            <div id={"top"}>
                <button onClick={(el) => {send({}, "close", true, () => {}); el.target.disabled = true}}>Stop</button>

                <Card id={"1"} image={"shirt"} top={"12vh"} left={"50vw"} display={"none"} />
                <Frame id={"frame1"} top={"12vh"} left={"50vw"} app={this} onClick={(frame) => put(this, frame)}/>
            </div>
            <div id={"center"}>
                <Card id={"2"} image={"shirt"} top={"27vh"} left={"5vw"} display={"none"} />
                <Frame id={"frame2"} top={"27vh"} left={"5vw"} app={this} onClick={(frame) => put(this, frame)}/>

                {deck.map((e) => e)}

                <Card id={"3"} image={this.props.common} top={"27vh"} left={"84vw"} />
                <Frame id={"frame3"} top={"27vh"} left={"84vw"} app={this} onClick={(frame) => put(this, frame)}/>
            </div>
            <div id={"bottom"}>
                <Card id={"0"} image={"shirt"} top={"10vh"} left={"50vw"} display={"none"} onClick={() => take(this)}/>
                <Frame id={"frame0"} top={"10vh"} left={"50vw"} app={this} onClick={(frame) => put(this, frame)}/>
            </div>
            <ClickOnCardDeck app={this}/>
        </div>)
    }

}
document.getElementById("start").addEventListener("click", () => {
    send({number: 12}, "connect", true, () => {
        send({}, "start", true, (message) => {
            createRoot(document.getElementById("root")).render(<App common={message}/>)
        })
    })
})
