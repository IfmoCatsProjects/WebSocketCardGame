
let players = 3

function distribution() {
    let offset = 18;
    for (let i = 0; i < 35; i++) {
        let el = createCard("#center", "card" + i, "shirt", "41%", offset + "%")
        cardFromDeckEvent(i)
        offset = offset + 1.57;
    }
//cardCreate("#bottom", "common", "shirt", "", "46%")
    send({}, "start", (message) => {
        createCard("#center", "common", message, "41%", "81%")
    })

    let graphics = []
    graphics.push(createCard("#bottom", "your-card", "shirt", 0, "46%"))
    graphics.push(createCard("#top", "player1-card", "shirt", "0.5%", "46%"))
    graphics.push(createCard("#center", "player2-card", "shirt", "41%", "0.5%"))
    graphics.forEach((e) => e.style.display = "none")
}
distribution()
