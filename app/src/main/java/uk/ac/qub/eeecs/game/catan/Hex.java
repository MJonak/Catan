package uk.ac.qub.eeecs.game.catan;

public class Hex {
    //The Hex map is made up of an array of 19 hexes

    //Each hex is used to store:
    // - the resources gained
    // - the associated dice #
    // - whether there is a knight
    // - a 2D array storing the 6 node #s


    //The build map stores information about the nodes and edges between them.
    // Ports will be incorporated by 'unlocking' the ability to trade if the player
    // builds a settlement on one of the port nodes (certain nodes between 0-29)

    //PROPERTIES
    private byte resource;
    private byte diceNo;
    private boolean robber;
    private byte[] Nodes = new byte[6];

    //CONSTRUCTOR - Uses node0-node5 for the time being as i just want to hardcode the nodes to each hex atm, ill clean this up later
    //TODO clean this up later
    public Hex(byte res, byte dice, byte node0, byte node1, byte node2, byte node3, byte node4, byte node5){
        this.resource = res;
        this.diceNo = dice;
        this.Nodes[0] = node0;this.Nodes[1] = node1;this.Nodes[2] = node2;this.Nodes[3] = node3;this.Nodes[4] = node4;this.Nodes[5] = node5;
        robber = false;
    }
    public Hex(byte res, byte dice, byte[] nodes){
        this.resource = res;
        this.diceNo = dice;
        for (int i = 0;i<6;i++){
            this.Nodes[i] = nodes[i];
        }
    }

    //METHODS
    public boolean hasRobber(){return robber;};

    public boolean checkDiceNo(byte dice) {
        if(this.diceNo == dice)
            return true;
        else
            return false;
    }

    public short getResource(){return this.resource;}
    public short getNode(short x) {return this.Nodes[x];}

}
