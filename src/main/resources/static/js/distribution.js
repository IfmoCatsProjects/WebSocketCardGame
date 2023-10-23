
function distribution() {
    let smallDecks = []
    smallDecks.push(createCard("#bottom", "your-card", "shirt", 0, "46vw"))
    smallDecks.push(createCard("#top", "player1-card", "shirt", "0.5vh", "46vw"))
    smallDecks.push(createCard("#center", "player2-card", "shirt", "16vh", "0.5vw"))
    smallDecks.forEach((e) => e.style.visibility = "hidden")
    smallDecks.push(document.getElementById("common"))

    let offset = 18;
    for (let i = 0; i < 35; i++) {
        let el = createCard("#center", "card" + i, "shirt", "16vh", offset + "vw")
        cardFromDeckEvent(smallDecks, i)
        offset = offset + 1.57;
    }
//cardCreate("#bottom", "common", "shirt", "", "46%")
}

document.getElementById("start").addEventListener("click", () => {
    send({}, "start", true, (message) => {
        createCard("#center", "common", message, "16vh", "81vw")
        distribution()
    })
})
