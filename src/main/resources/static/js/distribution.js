cardCreate("#top", "your", "shirt", "0.5%", "46%")
cardCreate("#center", "player1",  "shirt", "41%", "0.5%")
cardCreate("#bottom", "player2","shirt", "", "46%")
cardCreate("#center", "common","spades/6", "41%", "91.3%")

let offset = 20;
for(let i = 0; i < 32; i++) {
    cardCreate("#center", "card"+i ,"shirt", "41%", offset + "%")
    offset = offset + 1.6;
}

for(let i = 0; i < 32; i++) {
    let el = document.getElementById("card"+i);
    el.addEventListener('mouseenter', () => {
        el.style.border = "2px solid blue"
        el.style.top = "40%"
    })
    el.addEventListener('mouseleave', () => {
        el.style.border = "1px solid black"
        el.style.top = "41%"
    })
}