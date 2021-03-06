package uk.ac.qub.eeecs.game.catan.World;

import uk.ac.qub.eeecs.gage.world.GameScreen;

public class Hex extends ClickableObject{
    //The Hex map is made up of an array of 19 hexes

    //Each hex is used to store:
    // - the resources gained
            // 0 is brick, 1 is wool, 2 is ore, 3 is  grain, 4 is wood, 5 is desert
    // - the associated dice #
            //2-12, 7 is special
    // - whether there is a knight
    // - a 2D array storing the 6 node #s
    // - hex center X
    // - hex center Y



    //The build map stores information about the nodes and edges between them.
    // Ports will be incorporated by 'unlocking' the ability to trade if the player
    // builds a settlement on one of the port nodes (certain nodes between 0-29)

    //PROPERTIES
    private int resource;
    private int diceNo;
    private boolean robber;
    private int[] Nodes = new int[6];

    //CONSTRUCTOR - Uses node0-node5 for the time being as i just want to hardcode the nodes to each hex atm, ill clean this up later
    //TODO clean this up later
    public Hex(int res, int dice, int node0, int node1, int node2, int node3, int node4, int node5, GameScreen gameScreen){
        super(0, 0, 174f, 200f, null, gameScreen);
        this.resource = res;
        this.diceNo = dice;
        this.Nodes[0] = node0;this.Nodes[1] = node1;this.Nodes[2] = node2;this.Nodes[3] = node3;this.Nodes[4] = node4;this.Nodes[5] = node5;
        robber = false;
    }

        //METHODS

    /**
     * Checks if this hex is currently occupied by the robber.
     * @return True if the robber is on this hex, False otherwise
     */
    public boolean hasRobber(){return robber;}

    /**
     * Returns the dice number corresponding to this hex
     * @return The dice number
     */
    public int getDiceNo(){return this.diceNo;}

    /**
     * Returns the resource corresponding to this hex
     * @return The resource number
     */
    public int getResource(){return this.resource;}

    /**
     * Returns the node index corresponding to the selected node of this hex.
     * @param x Node index within this hex: 0 - Top left ; 1 - Top center ; 2 - Top right ; 3 - Bottom right ; 4 - Bottom center ; 5 - Bottom Left
     * @return Node index within BuildMap Nodes[] array
     */
    public int getNode(int x) {return this.Nodes[x];}

    /**
     * Returns the X coordinate of this hex
     * @return X coordinate in relation to game layer
     */
    public float getX(){return this.position.x;}

    /**
     * Returns the Y coordinate of this hex
     * @return Y coordinate in relation to game layer
     */
    public float getY(){return this.position.y;}
}
