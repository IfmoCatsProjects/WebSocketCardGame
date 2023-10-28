package org.ioanntar.webproject.logic;

import java.util.*;

public class Cards {

    private LinkedList<String> deck = new LinkedList<>();

    public Cards() {
        generateRandomDeck();
    }

    private void generateRandomDeck() {
        String[] suits = {"spades", "clubs", "hearts", "diamonds"};
        String[] dignities = {"6", "7", "8", "9", "10", "j", "q", "k", "a"};
        List<String> cards = new LinkedList<>();
        for (String suit: suits) {
            for (String dignity: dignities) {
                cards.add(suit + "/" + dignity);
            }
        }
        int size = 35;
        for (int i = 0; i < 36; i++) {
            int j = (int) Math.round(Math.random() * size--);
            deck.add(cards.get(j));
            cards.remove(j);
        }
    }

    public LinkedList<String> getDeck() {
        return deck;
    }

    public void remove(int index) {
        deck.set(index, null);
    }

    public String get(int key) {
        return deck.get(key);
    }
    public void clear() {
        deck.clear();
    }

    @Override
    public String toString() {
        return "Cards{" +
                "deck=" + deck +
                '}';
    }
}
