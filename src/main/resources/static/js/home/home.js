import React, {useEffect, useState} from "react";
import {ajax} from "../utils/requests";
import {createRoot} from "react-dom/client";
import {BiPencil} from "react-icons/bi";

function Home() {
    const [data, setData] = useState({
        name: "fregrerege",
        email: "regrgrg",
        weight: 0,
        rating: 0
    })

    // useEffect(() => {
    //     const response = ajax("/get_client", "GET", {})
    //     response.onload = () => {
    //         let text = JSON.parse(response.responseText)
    //         setData({name: text.name, email: text.email, rating: text.rating, weight: text.weight})
    //         console.log(data)
    //     }
    // }, [])
    return (<div id={"main"}>
        <header>
            <img id={"profile"} src={"../images/profile.png"}/>
            <div id={"name-email"}>
                <h1 id={"name"}>ИванИванИванИван <BiPencil size={25}/></h1>
                <h5 id={"email"}>iwan.tarasow2013@gmail.com</h5>
            </div>
            <h1 id={"weight"}>Вес: 999 кг <BiPencil size={25}/></h1>
            <div id={"rating"}>
                <img className={"cup"} src={"../images/cup.png"}/>
                <h1>Рейтинг: 2147483647</h1>
                <img className={"cup"} src={"../images/cup.png"}/>
            </div>
        </header>
    </div>)
}

createRoot(document.getElementById("root")).render(<Home />)