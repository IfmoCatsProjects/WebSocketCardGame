import React from "react";
import {Card, ClickOnCardDeck} from "./cardManager";
import {createRoot} from "react-dom/client";
import {mouseEnterOnCardDeck, mouseLeaveFromCardDeck, click, Frame} from "./events";

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
                <Card id={"1"} image={"shirt"} top={"12vh"} left={"50vw"} display={"none"} onClick={() => put(this)}/>
                <Frame id={"frame1"} top={"12vh"} left={"50vw"} app={this}/>
            </div>
            <div id={"center"}>
                <Card id={"2"} image={"shirt"} top={"27vh"} left={"0.5vw"} display={"none"} onClick={() => put(this)}/>
                <Frame id={"frame2"} top={"27vh"} left={"5vw"} app={this}/>
                {deck.map((e) => e)}
                <Card id={"4"} image={this.props.common} top={"27vh"} left={"84vw"} onClick={() => put(this)}/>
            </div>
            <div id={"bottom"}>
                <Card id={"0"} image={"shirt"} top={"10vh"} left={"50vw"} display={"none"} onClick={() => put(this)}/>
                <Frame id={"frame 0"} top={"10vh"} left={"50vw"} app={this}/>
            </div>
            <ClickOnCardDeck app={this}/>
        </div>)
    }

}
document.getElementById("start").addEventListener("click", () => {
    createRoot(document.getElementById("root")).render(<App />)
    // send({number: 12}, "connect", true, () => {
    //     send({}, "start", true, (message) => {
    //         createRoot(document.getElementById("root")).render(<App common={message}/>)
    //     })
    // })
})
