let callbacks = 0
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    document.getElementById("start").disabled = false
};

function send(object, destination, disposable, callback ) {
    if (stompClient.connected) {
        stompClient.publish({
            destination: "/app/" + destination,
            body: JSON.stringify(object)
        });
        stompClient.subscribe("/room/game", (message) => {
            callback(JSON.parse(message.body).content)
            callbacks++;
            if (disposable) {
                stompClient.unsubscribe(`sub-${callbacks - 1}`);
            }
        });
    }
}
function unsubscribeLast() {
    stompClient.unsubscribe(`sub-${callbacks - 1}`);
}
stompClient.activate();