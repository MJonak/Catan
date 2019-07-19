package uk.ac.qub.eeecs.game.catan;

public class Road {
    //Used to represent the edges of the buildMap graph
    //Stores the two nodes the road links, the build state of said road and the associated player number
        //The smaller node will always be the start node.
    short startNode, endNode, buildState, player;

    public Road(short A, short B){
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

    public void buildRoad(short playerNo){
        buildState = 1;
        player = playerNo;
    }


}
