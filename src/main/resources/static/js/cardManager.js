let width = 120;
let height = width * 1.5;
function createCard(selector, id, imgName, top, left) {
    const img = document.createElement("img")
    img.id = id
    img.style = `style="
        width: ${width}px;
        height: ${height}px;
        position: absolute;
        top: ${top};
        left: ${left};
        border: 1px solid black;
        border-radius: 20px;"`
    img.src = `images/${imgName}.png`
    document.querySelector(selector).appendChild(img)
    return document.getElementById(id)
}

function removeCard(id) {
    document.getElementById(id).remove()
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

function action(players) {
    for (let player of players) {
        if (clicked) {
            player.style.border = "2px solid lime"
            player.style.borderRadius = "20 px"
        }
    }
}