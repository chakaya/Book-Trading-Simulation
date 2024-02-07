# Book-Trading-Simulation

## Overview 

This Java program simulates a simple book trading scenario where some agents sell books and others buy books on behalf of users. It follows a variant of the Contract-Net protocol and includes buyer agents, seller agents, and an optional broker agent.

## Components

* Book.java: Represents a book with title and price.
* BuyerAgent.java: Simulates a buyer agent.
* SellerAgent.java: Represents a seller agent with a book catalogue.
* BrokerAgent.java: Optional intermediary between buyers and sellers.
* MarketStatus.java: Utility class for market status.
* Main.java: Sets up and initiates the simulation.

## How it works

1. Initialization: Seller agents with catalogues, buyer agents with target book titles(These titles are passed as command-line arguments), and an optional broker agent.
2. Simulation Process: Buyers request offers, sellers respond, and buyers purchase book at the lowest price.
3. Market Closure: Closes when all buyers are done or sellers have sold all books.
4. Termination: Program ends when market closes.

## Running the program

Compile: javac *.java.
Run with book titles as arguments, e.g., java Main "Java Programming" "Sapiens".
Enter books for each seller as prompted.
