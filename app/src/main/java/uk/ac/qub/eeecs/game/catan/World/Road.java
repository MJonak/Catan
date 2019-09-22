package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Road extends ClickableObject{
    //Used to represent the edges of the buildMap graph
    //Stores the two nodes the road links, the build state of said road and the associated player number
        //The smaller node will always be the start node.
    private byte startNode, endNode, buildState, player;

    //TODO The default height & width of the road will only work for 2/3 of the roads, need to change it to 40*100 for vertical roads - also redo the bitmap for vertical roads
    Road(byte A, byte B, GameScreen gameScreen){
        super(0, 0, 87, 50, null, gameScreen);
        if(A<B){
            startNode = A;
            endNode = B;
        }
        else{
            startNode = B;
            endNode = A;
        }
        buildState = 0;
        player = 0;
    }

    public void buildRoad(byte playerNo){
        buildState = 1;
        player = playerNo;
    }

    public byte getBuildState(){return this.buildState;}
    public byte getPlayer(){return this.player;}
    public byte getStartNode(){return this.startNode;}
    public byte getEndNode(){return this.endNode;}

    public boolean checkNodes(byte A, byte B) {
        return (this.startNode == A && this.endNode == B) || (this.startNode == B && this.endNode == A);
    }



}
