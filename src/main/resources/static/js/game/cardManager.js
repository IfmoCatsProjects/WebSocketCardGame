import React from "react";

export function Card(props){
    return (<img id={props.id} className={"card "} style={{top: `${props.top}`,
        left:`${props.left}`}} src={`../../images/${props.image}.png`}
        onClick={props.onClick}
        onMouseEnter={props.onMouseEnter}
        onMouseLeave={props.onMouseLeave}/>)
}

export function ClickOnCardDeck(app) {
    if (app.state.clicked) {
        return (<div id={"move-card"} style={{top: String(app.state.cursorY) + "px", left: String(app.state.cursorX) + "px"}}
                     onMouseMove={(el) => cardMouseMove(el)}>
            <Card id={"move"} image={app.state.clickedCard}/>
        </div>)
    }
}
export function removeCard(id) {
    let element = document.getElementById(id)
    element.parentElement.removeChild(element)
}

function addCardToDeck(id, imgName) {
    let card = document.getElementById(id)
    card.style.display = ""
    card.src = `../../images/${imgName}.png`
    return card
}

export function cardMouseMove(el) {
    el.target.style.left = el.target.pageX - 60 + 'px'
    el.target.style.top = el.target.pageY - 90 + 'px';
}
function addCardToCursor(takenCard) {
    let clickedCard = createCard("#move-card", takenCard, takenCard, 0, 0)
    clickedCard.style.pointerEvents = "none"
    window.addEventListener('mousemove', e => cardMouseMove(e))
}

function removeCardFromCursor() {
    window.removeEventListener('mousemove', e => cardMouseMove(e))
}