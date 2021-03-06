package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by michaelhilton on 1/25/16.
 */
public class Game {

    public int score;
    public int type;
    public UDeck d;
    public SDeck e;


    //public java.util.List<Card> deck = new ArrayList<>();


    public java.util.List<java.util.List<Card>> cols = new ArrayList<>();


    public Game(){

        d = new UDeck();
        e = new SDeck();
        type = 0;

        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        cols.add(new ArrayList<Card>());
        score = 0;
    }

    public void gameType (int i) {
        if (i == 0) {
            type = 0;
        }
        else {
            type = 1;
        }
    }

    public void buildDeck() {
        if (type == 0) d.buildDeck();
        else e.buildDeck();
    }

/*
    public void buildDeck() {
        for(int i = 2; i < 15; i++){
            deck.add(new Card(i,Suit.Clubs));
            deck.add(new Card(i,Suit.Hearts));
            deck.add(new Card(i,Suit.Diamonds));
            deck.add(new Card(i,Suit.Spades));
        }
    }
*/

    public void shuffle() {
        if (type == 0) d.shuffle();
        else e.shuffle();
    }

/*
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }
*/

    public void dealFour() {
        for (int i = 0; i < 4; i++) {
            if (type == 0) cols.get(i).add(d.deal());
            else cols.get(i).add(e.deal());
        }
    }

/*
    public void dealFour() {
        for(int i = 0; i < 4; i++){
            cols.get(i).add(deck.get(deck.size()-1));
            deck.remove(deck.size()-1);
        }
    }
*/


    //customDeal to setup game for testing purposes
    public void customDeal(int c1, int c2, int c3, int c4) {
        cols.get(0).add(d.deck.get(c1));
        d.deck.remove(c1);
        cols.get(1).add(d.deck.get(c2));
        d.deck.remove(c2);
        cols.get(2).add(d.deck.get(c3));
        d.deck.remove(c3);
        cols.get(3).add(d.deck.get(c4));
        d.deck.remove(c4);
    }


    public void remove(int columnNumber) {
        if(colHasCards(columnNumber)) {
            Card c = getTopCard(columnNumber);
            boolean removeCard = false;
            for (int i = 0; i < 4; i++) {
                if (i != columnNumber) {
                    if (colHasCards(i)) {
                        Card compare = getTopCard(i);
                        if (compare.getSuit() == c.getSuit()) {
                            if (compare.getValue() > c.getValue()) {
                                removeCard = true;
                            }
                        }
                    }
                }
            }
            if (removeCard) {
                this.cols.get(columnNumber).remove(this.cols.get(columnNumber).size() - 1);
                score++;
            }
        }
    }

    private boolean colHasCards(int colNumber) {
        if(this.cols.get(colNumber).size()>0){
            return true;
        }
        return false;
    }

    private Card getTopCard(int columnNumber) {
        return this.cols.get(columnNumber).get(this.cols.get(columnNumber).size()-1);
    }


    public void move(int colFrom, int colTo) {
        Card cardToMove = getTopCard(colFrom);
        this.removeCardFromCol(colFrom);
        this.addCardToCol(colTo,cardToMove);
    }

    private void addCardToCol(int colTo, Card cardToMove) {
        cols.get(colTo).add(cardToMove);
    }

    private void removeCardFromCol(int colFrom) {
        this.cols.get(colFrom).remove(this.cols.get(colFrom).size()-1);
    }
}
