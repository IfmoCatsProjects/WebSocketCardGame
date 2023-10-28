package org.ioanntar.webproject.modules;

public class Request {
    private String data;
    private int number;

    public Request() {
    }

    public Request(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Request{" +
                "data='" + data + '\'' +
                ", playerDeck='" + number + '\'' +
                '}';
    }
}
