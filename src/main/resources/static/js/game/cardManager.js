import React, {useEffect, useState} from "react";

export function Card(props){
    return (<img id={props.id} className={"card"} style={{top: props.top,
        left: props.left, display: props.display}} src={`../../images/${props.image}.png`}
        onClick={props.onClick}
        onMouseEnter={props.onMouseEnter}
        onMouseLeave={props.onMouseLeave}/>)
}

export function ClickOnCardDeck({state}) {
    const [coordinates, setCoordinates] = useState({x: "50%", y: "50%"})

    useEffect(() => {
        if (state.current === state.position) {
            const cardManager = event => {
                setCoordinates({
                    x: `${event.pageX}px`,
                    y: `${event.pageY}px`,
                });
            };
            window.addEventListener('mousemove', cardManager);

            return () => window.removeEventListener('mousemove', cardManager)
        }
    }, [state])

    if (state.clicked) {
        let x = state.current === state.position ? coordinates.x : "50%"
        let y = state.current === state.position ? coordinates.y : "50%"

        return (
            <div id={"move-card"} style={{top: y, left: x}}>
                <Card id={"move"} image={state.clickedCard}/>
            </div>
        )
    }
}
export function removeCard(id) {
    let element = document.getElementById(id)
    element.parentElement.removeChild(element)
}