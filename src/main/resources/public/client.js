class WSBBChannel {
    constructor(callback) {
            this.wsocket = new WebSocket(URL);
            this.wsocket.onopen = (evt) => this.onOpen(evt);
            this.wsocket.onmessage = (evt) => this.onMessage(evt);
            this.wsocket.onerror = (evt) => this.onError(evt);
            this.receivef = callback;
    }


    onOpen(evt) {
            console.log("In onOpen", evt);

    }
    onMessage(evt) {
            console.log("In onMessage", evt);

            if (evt.data != "Connection established.") {
                    let mens = evt.data.split("/");
                    this.receivef(mens);
            }
    }
    onError(evt) {
            console.error("In onError", evt);
    }
}