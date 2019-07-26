package uk.ac.qub.eeecs.game.catan;
import java.lang.System;
public class BuildMap {
    //The build map will be used to store arrays of:
    // Nodes
    //      - Build state of the node (0 - empty, 1 - settlement, 2 - town)
    //      - Player # (0-3)
    // Roads
    //      - Build state of the road (0 - no, 1 - yes)
    //      - Player # (0-3)

// TODO (FUTURE) Review using 2 bits to store player #, every node & road would have to default to player 0 - would need extra checks when building
    //This will allow to determine where settlements are built, where they can be built,
    // where towns can be built, which player to give resources to after looking up the
    // node from the HexMap node array and determine who has the longest road

    // the build map is a predefined graph which can take different weights & values for
    // each edge and node, the structure of the graph wont change (for now anyway)

    //Tested as of 26/07/19, creates all of the roads and every StartNode < EndNode


    //PROPERTIES
    Node[] nodes = new Node[54];
    Road[] roads = new Road[72];

    //CONSTRUCTOR
    public BuildMap(){
        //Populate array of nodes
        for (short i = 0;i<54;i++){
            nodes[i] = new Node();
        }

        //The roads are generated as efficiently as possible
        //Create the roads
        //Step 1 - sequentially from 0-53
        for(short i = 0; i <53; i++){
            roads[i] = new Road(i, (short)(i+1));
        }

        //Step 2 - roads linking in and out of hexes 12-17 outer edge (check map diagram)
        short a = 0; // Used to correct the difference between the node numbers as it decreases
        for (short i = 31;i<45;i+=3){
            roads[i+22] = new Road((short)(i-29+a), i);
            roads[i+23] = new Road((short)(i+1), (short)(i+18-a));
            roads[i+24] = new Road((short)(i-27+a), (short)(i+2));
            a+=2;
        }
        //Step 3 - exceptions
        roads[68] = new Road((short)0, (short)29);
        roads[69] = new Road((short)30, (short)47);
        roads[70] = new Road((short)48, (short)53);
        roads[71] = new Road((short)27, (short)46);
    }

}
