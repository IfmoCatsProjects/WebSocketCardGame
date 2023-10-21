package org.ioanntar.webproject.logic;

import java.util.*;

public class Cards {
    private LinkedHashMap<String, String> deck = new LinkedHashMap<>();
    private static Cards cards;

    public static Cards getCards() {
        if (cards == null) {
            cards = new Cards();
        }
        return cards;
    }

    private Cards() {}

    public void generateRandomDeck() {
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
            deck.put("card" + i, cards.get(j));
            cards.remove(j);
        }
    }

    public LinkedHashMap<String, String> getDeck() {
        return deck;
    }

    public void remove(String key) {
        deck.remove(key);
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
