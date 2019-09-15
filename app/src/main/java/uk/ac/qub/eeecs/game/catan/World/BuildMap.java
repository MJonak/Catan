package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class BuildMap {
    //The build map will be used to store arrays of:
    // Nodes
    //      - Build state of the node (0 - empty, 1 - settlement, 2 - town)
    //      - Player # (0-3)
    // Roads
    //      - Build state of the road (0 - no, 1 - yes)
    //      - Player # (0-3)

    // The build map is a predefined graph which can take different weights & values for
    // each edge and node, the structure of the graph wont change (for now anyway)

    //This will allow to determine where settlements are built, where they can be built,
    // where towns can be built, which player to give resources to after looking up the
    // node from the HexMap node array and determine who has the longest road

    //Tested as of 26/07/19, creates all of the roads and every StartNode < EndNode

// TODO (FUTURE) Review using 2 bits to store player #, every node & road would have to default to player 0 - would need extra checks when building

    //PROPERTIES
    public Node[] nodes = new Node[54];
    public Road[] roads = new Road[72];

    //CONSTRUCTOR
    public BuildMap(HexMap HM, GameScreen gameScreen){
        //Populate array of nodes
        for (short i = 0;i<54;i++){
            nodes[i] = new Node(gameScreen);
        }
        String Processed = ",";
        //Ending the loop at 17 as 18&17 dont need to be checked - all nodes will have been processed
        for (int h = 0; h < 17; h ++) {
            for (byte n = 0; n <6; n++){
                if (!Processed.contains("," + HM.Hexes[h].getNode(n) + ",")){
                    switch(n) {
                        case 0:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()-HM.XCos30,HM.Hexes[h].getY()-(HM.X*0.5f));
                            break;
                        case 1:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX(),HM.Hexes[h].getY()-HM.X);
                            break;
                        case 2:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()+HM.XCos30,HM.Hexes[h].getY()-(HM.X*0.5f));
                            break;
                        case 3:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()+HM.XCos30,HM.Hexes[h].getY()+(HM.X*0.5f));
                            break;
                        case 4:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX(),HM.Hexes[h].getY()+HM.X);
                            break;
                        case 5:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()-HM.XCos30,HM.Hexes[h].getY()+(HM.X*0.5f));
                            break;
                    }
                    Processed += + HM.Hexes[h].getNode(n) + ",";
                }
            }
        }

        //Set Node bitmap
        for (Node n: nodes) {
            n.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("TempNode"));
        }

        //The roads are generated as efficiently as possible
        //Create the roads
        //Step 1 - sequentially from 0-53
        for(short i = 0; i <53; i++){
            roads[i] = new Road(i, (short)(i+1), gameScreen);
        }

        //Step 2 - roads linking in and out of hexes 12-17 outer edge (check map diagram)
        short a = 0; // Used to correct the difference between the node numbers as it decreases
        for (short i = 31;i<45;i+=3){
            roads[i+22] = new Road((short)(i-29+a), i, gameScreen);
            roads[i+23] = new Road((short)(i+1), (short)(i+18-a), gameScreen);
            roads[i+24] = new Road((short)(i-27+a), (short)(i+2), gameScreen);
            a+=2;
        }
        //Step 3 - exceptions
        roads[68] = new Road((short)0, (short)29, gameScreen);
        roads[69] = new Road((short)30, (short)47, gameScreen);
        roads[70] = new Road((short)48, (short)53, gameScreen);
        roads[71] = new Road((short)27, (short)46, gameScreen);
    }

}
