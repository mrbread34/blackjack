# 4kuhdcasino — Blackjack

A console Blackjack game written in Java.

## Features

- Start with a $100 bank and bet on each round
- Hit, Stand, Double Down, Split, and Surrender
- Insurance when the dealer shows an Ace
- Blackjack pays 3 to 2
- Dealer stands on 17
- Special move: spend $50 for a guaranteed Ace
- Coloured output and Unicode card suits

## Requirements

- JDK 25 (the code uses text blocks, so JDK 15+ is the minimum)

## Running

### IntelliJ IDEA

1. Open the project folder.
2. Make sure a JDK is set under **File → Project Structure → Project → SDK**.
3. Open `src/Main.java` and click the green ▶ next to `main`.

### Command line

From the project folder:

```
javac -d bin src/*.java
java -cp bin Main
```

> **Windows:** run `chcp 65001` in the terminal first so the card suits (♠ ♥ ♦ ♣) display correctly.

## Project Structure

```
src/
  Main.java             Entry point and menu
  BlackjackGame.java    Game loop, betting, and round results
  BlackjackPlayer.java  Player hands, bets, and splitting
  BlackjackDealer.java  Dealer logic (stands on 17, hidden hole card)
  Deck.java             Deck creation, shuffling, and drawing
  Hand.java             Hand value, bust, and blackjack checks
  Card.java             A single card
  Rank.java / Suit.java Card ranks and suits
  Currency.java         Player balance
  Ansi.java             Terminal colour codes
```
