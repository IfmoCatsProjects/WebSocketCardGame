let offset = 18;
let players = 3

for(let i = 0; i < 35; i++) {
    cardCreate("#center", "card"+i ,"shirt", "41%", offset + "%")
    offset = offset + 1.57;
}
//cardCreate("#bottom", "common", "shirt", "", "46%")
cardCreate("#center", "common",cards[0], "41%", "81%")
removeFromDeck(0)

let yourCard = cardCreate("#bottom", "your-card", "shirt", 0, "46%")
let player1Card = cardCreate("#top", "player1-card", "shirt", "0.5%", "46%")
let player2Card = cardCreate("#center", "player2-card", "shirt", "41%", "0.5%")

yourCard.style.display = "none"
player1Card.style.display = "none"
player2Card.style.display = "none"
