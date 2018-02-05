# theGatherer Project
theGatherer started out as a question: Can I make money via Magic: the Gathering? Of course I could get really good at the game and travel the country winning tournaments (if only) but I wondered if there was an alternate way. Then came the Standard bannings on January 9th, 2017:
```
Smuggler's Copter; Emrakul, the Promised End; and Reflector Mage—to improve and diversify 
the Standard environment.
```
For non-Magic players, Wizards of the Coast (the company in charge of creating Magic cards and maintaining competitive play), they basically said that the three cards listed above were no longer allowed in sanctioned tournaments. You can use them at home with your friends at the kitchen table but not in any sort of tournaments.   

This had a huge impact on "Emrakul, the Promised End"'s price. It went from being ~$20 per copy to ~$15 overnight to now being ~$8. Another card that was similar to "Emrakul, the Promised End" rose up to fill its spot: "Ulamog, the Ceaseless Hunger". It's price went from $13 to $20 per copy in one day. 

At face value, Ulamog looks like an obviously good card to almost any Magic player. It simply wasn't being played  as much as it could have been because there was a slightly better card to play in its position. This made me wonder if there was a way to look at a card and tell what price it should reach.

This became the basis for my project. Could I identify key variables on a card that dictated its price? Was there a way to look at card and say, "It should be $X, I'm going to buy it now while its cheaper then sell it for profit later."? 

I began learning machine learning through udemy (https://www.udemy.com/machinelearning/learn/v4/overview) so that I could utilize it to better understand how to go about this. I ended up using Java to gather all of my information and Python to run a random forest regression on the data. 

# Getting Started
To run theGatherer and utilize the Card object, you'll need a Java IDE as well as the following libraries installed:
* [HTMLUnit](http://htmlunit.sourceforge.net/) - For webscraping
* [JSON Simple](https://github.com/fangyidong/json-simple) - For parsing the .json file from MTGJSON.com

You'll also want to download the AllSets.json file from [MTGJSON.com](https://mtgjson.com/) and keep it somewhere you know the path to. You won't need to install anything extra for python. We do use some extra libraries but they're imported in the code.

Please note that the recent price function in theGatherer won't work. I decided to remove that section of the code to protect mtggoldfish.com's info. It's legal to use it for non-profit & academic purposes. However, because I can't guarantee everyone who downloads it via github will do the same, I have set the function to return $0.00 as the price for each card.

# Running the Price Analysis with Python
First you'll want to download the Master12718.csv and RIX.csv. The first file has all Modern legal cards and their prices as of January 27th, 2018. The RIX file contains all of the cards from Rivals of Ixalan. The python code is set up to try its prediction model on the newest cards (Rivals of Ixalan), the RIX cards are not included in the Master set to avoid training the set to those cards. 

The python set-up is pretty simple. Simply set your working directory to the folder containing the .csv files. If you change the name of the master .csv file, you'll need to edit the "csv" field on line 9 of the python code.

## Authors
[Dylan Dilla](https://www.linkedin.com/in/dylandilla/) (Link goes to my LinkedIn)

## Acknowledgements
Computer Science Department of Bowdoin College and my computer science mentor, Allen Harper, whose guidance made this project possible.
