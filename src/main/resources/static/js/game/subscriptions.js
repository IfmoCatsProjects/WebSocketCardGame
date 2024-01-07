import {subscribe} from "./connection";

export class MoveSubscriptions {
    static state

    static setSubscriptions(setState) {
        subscribe("/players/game/click", (card) => {
            setState({clicked: true, clickedCard: card})    //клик по карте в колоде
        })

        subscribe("/players/game/put",(id) => {
            let playerCard = document.getElementById(id)    //положить в чью-то колоду
            playerCard.style.display = ""
            playerCard.src = `../../images/${this.state.clickedCard}.png`
            setState({clicked: false, clickedCard: null})
        })

        subscribe("/players/game/take",(msg) => {   //взять карту из своей колоды
            const text = JSON.parse(msg)
            setState({clicked: true, clickedCard: text["card"]})

            if(text["subCard"] === "none")
                document.getElementById("0").style.display = "none"
            else
                document.getElementById("0").src = `../../images/${text["subCard"]}.png`
        })
    }
}