import React from "react";
import {Card, ClickOnCardDeck} from "./cardManager";
import {createRoot} from "react-dom/client";
import {mouseEnterOnCardDeck, mouseLeaveFromCardDeck, click} from "./events";

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
        let offset = 18;
        for (let i = 0; i < 35; i++) {
            cards.push(<Card id={"card" + i} image={"shirt"} top={"16vh"} left={`${offset}vw`}
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
                <Card id={"1"} image={"shirt"} top={"0.5vh"} left={"46vw"} display={"none"} onClick={() => put(this)}/>
                <Card id={"frame1"} image={"shirt"} top={"0.5vh"} left={"46vw"} display={"none"} onClick={() => put(this)}/>
            </div>
            <div id={"center"}>
                <Card id={"2"} class={"player"} image={"shirt"} top={"16vh"} left={"0.5vw"} display={"none"} onClick={() => put(this)}/>
                <Card id={"frame2"} image={"shirt"} top={"16vh"} left={"0.5vw"} display={"none"} onClick={() => put(this)}/>
                {deck.map((e) => e)}
                <Card id={"4"} image={this.props.common} top={"16vh"} left={"81vw"} onClick={() => put(this)}/>
            </div>
            <div id={"bottom"}>
                <Card id={"0"} class={"player"} image={"shirt"} top={"0"} left={"46vw"} display={"none"} onClick={() => put(this)}/>
                <Card id={"frame 0"} class={"player"} image={"shirt"} top={"0"} left={"46vw"} display={"none"} onClick={() => put(this)}/>
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
// function createFrames(players) {
//     for (let player of players) {
//         let frame = document.createElement('div')
//
//         frame.className = "frame " + player.id
//         frame.style.width = "120px"
//         frame.style.height = player.style.height
//         frame.style.position = "absolute"
//         frame.style.top = player.style.top
//         frame.style.left = player.style.left
//         frame.style.border = "3px solid lime"
//         frame.style.borderRadius = "20px"
//         frame.style.display = "none"
//
//         frame.addEventListener("mouseenter", () => {
//             frame.style.cursor = "pointer"
//         })
//         frame.addEventListener("mouseleave", () => {
//             frame.style.cursor = "default"
//         })
//         document.getElementById(player.parentNode.id).appendChild(frame)
//     }
// }
//
// document.getElementById("start").addEventListener("click", () => {
//     send({number: 12}, "connect", true, (msg) => {
//         send({}, "start", true, (message) => {
//             createCard("#center", "3", message, "16vh", "81vw")
//             distribution()
//         })
//     })
// })
