package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Node extends ClickableObject{
    //Used to represent the vertices of the BuildMap graph
    //Stores the build state of the node and the associated player number

    private short buildState, player;

    Node(GameScreen gameScreen){
        super(0, 0, 40, 40, null,  gameScreen);
        buildState = 0;
        player = 0;
    }

    public void buildSettlement(short playerNo){
        buildState = 1;
        player = playerNo;
    }
    public void buildTown(){
        buildState = 2;
    }




}
