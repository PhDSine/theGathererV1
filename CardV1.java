package com.company;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Stores the Magic: the Gathering card as an  object. This Object ensures no data types are missing and gives us
 * a consistent way of accessing information for storage into the .csv files.
 */

public class CardV1 {

    private String artist;
    private double cmc;
    private String[] colorIdentity;
    private String[] colors;
    private String flavor;
    private String layout;
    private String manaCost;
    private String name;
    private String names[];
    private String power;
    private String rarity;
    private String[] subtypes;
    private String[] supertypes;
    private String text;
    private String toughness;
    private String type;
    private String[] types;
    private double[] variations;
    private String watermark;
    private String setName;

    public CardV1(String artist, double cmc, String[] colorIdentity, String[] colors, String flavor, String layout,
                String manaCost, String name, String[] names, String power, String rarity,
                String[] subtypes, String[] supertypes, String text, String toughness, String type,
                String[] types, double[] variations, String watermark, String setName) {
        this.artist = artist;
        this.cmc = cmc;
        this.colorIdentity = colorIdentity;
        this.colors = colors;
        this.flavor = flavor;
        this.layout = layout;
        this.manaCost = manaCost;
        this.name = name;
        this.names = names;
        this.power = power;
        this.rarity = rarity;
        this.subtypes = subtypes;
        this.supertypes = supertypes;
        this.text = text;
        this.toughness = toughness;
        this.type = type;
        this.types = types;
        this.variations = variations;
        this.watermark = watermark;
        this.setName = setName;
    }

    public CardV1(JSONObject card, String setName){

        // Overhead required for pricing URL
        JSONParser parser = new JSONParser();
        //String fileName = "C:\\CompSci\\MagicProject\\AllSets.json\\AllSets.json";
        String[] lands = {"Plains", "Forest", "Mountain", "Island", "Swamp", "Wastes"};

        this.setName = setName;

        // 1. Get the name of the artist
        this.artist = (String) card.get("artist");

        // 2. Get the cmc of the card
        //Both longs and doubles can appear. Cast to Number than cast to double
        Number convertedCost = (Number) card.get("cmc");
        double cmc = 0;
        if (convertedCost != null) {
            this.cmc = convertedCost.doubleValue();
        }
        // 3. Get the color identity array
        this.colorIdentity = JSONtoStringArray("colorIdentity", card);

        // 4. Get the color array
        this.colors = JSONtoStringArray("colors", card);

        // 5. Get the flavor of the card
        // Not all cards have flavor text so this can be null
        this.flavor = (String) card.get("flavor");

        // 6. Get the layout of the card
        this.layout = (String) card.get("layout");

        // 7. Get the manacost of the card
        this.manaCost = (String) card.get("manaCost");

        // 8. Get the name of the card
        String nameF = (String) card.get("name");
        this.name = nameF.replaceAll(",", " ");

        // 9. Get the names array of the card. Only used for meld, split, flip, and dfc
        this.names = JSONtoStringArray("names", card);

        // 10. Get the power of the card
        this.power = (String) card.get("power");
        if (!StringUtils.isNumeric(this.power)){
            this.power = "0";
        }

        // 11. Get the rarity of the card
        this.rarity = (String) card.get("rarity");

        // 12. Get subtype array
        this.subtypes = JSONtoStringArray("subtypes", card);

        // 13. Get the supertype array
        this.supertypes = JSONtoStringArray("supertypes", card);

        // 14. Get the rules text of the card
        this.text = (String) card.get("text");

        // 15. Get the toughness of the card
        this.toughness = (String) card.get("toughness");
        if (!StringUtils.isNumeric(this.toughness)){
            this.toughness = "0";
        }

        // 16. Get the type of the card
        this.type = (String) card.get("type");

        // 17. Get type array
        this.types = JSONtoStringArray("types", card);

        // 18. Get variations array. Only exists if there are different arts
        this.variations = JSONtoDoubleArray("variations", card);

        // 19. Get watermark
        this.watermark = (String) card.get("watermark");

    }

