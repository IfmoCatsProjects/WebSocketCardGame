import React, {useState} from "react";
import { IoIosCloseCircleOutline } from "react-icons/io";

export function GameRoom({gameType, setGameType}) {
    const [playersCount, setPlayersCount] = useState(2)

    const changeCount = (e) => {
        let elementProps = e.target.getBoundingClientRect()

        if ((e.pageX - elementProps.x) / elementProps.width < 0.5) {
            setPlayersCount(2)
            document.querySelector(".two-players").classList.add("selected")
            document.querySelector(".three-players").classList.remove("selected")
        }
        else {
            setPlayersCount(3)
            document.querySelector(".three-players").classList.add("selected")
            document.querySelector(".two-players").classList.remove("selected")
        }
    }

    return (<div id={"game-create"}>
            <div id={"blur"}></div>
            <div id={"create-window"}>
                <div id={"close"} onClick={() => setGameType("none")}>
                    <IoIosCloseCircleOutline size={25} color={"red"}/>
                </div>
                <div onClick={e => changeCount(e)} id={"players"}>
                    <div className={"player-count-selector two-players selected"}>2 игрока</div>
                    <div className={"player-count-selector three-players"}>3 игрока</div>
                </div>
                <button id={"create-button"}>Создать</button>
            </div>
        </div>
    )
}
