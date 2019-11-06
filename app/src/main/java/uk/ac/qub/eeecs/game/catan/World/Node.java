package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.CatanGameScreen;

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
        CatanGameScreen.getCurrentPlayer().removeResourcesFor((byte)1);
        CatanGameScreen.getCurrentPlayer().addVictoryPoints((byte)1);
    }

    /**
     * Marks that the settlement on this node has now been upgraded to a town.
     * Check that the playerNo matches and that there is already a settlement built.
     */
    public void buildTown() {
        buildState = 2;
        CatanGameScreen.getCurrentPlayer().removeResourcesFor((byte)2);
        CatanGameScreen.getCurrentPlayer().addVictoryPoints((byte)1);
    }



    @Override
    void updateTriggerActions(TouchEvent touchEvent, Vector2 touchLocation){
        if (this.buildState == 0){
            this.buildSettlement(CatanGameScreen.getCurrentPlayer().getPlayerNo());
            this.setBitmap(mGameScreen.getGame().getAssetManager().getBitmap("Node" + player));
            if(CatanGameScreen.UIMode == 10)CatanGameScreen.UIMode++;else CatanGameScreen.UIMode = 0;
        }
        //TODO add building towns

    }



}
