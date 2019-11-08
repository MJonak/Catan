package uk.ac.qub.eeecs.game.catan;

public class Player {
    private final byte playerNo;
    private byte victoryPoints;
    private byte[] resources = new byte[]{4, 2, 0, 2, 4}; //0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood || Starting off with this array to allow players to build initial settlements & roads
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
    public void addResource(byte resourceNo, byte quantity){ if(resourceNo<5){this.resources[resourceNo] += quantity;}}

    /**
     * Removes the specified quantity of the corresponding resource from the players inventory
     * @param resourceNo Corresponding Resource number: 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood
     * @param quantity Quantity removed
     */
    public void removeResource(byte resourceNo, byte quantity){this.resources[resourceNo] -= quantity;}


    /**
     * Checks if the player has sufficient resources to build a building of the specified type
     * @param buildingType number corresponding to the building to be built, 1 is a settlement, 2 is a city, 3 is a road
     * @return
     */
    public boolean hasEnoughResourcesFor(byte buildingType){
        switch(buildingType){
            case 1:
                if(resources[0]>0 && resources[1]>0 && resources[3]>0 && resources[4]>0)return true;
                break;
            case 2:
                if(resources[2]>2 && resources[3]>1) return true;
                break;
            case 3:
                if(resources[0]>0 && resources[4]>0) return true;
                break;
        }
        return false;
    }

    /**
     * Deduct the resources required to build the specified building
     * @param buildingType number corresponding to the building to be built, 1 is a settlement, 2 is a city, 3 is a road
     */
    public void removeResourcesFor(byte buildingType){
        switch(buildingType){
            case 1:
                resources[0]-=1; resources[1]-=1; resources[3]-=1; resources[4]-=1;
                break;
            case 2:
                resources[2]-=3; resources[3]-=2;
                break;
            case 3:
                resources[0]-=1; resources[4]-=1;
                break;
        }
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

    /**
     * Returns the amount of the specified resource that the player has.
     * @param resourceNo Corresponding Resource number: 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood
     * @return the amount of resource the player has
     */
    public byte getResource(byte resourceNo){return this.resources[resourceNo];}

}
