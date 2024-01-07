import SockJS from "sockjs-client";
import Stomp from "stompjs"

const socket = new SockJS("/pig")
const stompClient = Stomp.over(socket)
// stompClient.debug = null

stompClient.connect({id: "12"}, () => {
    document.getElementById("start").disabled = false
})

export function send(object, destination) {
    if (stompClient.connected) {
        stompClient.send(`/app/${destination}`, {}, JSON.stringify(object))
    }
}

export function subscribe(point, callback) {
    stompClient.subscribe(point, (message) => {
        callback(message.body)
    })
}