    //Methods
    public String toStringShort() {
        return "Name: " + this.name + "\n" +
                "Mana Cost: " + this.manaCost + "\n" +
                "Type: " + this.type + "\n" +
                "Text: " + this.text;

    }

    public String toString(){
        return "Name: " + this.name + "\n" +
                "Set: " + this.setName + "\n" +
                "Mana Cost: " + this.manaCost + "\n" +
                "Type: " + this.type + "\n" +
                "Text: " + this.text + "\n" + this.flavor;

    }

    /**
     * Converts the name (String) to its length (double)
     * @return double Length of the name
     */
    public double convertName() {
        return this.name.length();
    }

    /**
     * Converts the rules text (String) to its length (double)
     * @return double Length of the rules text
     */
    public double convertRulesText() {
        if (this.text!= null){
            return text.length();
        }
        else{
            return 0;
        }
    }
    /**
     * Converts the artist name (String) to its length (double)
     * @return double Length of the artist name
     */
    public double convertArtist() {
        return this.artist.length();
    }

    public double numberColors() {
        return this.colors.length;
    }

    public double numberColorsIdentity() {
        return this.colorIdentity.length;
    }
    /**
     * Subtracts the difference between the # of colors and the # of color identity.
     * # Colors - # Color identity
     * @return double Difference in color and color identity
     */
    public double colorsDifference() {
        return (numberColors() - numberColorsIdentity());
    }

    public double convertFlavorText() {
        if (this.flavor != null) {
            return this.flavor.length();
        }
        return 0;
    }

    public double numberManaSymbols(){
        // give us a String to play with
        String manaSymbol = manaCost;

        if (manaSymbol != null){
            // take all the numbers in the cost and change to a list of those numbers
            manaSymbol = manaSymbol.replaceAll("[^-?0-9]+", " ");
            List<String> generic = Arrays.asList(manaSymbol.trim().split(" "));

            // the first one will be the generic cost
            if(StringUtils.isNumeric(generic.get(0))) {
                double genericNum = Double.parseDouble(generic.get(0));

                // subtract the generic costs from the cmc to get the number of mana symbols
                return (cmc - genericNum);
            }
            else{

                // if there is no generic costs then its all mana symbols
                return cmc;
            }
        }
        return 0;
    }

    public double namesLength(){
        double total = 0;
        for (String namePart: names
                ) {total += namePart.length();

        }
        return total;
    }

    public double typeLineLength(){
        return this.type.length();
    }

    public double numTypes(){
        return types.length;
    }

    public double numSubtypes(){
        return subtypes.length;
    }

    public boolean isLegendary(){
        return Arrays.asList(supertypes).contains("Legendary");
    }

    public boolean isInstant(){
        return Arrays.asList(types).contains("Instant");
    }

    public boolean isSorcery(){
        return Arrays.asList(types).contains("Sorcery");
    }

    public boolean isEnchantment(){
        return Arrays.asList(types).contains("Enchantment");
    }

    public boolean isArtifact(){
        return Arrays.asList(types).contains("Artifact");
    }

    public boolean isCreature(){
        return Arrays.asList(types).contains("Creature");
    }

    public boolean isLand(){
        return Arrays.asList(types).contains("Land");
    }

    public boolean isPlaneswalker(){
        return Arrays.asList(types).contains("Planeswalker");
    }

    /**
     * Returns true if the card has a variable power and/or toughness
     * @return boolean
     */
    public boolean hasVariablePT(){
        return StringUtils.isNumeric(power) && StringUtils.isNumeric(toughness);
    }

    public double getPowerDouble(){
        if ((this.power!= null) && (StringUtils.isNumeric(power))){
            return Double.parseDouble(this.power);
        }
        else{
            return 0;
        }
    }

