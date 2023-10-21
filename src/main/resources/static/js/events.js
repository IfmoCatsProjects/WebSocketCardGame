let clicked = false

function cardFromDeckEvent(i) {
    let el = document.getElementById("card" + i);
    el.addEventListener('mouseenter', () => {
        if (!clicked) {
            el.style.border = "2px solid blue"
            el.style.top = "40%"
        }
    })
    el.addEventListener('mouseleave', () => {
        if (!clicked) {
            el.style.border = "1px solid black"
            el.style.top = "41%"
        }
    })

    // el.addEventListener('click', (e) => {
    //     clicked = true
    //     document.getElementById("move-card").style.left = String(e.pageX) - width / 2 + "px"
    //     document.getElementById("move-card").style.top = String(e.pageY) - height / 2 + "px"
    //     createCard("#move-card", cards[i], cards[i], 0, 0)
    //     linkCardToCursor()
    //     removeCard("card" + i)
    //     send({"name": "card" + i}, "action")
    // })
}

function action(players) {
    for (let player of players) {
        if (clicked) {
            player.style.border = "2px solid lime"
            player.style.borderRadius = "20 px"
        }
    }
}

//