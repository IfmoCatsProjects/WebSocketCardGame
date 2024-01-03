import React, {useEffect, useState} from "react";

export function Card(props){
    return (<img id={props.id} className={"card"} style={{top: props.top,
        left: props.left, display: props.display}} src={`../../images/${props.image}.png`}
        onClick={props.onClick}
        onMouseEnter={props.onMouseEnter}
        onMouseLeave={props.onMouseLeave}/>)
}

export function ClickOnCardDeck(props) {
    const [coordinates, setCoordinates] = useState({x: 0, y: 0})
    const app = props.app

    useEffect(() => {
        const cardManager = event => {
            setCoordinates({
                x: event.pageX,
                y: event.pageY,
            });
        };
        window.addEventListener('mousemove', cardManager);

        return () => window.removeEventListener('mousemove', cardManager,)
    }, [])

    if (app.state.clicked) {
        return (
            <div id={"move-card"} style={{top: `${coordinates.y}px`, left: `${coordinates.x}px`}}>
                <Card id={"move"} image={app.state.clickedCard}/>
            </div>
        )
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