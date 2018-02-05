package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * @author      Dylan Dilla (ddilla@bowdoin.edu)
 * @version     1.0
 */

/**
 * Stores all Modern-legal card information to a master .csv file and by sets.
 */
public class theGathererV1 {

    // create the master file which will hold all cards
    /**
     * This master file will be a .csv file saved to the working directory. It will contain every single card.
     */
    public static PrintWriter MASTER = createSetFile("master");

    /**
     * This function gathers Magic: the Gathering card info from the AllSets.json file housed on mtgjson.com.
     * It stores card information in .csv files by set. It also stores all card info in a master set defined
     * above.
     * @param args Not used
     */
    public static void main(String[] args) {
        try{
            MASTER = new PrintWriter("master.csv");
        } catch (java.io.FileNotFoundException ex){
            ex.printStackTrace();
        }

        // Goal: Take all cards from the JSON file and create a Card object around each card's data.
        // That card object will be passed to a function which writes the below features to a .csv file.

        // Name
        // Name length
        // Rules length
        // Flavor length
        // Artist length
        // Type length
        // Total length
        // # Colors
        // # Color Identities
        // Difference in Colors and Identity
        // # Types
        // # Subtypes
        // VariablePT
        // CMC	Power
        // Toughness
        // P+T
        // P+T/CMC
        // P/CMC
        // T/CMC
        // Rarity
        // isLegendary
        // isInstant
        // isSorcery
        // isEnchantment
        // isArtifact
        // isCreature
        // isLand
        // isPlaneswalker
        // # Mana Symbols
        // recentPrice

        // sets done
        /* , "MRD", "DST", "5DN", "CHK", "BOK", "SOK", "9ED", "RAV", "GPT", "DIS", "CSP",
        "TSP", "PLC", "FUT", "10E", "LRW", "MOR", "SHM", "EVE", "ALA", "CON", "ARB", "M10", "ZEN",
                "WWK", "ROE", "M11", "SOM", "MBS", "NPH", "M12", "ISD", "DKA", "AVR", "M13", "RTR", "GTC",
                "DGM", "M14", "THS", "BNG", "JOU", "M15", "KTK", "FRF", "DTK", "ORI", "BFZ", "OGW", "SOI",
                "EMN", "KLD", "AER", "AKH", "HOU", "XLN"*/

        String[] codes = {"RIX"};

        String fileName = "C:\\CompSci\\MagicProject\\AllSets.json\\AllSets.json";



        for (String code : codes) {

            // Create the file for the set. The file will be put in the same folder as this .java file
            // Unlike the master file, each set file will only have the cards in its set.
            System.out.println(code);
            PrintWriter setFile = createSetFile(code);

            JSONParser parser = new JSONParser();

            Object obj = null;

            try {
                obj = parser.parse(new FileReader(fileName));
            } catch (java.io.FileNotFoundException ex){
                ex.printStackTrace();
            } catch (java.io.IOException ex){
                ex.printStackTrace();
            } catch (org.json.simple.parser.ParseException ex){
                ex.printStackTrace();
            }

            // fetch our list of cards
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject set = (JSONObject) jsonObject.get(code);
            JSONArray cards = (JSONArray) set.get("cards");
            String setName = (String) set.get("name");

            int size = cards.size();

            for (int i = 0; i < size; i++) {

                // Create a card object
                JSONObject card = (JSONObject) cards.get(i);
                CardV1 mtgCard = new CardV1(card, setName);

                System.out.println(mtgCard.getName());

                // Write the card to the set file. This will also add it to the master file.
                insertCard(mtgCard, setFile);
            }
        }
    }

