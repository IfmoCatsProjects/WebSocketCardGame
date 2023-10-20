cardCreate("#top", "your", "shirt", "0.5%", "46%")
cardCreate("#center", "player1",  "shirt", "41%", "0.5%")
cardCreate("#bottom", "player2","shirt", "", "46%")
cardCreate("#center", "common","spades/6", "41%", "80%")

let offset = 20;
for(let i = 0; i < 32; i++) {
    cardCreate("#center", "card"+i ,"shirt", "41%", offset + "%")
    offset = offset + 1.6;
}

let clicked = false
for(let i = 0; i < 32; i++) {
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

    let cards = getRandomCardDeck();
    el.addEventListener('click', (e) => {
        clicked = true
        cardCreate("#move-card", cards[i], cards[i], 0, 0)
        linkCardToCursor()
        cardRemove("card" + i)
    })
}