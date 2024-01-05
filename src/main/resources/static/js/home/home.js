import React, {useEffect, useState} from "react";
import {ajax} from "../utils/requests";
import {createRoot} from "react-dom/client";
import {BiPencil} from "react-icons/bi";
import {Header} from "./header";
import {Table} from "./table";

function Home() {
    const [data, setData] = useState({
        name: "",
        email: "",
        weight: 0,
        rating: 0
    })

    return (<div id={"main"}>
            <Header data={data} setData={setData}/>
        <div id={"main-part"}>
            <div id={"menu"}>
                <div id={"frame"}>
                    <img id={"img-frame"} src={"../images/frame.png"}/>
                    <div id={"menu-buttons"}>
                        <button className={"menu-button create-game"}>Создать игру</button>
                        <button className={"menu-button join-game"}>Присоединиться</button>
                        <button className={"menu-button my-rating"}>Мой рейтинг</button>
                    </div>
                </div>
            </div>
            <div id={"table"}>
                <Table />
            </div>
        </div>

    </div>)
}

createRoot(document.getElementById("root")).render(<Home />)