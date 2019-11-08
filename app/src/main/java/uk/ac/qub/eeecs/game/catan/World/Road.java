package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.CatanGameScreen;

public class Road extends ClickableObject{
    //Used to represent the edges of the buildMap graph
    //Stores the two nodes the road links, the build state of said road and the associated player number
        //The smaller node will always be the start node.
    private byte roadNo, startNode, endNode, buildState, player, roadType;

    Road(byte roadNo, byte A, byte B, GameScreen gameScreen){
        super(0, 0, 87, 50, null, gameScreen);
        if(A<B){
            startNode = A;
            endNode = B;
        }
        else{
            startNode = B;
            endNode = A;
        }
        this.roadNo = roadNo;
        buildState = 0;
        player = 0;
    }

    public void buildRoad(byte playerNo){
        buildState = 1;
        player = playerNo;
        CatanGameScreen.getCurrentPlayer().removeResourcesFor((byte)3);
    }

    /**
     * Returns the current build state of this node
     * @return 0 - empty ; 1 - road built
     */
    public byte getBuildState(){return this.buildState;}

    /**
     * Returns the player number associated with this node
     * @return Corresponding player number if something has been built, 0 otherwise
     */
    public byte getPlayer(){return this.player;}

    /**
     * Returns the start node
     * @return the start node
     */
    public byte getStartNode(){return this.startNode;}

    /**
     * Returns the end node
     * @return the end node
     */
    public byte getEndNode(){return this.endNode;}

    /**
     * Returns the index of the road in the BM.roads array
     * @return the index of the road in the BM.roads array
     */
    public byte getRoadNo(){return this.roadNo;}

    /**
     * Checks if the given node corresponds to the start or end node of this road
     * @param A a node
     * @return true if the road contains this node
     */
    public boolean checkForNode(byte A) {
        return (this.startNode == A || this.endNode == A);
    }

    /**
     * Sets the road type (part of the suffix which determines which bitmap to use when the road is built)
     * @param xy 12 for a downward sloping road; 23 for a vertical road; 34 for an upward sloping road
     */
    public void setRoadType(byte xy){ this.roadType = xy;}


    @Override
    void updateTriggerActions(TouchEvent touchEvent, Vector2 touchLocation) {
        if(this.buildState == 0) {
            if (CatanGameScreen.BM.canRoadBeBuilt(roadNo, CatanGameScreen.getCurrentPlayer().getPlayerNo())) {
                this.buildRoad(CatanGameScreen.getCurrentPlayer().getPlayerNo());
                this.setBitmap(mGameScreen.getGame().getAssetManager().getBitmap("Road" + roadType + "-" + player));
                if (CatanGameScreen.UIMode == 11) CatanGameScreen.UIMode++;
                else CatanGameScreen.UIMode = 0;
            }
        }
    }



}
