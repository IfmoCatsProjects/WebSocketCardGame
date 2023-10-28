let clicked = false
function cardFromDeckEvent(players, i) {
    let el = document.getElementById("card" + i);
    el.addEventListener('mouseenter', () => {
        if (!clicked) {
            el.style.border = "2px solid blue"
            el.style.top = "15vh"
            el.style.cursor = "pointer"
        }
    })
    el.addEventListener('mouseleave', () => {
        if (!clicked) {
            el.style.border = "1px solid black"
            el.style.top = "16vh"
            el.style.cursor = "default"
        }
    })

    el.addEventListener('click', (e) => {
        if (!clicked) {
            clicked = true
            document.getElementById("move-card").style.left = String(e.pageX)  + "px"
            document.getElementById("move-card").style.top = String(e.pageY)  + "px"
            removeCard("card" + i)
            send({"number": i}, "click", true, (card) => {
                let clicked = createCard("#move-card", card, card, 0, 0)
                clicked.style.pointerEvents = "none"
                addCardToCursor()
                framesOn()
            })
        }
    })
}

function framesOn() {
    document.querySelectorAll(".frame").forEach(e => e.style.display = "")
}

function framesOff() {
    document.querySelectorAll(".frame").forEach(e => e.style.display = "none")
}

function put() {
    for (let frame of document.querySelectorAll(".frame")) {
        frame.addEventListener('click', () => {
            let player = frame.className.split(" ")[1]
            console.log(player)
            if (clicked) {
                let card = document.getElementById("move-card").firstElementChild
                send({"number": player, "data": card.id}, "put", true, () => {
                    clicked = false
                    removeCardFromCursor()
                    removeCard(card.id)
                    addCardToDeck(player, card.id)
                    framesOff()
                })
            }
        })
    }
}