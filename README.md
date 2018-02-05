# theGatherer Project
theGatherer started out as a question: Can I make money via Magic: the Gathering? Of course I could get really good at the game and travel the countries winning tournaments (if only) but I wondered if there were an alterate way. Then came the Standard bannings on January 9th, 2017:
```
Smuggler's Copter; Emrakul, the Promised End; and Reflector Mage—to improve and diversify 
the Standard environment.
```
For non-Magic players, Wizards of the Coast (the company in charge of creating Magic cards and maintaining competitive play), they basically said that the three cards listed above were no longer allowed in sanctioned tournaments. You can use them at home with your friends at the kitchen table but not in any sort of tournaments.   

This had a huge impact on "Emrakul, the Promised End"'s price. It went from being ~$20 per copy to ~$15 overnight to now being ~$8. Another card that was similar to "Emrakul, the Promised End" rose up to fill its spot: "Ulamog, the Ceaseless Hunger". It's price went from $13 to $20 per copy in one day. 

At face value, Ulamog looks like an obviously good card to almost any Magic player. It simply wasn't being played  as much as it could have been because there was a slightly better card to play in its position. 

This became the basis for my project. Could I identify key variables on a card that dictated its price? Was there a way to look at card and say, "It should be $X, I'm going to buy it now while its cheaper then sell it for profit later."? 

I began learning machine learning through udemy (https://www.udemy.com/machinelearning/learn/v4/overview) so that I could utilize it to better understand how to go about this. I ended up using Java to gather all of my information and Python to run a random forest regression on the data. 

# Getting Started
To run theGatherer and utilize the Card object, you'll need a Java IDE as well as the following libraries installed:
* [HTMLUnit](http://htmlunit.sourceforge.net/) - For webscraping
* [JSON Simple](https://github.com/fangyidong/json-simple) - For parsing the .json file from MTGJSON.com

You'll also want to download the AllSets.json file from [MTGJSON.com](https://mtgjson.com/) and keep it somewhere you know the path to.
