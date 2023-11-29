let width = 120;
let height = width * 1.5;
function createCard(selector, id, imgName, top, left) {
    const img = document.createElement("img")
    img.id = id
    img.style = `
        width: ${width}px;
        height: ${height}px;
        position: absolute;
        top: ${top};
        left: ${left};
        border: 1px solid black;
        border-radius: 20px;"`
    img.src = `../../images/${imgName}.png`
    document.querySelector(selector).appendChild(img)
    return document.getElementById(id)
}

function removeCard(id) {
    document.getElementById(id).remove()
}

function addCardToDeck(id, imgName) {
    let card = document.getElementById(id)
    card.style.display = ""
    card.src = `../../images/${imgName}.png`
    return card
}

function cardMouseMove(e) {
    const el = document.querySelector('#move-card');
    const target = e.target;
    if (!target) return;

    el.style.left = e.pageX - width/2 + 'px'
    el.style.top = e.pageY - height/2 + 'px';
}
function addCardToCursor(takenCard) {
    let clickedCard = createCard("#move-card", takenCard, takenCard, 0, 0)
    clickedCard.style.pointerEvents = "none"
    window.addEventListener('mousemove', e => cardMouseMove(e))
}

function removeCardFromCursor() {
    window.removeEventListener('mousemove', e => cardMouseMove(e))
}