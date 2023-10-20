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
}