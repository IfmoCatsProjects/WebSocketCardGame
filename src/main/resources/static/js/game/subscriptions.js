import {subscribe} from "./connection";
import {removeCard} from "./cardManager";

export class MoveSubscriptions {
    static state

    static setSubscriptions(setState, count) {
        subscribe("/players/game/click", (data) => {
            data = JSON.parse(data)
            setState(prevState => ({...prevState, clicked: true, clickedCard: data["card"]}))    //клик по карте в колоде
            removeCard(data["cardPos"])
        })

        subscribe("/players/game/put",(data) => {
            data = JSON.parse(data)
            const finalPos = getRelativeDeck(data["gamePos"], this.state.position, count)

            const playerCard = document.getElementById(String(finalPos))    //положить в чью-то колоду
            playerCard.style.display = ""
            playerCard.src = `../../images/${this.state.clickedCard}.png`

            if (data["gamePos"] - this.state.current === 0)
                setState(prevState => ({...prevState, current: (this.state.current + 1) % count}))

            setState(prevState => ({...prevState, clicked: false, clickedCard: null}))
        })

        subscribe("/players/game/take",(data) => {   //взять карту из своей колоды
            data = JSON.parse(data)
            const finalPos = getRelativeDeck(this.state.current, this.state.position, count)
            const deck = document.getElementById(finalPos)
            setState(prevState => ({...prevState, clicked: true, clickedCard: data["card"]}))

            if(data["subCard"] === "none")
                deck.style.display = "none"
            else
                deck.src = `../../images/${data["subCard"]}.png`
        })

        function getRelativeDeck(gamePos, position, count) {
            let finalPos
            if (gamePos === 3)
                finalPos = 3
            else {
                finalPos = gamePos - position
                finalPos = finalPos === -1 ? count - 1 : finalPos
            }
            return finalPos
        }

    }
}