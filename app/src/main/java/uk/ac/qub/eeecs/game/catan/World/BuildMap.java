package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.CatanGameScreen;

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


    //PROPERTIES

    public Node[] nodes = new Node[54];
    public Road[] roads = new Road[72];

    //CONSTRUCTOR
    public BuildMap(HexMap HM, GameScreen gameScreen){
        //Populate array of nodes
        for (int i = 0;i<54;i++){
            nodes[i] = new Node(i, gameScreen);
        }
        String Processed = ",";

        //Vertices of the hexes 0-11 & 18 make up all of the nodes on the map, so hexes 12-17 do not need to be processed.
        for (int h = 0; h < 19; h++) {
            for (int n = 0; n <6; n++){
                if (!Processed.contains("," + HM.Hexes[h].getNode(n) + ",")){
                    switch(n) {
                        case 0:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()-HM.XCos30,HM.Hexes[h].getY()+(HM.X*0.5f));
                            break;
                        case 1:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX(),HM.Hexes[h].getY()+HM.X);
                            break;
                        case 2:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()+HM.XCos30,HM.Hexes[h].getY()+(HM.X*0.5f));
                            break;
                        case 3:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()+HM.XCos30,HM.Hexes[h].getY()-(HM.X*0.5f));
                            break;
                        case 4:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX(),HM.Hexes[h].getY()-HM.X);
                            break;
                        case 5:
                            nodes[HM.Hexes[h].getNode(n)].setPosition(HM.Hexes[h].getX()-HM.XCos30,HM.Hexes[h].getY()-(HM.X*0.5f));
                            break;
                    }
                    Processed += HM.Hexes[h].getNode(n) + ",";
                }
                if(h==11) h=17;
            }
        }

        //Set Node bitmap
        for (Node n: nodes) {
            n.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("Node0"));
        }

        //The roads are generated as efficiently as possible
        //Create the roads
        //Step 1 - sequentially from 0-53
        for(int i = 0; i <53; i++){
            roads[i] = new Road(i, i, (i+1), gameScreen);
        }

        //Step 2 - roads linking in and out of hexes 12-17 outer edge (check map diagram)
        int a = 0; // Used to correct the difference between the node numbers as it decreases
        for (int i = 31;i<45;i+=3){
            roads[i+22] = new Road((i+22),(i-29+a), i, gameScreen);
            roads[i+23] = new Road((i+23),(i+1), (i+18-a), gameScreen);
            roads[i+24] = new Road((i+24),(i-27+a), (i+2), gameScreen);
            a+=2;
        }
        //Step 3 - exceptions
        roads[68] = new Road(68, 0, 29, gameScreen);
        roads[69] = new Road(69, 30, 47, gameScreen);
        roads[70] = new Road(70, 48, 53, gameScreen);
        roads[71] = new Road(71, 27, 46, gameScreen);

        for (Road r: roads){
            //Calculate the average coordinates for every road
            r.setPosition(((nodes[r.getStartNode()].position.x + nodes[r.getEndNode()].position.x)/2) , ((nodes[r.getStartNode()].position.y + nodes[r.getEndNode()].position.y)/2));

            if (nodes[r.getStartNode()].position.x == nodes[r.getEndNode()].position.x){
                r.setRoadType(23); //Use Road23
                r.setHeight(100f);
                r.setWidth(20f);
                continue;
            }

            //Very long condition which just checks the difference between the start & end nodes x&y
            if(((nodes[r.getStartNode()].position.x - nodes[r.getEndNode()].position.x) < 0 && (nodes[r.getStartNode()].position.y - nodes[r.getEndNode()].position.y) < 0) || ((nodes[r.getStartNode()].position.x - nodes[r.getEndNode()].position.x) > 0 && (nodes[r.getStartNode()].position.y - nodes[r.getEndNode()].position.y) > 0)) {
                r.setRoadType(34); //Use Road34
                continue;
            }

            //if we've reached this part of the loop there's only one other option
            r.setRoadType(12); //Use Road12


        }

    }

    /**
     * Checks the roads coming out of a specified node and determines whether a road has been built linking to this node. Used to make sure that during setup the player places their road at the last settlement they've placed.
     * @param nodeNo the node to be checked
     * @return true if there are no roads around this node, false otherwise
     */
    private boolean checkForRoadsAroundNode(int nodeNo){
        //Find the roads coming out of node nodeNo
        //Check if theres anything there
        int[] roadsToBeChecked = new int[3];
        int i = -1;
        for (Road r:roads) {
            if(r.getStartNode() == nodeNo || r.getEndNode() == nodeNo){
                i++;
                roadsToBeChecked[i] = r.getRoadNo();
            }
        }
        for (;i >=0; i--) {
            if(roads[roadsToBeChecked[i]].getBuildState()>0){
                return false;
            }
        }
        return true;
    }

    /**
     * Method used for checking that the selected road can be legally built
     * @param roadNo number corresponding to the road that was pressed
     * @param playerNo player attempting to place the road
     * @return true if the road can be built, false otherwise
     */
    boolean canRoadBeBuilt(int roadNo, int playerNo) {
        int A, B;
        A = roads[roadNo].getStartNode();
        B = roads[roadNo].getEndNode();
        if (CatanGameScreen.turnNo < 3) {   //If the game is still in setup the rules allow for roads to be built without linking onto other roads but settlements instead
            //Check nodes
            if (nodes[A].getBuildState() > 0 && nodes[A].getPlayer() == playerNo) {
                //Start node has a building owned by player, so can be built
                //Check that a road hasn't been built on this node
                return checkForRoadsAroundNode(A);
            }
            if (nodes[B].getBuildState() > 0 && nodes[B].getPlayer() == playerNo) {
                //End node has a building owned by player, so can be built
                //Check that a road hasn't been built on this node
                return checkForRoadsAroundNode(B);
            }
        } else {                  //During a normal turn roads must simply connect to another road
            int[] roadsToBeChecked = new int[4];
            int i = 0;
            for (Road r : roads) {
                if ((r.checkForNode(A)) ^ (r.checkForNode(B))) { //Using XOR to make sure the road we are testing for isn't included
                    roadsToBeChecked[i] = r.getRoadNo();
                    i++;
                }
            }
            //iterate through the array of roadsToBeChecked to see if one of them corresponds to a player owned road
            for (int j = 0; j < i; j++) {
                //if one of these is player owned, the road can be built
                if (roads[roadsToBeChecked[j]].getBuildState() > 0 && roads[roadsToBeChecked[j]].getPlayer() == playerNo) {
                    return true;
                }
            }
        }
        return false;
    }

    //For settlements check that the nodes surrounding haven't been built.
        //Find roads linked to this node
        //Find list of the 2/3 nodes linked
        //Check build state
    boolean canSettlementBeBuilt(int nodeNo){
        //Find nodes surrounding nodeNo
        int[] nodesToBeChecked = new int[3];
        int i = -1;
        for (Road r:roads) {
            if(r.getStartNode() == nodeNo){
                i++;
                nodesToBeChecked[i] = r.getEndNode();
            }else if(r.getEndNode() == nodeNo){
                i++;
                nodesToBeChecked[i] = r.getStartNode();
            }
        }
        //Check if any of those nodes are occupied
        for (; i >= 0; i--) {
            if(nodes[nodesToBeChecked[i]].getBuildState() > 0){
                return false;
            }
        }
        return true;
    }
}
