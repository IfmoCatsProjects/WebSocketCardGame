import React, {createContext, useState} from "react";
import {createRoot} from "react-dom/client";
import {Header} from "./header";
import {Table} from "./table";
import {GameRoom} from "./gameRoom";

export const GameTypeContext = createContext(null)

function Home() {
    const [data, setData] = useState({
        name: "",
        email: "",
        weight: 0,
        rating: 0
    })
    const [gameType, setGameType] = useState("none")

    return (<div id={"main"}>
            <Header data={data} setData={setData}/>
        <div id={"main-part"}>
            <div id={"menu"}>
                <div id={"frame"}>
                    <img id={"img-frame"} src={"../images/frame.png"}/>
                    <div id={"menu-buttons"}>
                        <button className={"menu-button create-game"} onClick={() => setGameType("create")}>Создать игру</button>
                        <button className={"menu-button join-game"} onClick={() => setGameType("join")}>Присоединиться</button>
                        <button className={"menu-button my-rating"}>Мой рейтинг</button>
                    </div>
                </div>
            </div>
            <div id={"table"}>
                <Table />
            </div>
        </div>
        {gameType !== "none" ?
            <GameTypeContext.Provider value={gameType}>
                <GameRoom setGameType={setGameType} gameType={gameType}/>
            </GameTypeContext.Provider> : ""}
    </div>)
}

createRoot(document.getElementById("root")).render(<Home />)