    /**
     * Returns a PrintWriter object as a .csv that can be written on with StringBuilder.
     * The String parameter must be a file that does not exist in the working directory.
     * The .csv header contains Name, Name length, Rules length, Flavor length, Artist length,
     * Type length, Total length, # Colors, # Color Identities, Difference in Color and Identities ,
     * # Types, # Subtypes, VariablePT, CMC, Power, Toughness, Power + Toughness, P+T/CMC, P/CMC, T/CMC,
     * Rarity, isLegendary, isInstant, isSorcery, isEnchantment, isArtifact, isCreature, isLand,
     * isPlaneswalker, # Mana Symbols, and the most recent price as features. The recent price is not
     * a feature but is used for creation of dependent variables.
     * @param code The set code. The set code was used to help make organization of the .csv files easier.
     * @return     A writable .csv as a PrintWriter object.
     */
    private static PrintWriter createSetFile(String code){

        // create the file for the set
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(code + ".csv"));
        }
        catch(java.io.FileNotFoundException ex){
            System.out.println("Error creating a .csv file for " + code + ".");
            ex.printStackTrace();
        }

        // assemble the header string
        StringBuilder sb = new StringBuilder();

        sb.append("Name");
        sb.append(',');
        sb.append("Name length");
        sb.append(',');
        sb.append("Rules length");
        sb.append(',');
        sb.append("Flavor length");
        sb.append(',');
        sb.append("Artist length");
        sb.append(',');
        sb.append("Type length");
        sb.append(',');
        sb.append("Total length");
        sb.append(',');
        sb.append("# Colors");
        sb.append(',');
        sb.append("# Color Identities");
        sb.append(',');
        sb.append("Difference in Colors and Identity");
        sb.append(',');
        sb.append("# Types");
        sb.append(',');
        sb.append("# Subtypes");
        sb.append(',');
        sb.append("VariablePT");
        sb.append(',');
        sb.append("CMC");
        sb.append(',');
        sb.append("Power");
        sb.append(',');
        sb.append("Toughness");
        sb.append(',');
        sb.append("P+T");
        sb.append(',');
        sb.append("P+T/CMC");
        sb.append(',');
        sb.append("P/CMC");
        sb.append(',');
        sb.append("T/CMC");
        sb.append(',');
        sb.append("Rarity");
        sb.append(',');
        sb.append("isLegendary");
        sb.append(',');
        sb.append("isInstant");
        sb.append(',');
        sb.append("isSorcery");
        sb.append(',');
        sb.append("isEnchantment");
        sb.append(',');
        sb.append("isArtifact");
        sb.append(',');
        sb.append("isCreature");
        sb.append(',');
        sb.append("isLand");
        sb.append(',');
        sb.append("isPlaneswalker");
        sb.append(',');
        sb.append("# Mana Symbols");
        sb.append(',');
        sb.append("recentPrice");
        sb.append('\n');

        pw.write(sb.toString());

        return pw;
    }

    /**
     * This method inserts a card's info onto the .csv file contained in the  given PrintWriter object.
     * For a list of the card's feature added to the file, please view the createSetFile method.
     * @param card The Card object is passed through here. All information is passed through the Card object
     *             constructor first to ensure no values or missing.
     * @param pw   This PrintWriter object contains the .csv file that the card will be written to.
     * @see Card
     */
    private static void insertCard(CardV1 card, PrintWriter pw){

        StringBuilder cardString = new StringBuilder();

        // Append card name
        cardString.append(card.getName());
        cardString.append(',');

        // Append card name length
        cardString.append(card.getName().length());
        cardString.append(',');

        // Append card rules length
        cardString.append(card.convertRulesText());
        cardString.append(',');

        // Append card flavor length
        cardString.append(card.convertFlavorText());
        cardString.append(',');

        // Append artist length
        cardString.append(card.convertArtist());
        cardString.append(',');

        // Append line length
        cardString.append(card.typeLineLength());
        cardString.append(',');

        // Append total length
        double textTotal = card.getName().length() + card.convertRulesText() + card.convertFlavorText() +
                card.convertArtist() + card.typeLineLength();
        cardString.append(Double.toString(textTotal));
        cardString.append(',');

        // Append # colors
        cardString.append(card.numberColors());
        cardString.append(',');

        // Append # color identities
        cardString.append(card.numberColorsIdentity());
        cardString.append(',');

        // Append color/identity difference
        cardString.append(card.colorsDifference());
        cardString.append(',');

        // Append # types
        cardString.append(card.numTypes());
        cardString.append(',');

        // Append # subtypes
        cardString.append(card.numSubtypes());
        cardString.append(',');

        // Append variablePT
        if (card.hasVariablePT()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append CMC
        cardString.append(card.getCmc());
        cardString.append(',');

        if (!card.hasVariablePT()) {
            // Append Power
            cardString.append(card.getPower());
            cardString.append(',');

            // Append Toughness
            cardString.append(card.getToughness());
            cardString.append(',');

            // Append P+T
            double totalPT = card.getPowerDouble() + card.getToughnessDouble();
            cardString.append(totalPT);
            cardString.append(',');

            // Append P+T/CMC
            cardString.append(totalPT / card.getCmc());
            cardString.append(',');

            // Append P/CMC
            cardString.append((card.getPowerDouble()) / (card.getCmc()));
            cardString.append(',');

            // Append T/CMC
            cardString.append((card.getToughnessDouble()) / (card.getCmc()));
            cardString.append(',');
        } else {
            // Append Power
            cardString.append(0);
            cardString.append(',');

            // Append Toughness
            cardString.append(0);
            cardString.append(',');

            // Append P+T
            cardString.append(0);
            cardString.append(',');

            // Append P+T/CMC
            cardString.append(0);
            cardString.append(',');

            // Append P/CMC
            cardString.append(0);
            cardString.append(',');

            // Append T/CMC
            cardString.append(0);
            cardString.append(',');
        }
        // Append Rarity
        cardString.append(card.getRarity());
        cardString.append(',');

        // Categorical values
        // Append isLegendary
        if (card.isLegendary()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isInstant
        if (card.isInstant()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isSorcery
        if (card.isSorcery()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isEnchantment
        if (card.isEnchantment()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isArtifact
        if (card.isArtifact()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isCreature
        if (card.isCreature()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isLand
        if (card.isLand()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append isPlaneswalker
        if (card.isPlaneswalker()) {
            cardString.append(1);
        } else {
            cardString.append(0);
        }
        cardString.append(',');

        // Append # Mana Symbols
        cardString.append(card.numberManaSymbols());
        cardString.append(',');

        // Append recentPrice
        cardString.append(card.getPriceString());
        cardString.append('\n');

        pw.write(cardString.toString());
        MASTER.write(cardString.toString());

        pw.flush();
        MASTER.flush();

    }
}