    public double getToughnessDouble() {
        if ((this.toughness!= null) && (StringUtils.isNumeric(toughness))){
            return Double.parseDouble(this.toughness);
        }
        else{
            return 0;
        }
    }
    /**
     * Returns the String array belonging to the type "name". json stores information under categories. To access
     * an information stored under a specific category you may pass the name to this method.
     * @param name Name listed to the wanted information
     * @param card A JSONObject necessary for retrieving the information
     * @return stringArray The information stored in an array of type String
     */
    public static String[] JSONtoStringArray(String name, JSONObject card){
        JSONArray jArray = (JSONArray) card.get(name);
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            // Not all cards have everything so we need to catch nullPointers
            Iterator<String> iterator = jArray.iterator();
            while (iterator.hasNext()) {
                arrayList.add(iterator.next());
            }
        } catch (NullPointerException e){
            /*String cardName = (String) card.get("name");
            System.out.println(cardName);
            System.out.println("NullPointerException at " + name);*/

        }

        // Turn our list into an array for the constructor
        String[] stringArray = new String[arrayList.size()];
        arrayList.toArray(stringArray);

        return stringArray;

    }

    /**
     * Returns the double array belonging to the type "name". json stores information under categories. To access
     * an information stored under a specific category you may pass the name to this method.
     * @param name Name listed to the wanted information
     * @param card A JSONObject necessary for retrieving the information
     * @return doublesArray The information stored in an array of type double
     */
    public static double[] JSONtoDoubleArray(String name, JSONObject card){
        JSONArray jArray = (JSONArray) card.get(name);
        List<Double> arrayList = new ArrayList<Double>();
        double[] doublesArray = new double[arrayList.size()];
        if (jArray != null) {
            try {
                // Not all cards have everything so we need to catch nullPointers
                Iterator<Long> iterator = jArray.iterator();
                while (iterator.hasNext()) {
                    long num = iterator.next();
                    double entry = (double) num;
                    arrayList.add(entry);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            // Turn our list into an array for the constructor

            for (int i = 0; i < doublesArray.length; i++) {
                doublesArray[i] = arrayList.get(i);
            }


        }
        return doublesArray;
    }

    /**
     * Uses HTMLUnit to open a windowless browser. The browser goes to mtggoldfish and accesses the pricing
     * value belonging to this Card. The function returns the most recent price. To protect mtggoldfish's info
     * this function has been stripped of any code relating to the web scraping of their data.
     *
     * @return price a String containg the price.
     */
    public String getPriceString(){
        // The following line of code prevents HTMLUnit from clogging up the console with unnecessary info
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

        // prepare the setName and name for URL
        String setName = fixName(this.setName);
        String name = fixName(this.name);

        String entry = "";
        HtmlPage page = null;
        boolean success = true;

        // Erased code would be here

       return "0.00";
    }

    /**
     * Fixes the names of the Strings for browsing capability. mtggoldfish URLs replace punctuation and spaces
     * with plus signs so we must do the same.
     * @param name String. Could be a set name or card name.
     * @return name This version of the name has all of the spaces and applicable punctuation replaced with
     * '+'
     */
    static public String fixName(String name){
        name = name.replaceAll(",", "+");
        name = name.replaceAll(" ", "+");
        name = name.replaceAll(":", "+");

        // Apostrophes get erased
        name = name.replaceAll("'", "");

        // There are some cases where two plus signs appear due to a cards name.
        name = name.replaceAll("\\+\\+","+");

        return name;
    }

    // Getters

    /**
     * Returns the name of the card as a String
     * @return name Card name as a String
     */
    public String getName(){return this.name;}
    /**
     * Returns the power of the card as a String. Only applicable to creatures.
     * @return power Power as a String
     */
    public String getPower(){return this.power;}

    /**
     * Returns the toughness of the card as a String. Only applicable to creatures.
     * @return toughness Toughness as a String
     */
    public String getToughness() {return this.toughness;}

    /**
     * Returns the converted mana cost (cmc) of the card as a String.
     * @return cmc CMC as a String
     */
    public double getCmc() {return this.cmc;}

    /**
     * Returns the rarity of the card as a String.
     * @return rarity Rarity as a String
     */
    public String getRarity(){return this.rarity;}

}