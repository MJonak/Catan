package uk.ac.qub.eeecs.game.catan;

public class Node {
    //Used to represent the vertices of the BuildMap graph
    //Stores the build state of the node and the associated player number

    short buildState, player;

    public Node(){
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
