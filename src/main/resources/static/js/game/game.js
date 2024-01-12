import React, {useEffect, useState} from "react";
import {createRoot} from "react-dom/client";
import { FaUserClock } from "react-icons/fa";
import {ajax} from "../utils/requests";
import {send, subscribe, webSocketGameConnect} from "./connection";
import {Distribution} from "./distribution";

function Game() {
    const [data, setData] = useState({
        playerId: 0,
        gameId: 0,
        name: "",
        email: "",
        weight: 0,
        rating: 0
    })
    const [players, setPlayers] = useState([])

    const leave = () => {
        send({}, "/disconnect")
        const response = ajax("/exit_game", "GET", {})
        response.onload = () => window.location.pathname = "/"
    }

    useEffect(() => {
        const response = ajax("/get_client", "GET", {})
        response.onload = () => {
            const text = JSON.parse(response.responseText)
            setData(text)
            webSocketGameConnect(text.playerId, () => {
                subscribe("/players/game/connectedPlayers", (resp) => {
                    const players = JSON.parse(resp)
                    setPlayers(players["players"])
                })
                subscribe("/players/game/start", (data) => {
                    data = JSON.parse(data)
                    root.render(<Distribution common={data["common"]} move={data["current"]} pos={data["position"]} count={data["count"]}/>)
                })
                send({playerId: text.playerId, gameId: text.gameId, name: text.name}, "connect")
            })
        }
    }, [])

    return (<div id={"main"}>
        <div id={"blur"}></div>
        <div id={"waiting-window"} className={"center"}>
            <h1>ID игры: {data.gameId}</h1>
            <div id={"connected-players"} className={"center"}>
                {getViewPlayers(players, data.playerId).map((e) => e)}
            </div>
            <div id={"logic-buttons"}>
                <button className={"logic-button start-button"} disabled={players.includes(null)} onClick={() => send({}, "/start")}>
                    <b>Старт</b></button>
                <button className={"logic-button leave-button"} onClick={leave}><b>Покинуть</b></button>
            </div>
        </div>
    </div>)
}

function getViewPlayers(players, playerId) {
    let totalPlayers = []
    for (let i = 0; i < players.length; i++) {
        if(players[i] != null)
            totalPlayers.push(<div className={"player connected"}>
                <div id={"black-circle"}>
                    <img className={"profile"} src={"../images/profile.png"}/>
                </div>
                <h3>{players[i].name}</h3>
                {players[i].playerId === playerId ? <h3 id={"you"}>Вы</h3> : ""}
            </div>)
        else
            totalPlayers.push(<div className={"player"}>
                <div id={"user-clock"} className={"center"}>
                    <FaUserClock size={100}/>
                </div>
            </div>)
    }
    return totalPlayers
}

const root = createRoot(document.getElementById("root"))
root.render(<Game />)