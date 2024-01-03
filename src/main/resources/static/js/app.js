import React, {createContext, useEffect, useState} from "react";
import {Start} from "./start/start";
import {createRoot} from "react-dom/client";
import {Example} from "./start/example";

export const DataContext = createContext(null)

function App() {
    const [page, setPage] = useState("start")
    const [data, setData] = useState({
        name: "",
        weight: "",
        rating: "",
    })
    switch (page) {
        case "start": {
            return (
                <DataContext.Provider value={{setData, setPage}}>
                    <Start />
                </DataContext.Provider>
            )
        }
        case "game":
            return (
                <DataContext.Provider value={data}>
                    <Example />
                </DataContext.Provider>
            )
    }
}

createRoot(document.getElementById("root")).render(<App />)