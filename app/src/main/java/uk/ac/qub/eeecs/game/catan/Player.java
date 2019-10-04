package uk.ac.qub.eeecs.game.catan;

public class Player {
    //TODO figure out how im going to store dev cards and trading - possibly, might just ignore those for the time being
    private final byte playerNo;
    private byte victoryPoints;
    private byte[] resources = new byte[4];
    //Dev cards
    //trading
    public Player(byte playerNumber){
        this.playerNo = playerNumber;
        victoryPoints = 0;
    }

    /**
     * Adds the specified quantity of the corresponding resource to the players inventory.
     * @param resourceNo Resource number stored in relevant hex
     * @param quantity Quantity given, based off of number of settlements/cities on relevant hexes
     */
    public void addResource(byte resourceNo, byte quantity){this.resources[resourceNo] += quantity;}

    /**
     * Removes the specified quantity of the corresponding resource from the players inventory
     * @param resourceNo Corresponding Resource number: 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood
     * @param quantity Quantity removed
     */
    public void removeResource(byte resourceNo, byte quantity){this.resources[resourceNo] -= quantity;}

    /**
     * Checks if the player's quantity of the specified resource exceeds or is equal to the quantity specified
     * @param resourceNo Resource number to check
     * @param quantity Required quantity
     * @return
     */
    public boolean hasEnoughResource(byte resourceNo, byte quantity){
        return this.resources[resourceNo] >= quantity;
    }

    /**
     * Add victory points to the players running total. Call this method when the player should be awarded victory points
     * @param quantity Number of victory points to be awarded
     */
    public void addVictoryPoints(byte quantity){this.victoryPoints += quantity;}

    /**
     * Return player number
     * @return This player's player number
     */
    public byte getPlayerNo(){return this.playerNo;}

    /**
     * Returns the player's current victory point total
     * @return This player's victory point total
     */
    public byte getVictoryPoints(){return this.victoryPoints;}

    /**
     * Counts the player's total number of resource cards. If a 7 is rolled and this player's resource card total exceeds 7 they must discard half of their cards (rounded down).
     * @return This player's total number of resources
     */
    public byte getNoOfResources(){
        byte sum = 0;
        for (int i = 0; i<resources.length; i++){
            sum += resources[i];
        }
        return sum;
    }


}
