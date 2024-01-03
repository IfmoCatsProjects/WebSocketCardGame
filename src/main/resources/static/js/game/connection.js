let callbacks = 0
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/pig'
});
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    document.getElementById("start").disabled = false
};

export function send(object, destination, disposable, callback ) {
    if (stompClient.connected) {
        stompClient.publish({
            destination: "/app/" + destination,
            body: JSON.stringify(object)
        });
        stompClient.subscribe("/players/game", (message) => {
            callback(message.body)
            callbacks++;
            if (disposable) {
                stompClient.unsubscribe(`sub-${callbacks - 1}`);
            }
        });
    }
}

stompClient.activate();