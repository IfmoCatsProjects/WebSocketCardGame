let clicked = false

function cardFromDeckEvent(players, i) {
    let el = document.getElementById("card" + i);
    el.addEventListener('mouseenter', () => {
        if (!clicked) {
            el.style.border = "2px solid blue"
            el.style.top = "15vh"
        }
    })
    el.addEventListener('mouseleave', () => {
        if (!clicked) {
            el.style.border = "1px solid black"
            el.style.top = "16vh"
        }
    })

    el.addEventListener('click', (e) => {
        if (!clicked) {
            clicked = true
            document.getElementById("move-card").style.left = String(e.pageX)  + "px"
            document.getElementById("move-card").style.top = String(e.pageY)  + "px"
            removeCard("card" + i)
            send({"data": "card" + i}, "click", true, (card) => {
                createCard("#move-card", card, card, 0, 0)
                console.log(card)
            })
            linkCardToCursor()
            createFrames(players)
        }
    })
}

function createFrames(players) {
    for (let player of players) {
        let frame = document.createElement('div')
        frame.className = "frame"

        frame.style.width = "120px"
        frame.style.height = player.style.height
        frame.style.position = "absolute"
        frame.style.top = player.style.top
        frame.style.left = player.style.left
        frame.style.border = "3px solid lime"
        frame.style.borderRadius = "20px"

        document.getElementById(player.parentNode.id).appendChild(frame)
    }
}

//