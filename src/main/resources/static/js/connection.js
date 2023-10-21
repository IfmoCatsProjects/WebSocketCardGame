const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
};

// stompClient.onWebSocketError = (error) => {
//     console.error('Error with websocket', error);
// };
//
// stompClient.onStompError = (frame) => {
//     console.error('Broker reported error: ' + frame.headers['message']);
//     console.error('Additional details: ' + frame.body);
// };

async function send(object, destination, callback) {
    stompClient.onConnect = (frame) => {
        console.log(frame)
        stompClient.publish({
            destination: "/app/" + destination,
            body: JSON.stringify(object)
        });
        stompClient.subscribe('/room/game', (greeting) => {
            callback(JSON.parse(greeting.body).content);
        });
    }
}

stompClient.activate();