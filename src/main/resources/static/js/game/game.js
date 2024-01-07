import React, {useState} from "react";
import {createRoot} from "react-dom/client";
import { FaUserClock } from "react-icons/fa";
import {ajax} from "../utils/requests";

function Game() {
    const [startGame, setStartGame] = useState(false)

    const leave = () => {
        const response = ajax("/exit/gameId", "GET", {})
        response.onload = () => window.location.pathname = "/"
    }

    if (!startGame)
        return (<div id={"main"}>
            <div id={"blur"}></div>
            <div id={"waiting-window"} className={"center"}>
                <h1>ID игры: 23</h1>
                <div id={"connected-players"} className={"center"}>
                    <div className={"player connected"}>
                        <div id={"black-circle"}>
                            <img className={"profile"} src={"../images/profile.png"}/>
                        </div>
                        <h3>IvanIvanIvanIvan</h3>
                        <p id={"admin"} className={"center"}>Админ</p>
                    </div>
                    <div className={"player"}>
                        <div id={"user-clock"} className={"center"}>
                            <FaUserClock size={100}/>
                        </div>
                    </div>
                    <div className={"player"}>
                        <div id={"user-clock"} className={"center"}>
                            <FaUserClock size={100}/>
                        </div>
                    </div>
                </div>
                <div id={"logic-buttons"}>
                    <button className={"logic-button start-button"} disabled={true}><b>Старт</b></button>
                    <button className={"logic-button leave-button"} onClick={leave}><b>Покинуть</b></button>
                </div>

            </div>
        </div>)
}

const root = createRoot(document.getElementById("root"))
root.render(<Game />)