package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Node extends ClickableObject{
    //Used to represent the vertices of the BuildMap graph
    //Stores the build state of the node and the associated player number

    private byte buildState, player;

    Node(GameScreen gameScreen){
        super(0, 0, 40, 40, null,  gameScreen);
        buildState = 0;
        player = 0;
    }

    /**
     * Returns the current build state of this node
     * @return 0 - empty ; 1 - settlement ; 2 - town
     */
    public byte getBuildState(){return this.buildState;}

    /**
     * Returns the player number associated with this node
     * @return Corresponding player number if something has been built, 0 otherwise
     */
    public byte getPlayer(){return this.player;}

    /**
     * Marks that this node now contains a settlement belonging to the player correspdoing to playerNo
     * @param playerNo corresponding player's player number
     */
    public void buildSettlement(byte playerNo){
        buildState = 1;
        player = playerNo;
    }

    /**
     * Marks that the settlement on this node has now been upgraded to a town.
     * Check that the playerNo matches and that there is already a settlement built.
     */
    public void buildTown(){
        buildState = 2;
    }




}
