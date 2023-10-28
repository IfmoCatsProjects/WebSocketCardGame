
function distribution() {
    let smallDecks = []
    smallDecks.push(createCard("#bottom", "0", "shirt", 0, "46vw"))
    smallDecks.push(createCard("#top", "1", "shirt", "0.5vh", "46vw"))
    smallDecks.push(createCard("#center", "2", "shirt", "16vh", "0.5vw"))
    smallDecks.forEach((e) => e.style.display = "none")
    smallDecks.push(document.getElementById("3"))

    let offset = 18;
    for (let i = 0; i < 35; i++) {
        createCard("#center", "card" + i, "shirt", "16vh", offset + "vw")
        cardFromDeckEvent(smallDecks, i)
        offset = offset + 1.57;
    }
    createFrames(smallDecks)
    put(smallDecks.length)
}

function createFrames(players) {
    for (let player of players) {
        let frame = document.createElement('div')

        frame.className = "frame " + player.id
        frame.style.width = "120px"
        frame.style.height = player.style.height
        frame.style.position = "absolute"
        frame.style.top = player.style.top
        frame.style.left = player.style.left
        frame.style.border = "3px solid lime"
        frame.style.borderRadius = "20px"
        frame.style.display = "none"

        frame.addEventListener("mouseenter", () => {
            frame.style.cursor = "pointer"
        })
        frame.addEventListener("mouseleave", () => {
            frame.style.cursor = "default"
        })
        document.getElementById(player.parentNode.id).appendChild(frame)
    }
}

document.getElementById("start").addEventListener("click", () => {
    send({"number": 3}, "start", true, (message) => {
        createCard("#center", "3", message, "16vh", "81vw")
        distribution()
    })
})
