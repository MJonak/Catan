package uk.ac.qub.eeecs.game.catan;

public class Player {
    private final int playerNo;
    private int victoryPoints;
    private int[] resources = new int[]{0, 0, 0, 0, 0}; //0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood //TODO Use a string indexed array/key value store instead of this
    //Dev cards
    //trading

    public Player(int playerNumber){
        this.playerNo = playerNumber;
        victoryPoints = 0;
    }

    /**
     * Return player number
     * @return This player's player number
     */
    public int getPlayerNo(){return this.playerNo;}

    /**
     * Sets the Player's resource array values to allow them to build 2 settlements & 2 roads at the start of the game.
     */
    //TODO This should probably be in the default constructor
    public void setStartingResources(){
        resources[0] = 4;
        resources[1] = 2;
        resources[2] = 0;
        resources[3] = 2;
        resources[4] = 4;
    }

    /**
     * Adds the specified quantity of the corresponding resource to the players inventory.
     * @param resourceNo Resource number stored in relevant hex
     * @param quantity quantity given, this number is equal to the buildState of the relevant node
     */
    public void addResource(int resourceNo, int quantity){ if(resourceNo<5){this.resources[resourceNo] += quantity;}}

    /**
     * Removes the specified quantity of the corresponding resource from the players inventory
     * @param resourceNo Corresponding Resource number: 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood
     * @param quantity Quantity removed
     */
    public void removeResource(int resourceNo, int quantity){this.resources[resourceNo] -= quantity;}

    /**
     * Counts the player's total number of resource cards. If a 7 is rolled and this player's resource card total exceeds 7 they must discard half of their cards (rounded down).
     * @return This player's total number of resources
     */
    public int getNoOfResources(){
        int sum = 0;
        for (int resource : resources) {
            sum += resource;
        }
        return sum;
    }

    /**
     * Returns the amount of the specified resource that the player has.
     * @param resourceNo Corresponding Resource number: 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood
     * @return the amount of resource the player has
     */
    public int getResource(int resourceNo){return this.resources[resourceNo];}

    /**int
     * Checks if the player has sufficient resources to build a building of the specified type
     * @param buildingType number corresponding to the building to be built, 1 is a settlement, 2 is a town, 3 is a road
     * @return true if the player has enough resources to build the specified building, false otherwise
     */
    public boolean hasEnoughResourcesFor(int buildingType){
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
    public void removeResourcesFor(int buildingType){
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
    public void addVictoryPoints(int quantity){this.victoryPoints += quantity;}

    /**
     * Returns the player's current victory point total
     * @return This player's victory point total
     */
    public int getVictoryPoints(){return this.victoryPoints;}



}
