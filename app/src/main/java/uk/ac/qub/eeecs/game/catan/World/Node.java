package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.CatanGameScreen;

public class Node extends ClickableObject{
    //Used to represent the vertices of the BuildMap graph
    //Stores the build state of the node and the associated player number

    private int nodeNo, buildState, player;

    Node(int id, GameScreen gameScreen){
        super(0, 0, 40, 40, null,  gameScreen);
        this.nodeNo = id;
        buildState = 0;
        player = 0;
    }

    /**
     * Returns the current build state of this node
     * @return 0 - empty ; 1 - settlement ; 2 - town
     */
    public int getBuildState(){return this.buildState;}

    /**
     * Returns the player number associated with this node
     * @return Corresponding player number if something has been built, 0 otherwise
     */
    public int getPlayer(){return this.player;}

    /**
     * Marks that this node now contains a settlement belonging to the player correspdoing to playerNo
     * @param playerNo corresponding player's player number
     */
    private void buildSettlement(int playerNo){
        buildState = 1;
        player = playerNo;
        CatanGameScreen.getCurrentPlayer().removeResourcesFor(1);
        CatanGameScreen.getCurrentPlayer().addVictoryPoints(1);
    }

    /**
     * Marks that the settlement on this node has now been upgraded to a town.
     * Check that the playerNo matches and that there is already a settlement built.
     */
    public void buildTown() {
        buildState = 2;
        CatanGameScreen.getCurrentPlayer().removeResourcesFor(2);
        CatanGameScreen.getCurrentPlayer().addVictoryPoints(1);
    }



    @Override
    void updateTriggerActions(TouchEvent touchEvent, Vector2 touchLocation){
        if (this.buildState == 0){
            if(CatanGameScreen.BM.canSettlementBeBuilt(this.nodeNo)) {
                this.buildSettlement(CatanGameScreen.getCurrentPlayer().getPlayerNo());
                this.setBitmap(mGameScreen.getGame().getAssetManager().getBitmap("Node" + player));
                if (CatanGameScreen.UIMode == 10) CatanGameScreen.UIMode++;
                else CatanGameScreen.UIMode = 0;
            }
        } else if(this.buildState == 1 && CatanGameScreen.turnNo>2){
            if(this.player == CatanGameScreen.getCurrentPlayer().getPlayerNo()) {
                this.buildTown();
                this.setBitmap(mGameScreen.getGame().getAssetManager().getBitmap("Town" + player));
                CatanGameScreen.UIMode = 0;
            }
        }
    }



}
