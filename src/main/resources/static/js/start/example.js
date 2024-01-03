import React, {useContext} from "react"
import {DataContext} from "../app";

export function Example() {
    const data = useContext(DataContext)

    return (
        <div id={"main"}>
            <b>Имя: </b>{data.name}
            <br />
            <b>Вес: </b>{data.weight}
            <br />
            <b>Рейтинг: </b>{data.rating}
        </div>
    )
}