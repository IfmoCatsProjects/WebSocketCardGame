import React, {useContext, useState} from "react";
import { IoIosCloseCircleOutline } from "react-icons/io";
import {GameTypeContext} from "./home";

export function GameRoom({setGameType}) {
    return (<div id={"game-create"}>
            <div id={"blur"}></div>
            <div id={"game-window"}>
                <div id={"close"} onClick={() => setGameType("none")}>
                    <IoIosCloseCircleOutline size={25} color={"red"}/>
                </div>
                <GameType />
            </div>
        </div>
    )
}

function GameType() {
    const [playersCount, setPlayersCount] = useState(2)
    const gameType = useContext(GameTypeContext)

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

    if (gameType === "create") {
        return (<div>
            <div onClick={e => changeCount(e)} id={"players"}>
                <div className={"player-count-selector two-players selected"}>2 игрока</div>
                <div className={"player-count-selector three-players"}>3 игрока</div>
            </div>
            <button id={"create-button"}>Создать</button>
        </div>)
    } else if (gameType === "join") {
        return (<div>
            <h3>Введите id игры</h3>
            <input type={"number"} id={"find-game"} min={1} max={Math.pow(2, 63) - 1}/>
            <button id={"join-button"}>Присоединиться</button>
        </div>)
    }
}
