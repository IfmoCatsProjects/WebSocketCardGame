let width = 120;
let height = width * 1.5;
function cardCreate(selector, id, imgName, top, left) {
    let node = document.querySelector(selector);
    node.innerHTML += `<img id="${id}" 
    style="
        width: ${width}px;
        height: ${height}px;
        position: absolute;
        top: ${top};
        left: ${left};
        border: 1px solid black;
        border-radius: 20px;"
    src="images/${imgName}.png">`
    return document.getElementById(id)
}

function cardRemove(id) {
    document.getElementById(id).remove()
}

function getRandomCardDeck() {
    let suits = ["spades", "clubs", "hearts", "diamonds"]
    let dignities = ["6", "7", "8", "9", "10", "j", "q", "k", "a"]
    let cards = []
    for (let suit of suits) {
        for (let dignity of dignities) {
            cards.push(suit + "/" + dignity)
        }
    }
    let randomDeck = []
    let size = 35
    for (let i = 0; i < 36; i++) {
        j = Math.round(Math.random() * size--)
        randomDeck.push(cards[j])
        cards.splice(j, 1)
    }
    return randomDeck
}

function linkCardToCursor() {
    window.addEventListener('mousemove', e => {
        const el = document.querySelector('#move-card');
        const target = e.target;
        if (!target) return;

        el.style.left = e.pageX - width/2 + 'px'
        el.style.top = e.pageY - height/2 + 'px';
    })
}

function removeFromDeck(pos) {
    cards.splice(pos, 1)
}

let cards = getRandomCardDeck()

function action(players) {
    for (let player of players) {
        if (clicked) {
            player.style.border = "2px solid lime"
            player.style.borderRadius = "20 px"
        }
    }
}