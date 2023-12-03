let color = 1

function changeBackground() {
    let colors = ["yellow", "black", "blue", "green", "yellow"]
    let back = document.getElementById("background")
    let j = 0
    for (let i = 0; i <= 200; i++) {
        setTimeout(() => {
            if (i < 100)
                j++
            back.style.backgroundImage = `linear-gradient(to top, ${colors[color]} ${-100 + i}%, ${colors[color-1]} ${j}%)`
        },20 * i)
    }
    setTimeout(() => {
        color++
        if (color === colors.length)
            color = 1
    }, 4000)
}

function changeLines() {
    let colors = ["pink", "red", "yellow", "black"]
    let lines = document.querySelectorAll(".line.move")
    lines.forEach(e => e.style.backgroundColor = colors[color - 1])
    for (let i = 0; i <= 100; i++) {
        setTimeout(() => {
            lines.forEach(e => e.style.width = `${i}vw`)
        },40 * i)
    }
    setTimeout(() => {
        document.querySelectorAll(".line.static").forEach(e => e.style.backgroundColor = colors[color - 1])
    }, 3999)
}

function passwordEqual() {
    document.querySelector(".submit").addEventListener("click", (e) => {
        if (document.getElementById("pass").value !== document.getElementById("repass").value) {
            document.getElementById("error-passwords").innerText = "Пароли не совпадают!"
            e.preventDefault()
        }
    })
}

changeLines()
changeBackground()

setInterval(changeBackground, 7000)
setInterval(changeLines, 7000)

passwordEqual()

document.body.addEventListener("click", (e) => {
    if (!e.target.classList.contains("submit")) {
        document.querySelectorAll("h5").forEach(e => e.innerText = "")
    }
})
