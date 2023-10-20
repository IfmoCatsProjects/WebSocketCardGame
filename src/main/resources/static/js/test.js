let c = [6,7,8,9,10,'j','q','k','a'];
let doc = document.getElementById("cards");
for (let i = 0; i < 9; i++) {
    document.getElementById("cards1").innerHTML += `<img class="card" style="width: 120px; height: 195px;" src="images/spades/${c[i]}.png">`
}
for (let i = 0; i < 9; i++) {
    document.getElementById("cards2").innerHTML += `<img class="card" style="width: 120px; height: 195px;" src="images/hearts/${c[i]}.png">`
}
for (let i = 0; i < 9; i++) {
    document.getElementById("cards3").innerHTML += `<img class="card" style="width: 120px; height: 195px;" src="images/clubs/${c[i]}.png">`
}
for (let i = 0; i < 9; i++) {
    document.getElementById("cards4").innerHTML += `<img class="card" style="width: 120px; height: 195px;" src="images/diamonds/${c[i]}.png">`
}
document.getElementById("cards4").innerHTML += `<img class="card" style="width: 120px; height: 195px;" src="images/shirt.png">`

let b = document.querySelectorAll(".card");
for (let c of b) {
    c.addEventListener('mouseenter', () => {
        c.style.borderWidth = "2px";
        c.style.borderColor = "cyan";
        c.style.borderStyle = "solid";
        c.style.borderRadius = "20px";
        c.style.boxSizing = "border-box";
    });
}
for (let c of b) {
    c.addEventListener('mouseleave', () => {
        c.style.border = "";
    });
}