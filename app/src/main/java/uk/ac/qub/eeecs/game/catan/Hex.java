package uk.ac.qub.eeecs.game.catan;

public class Hex {
    //The Hex map is made up of an array of 19 hexes

    //Each hex is used to store:
    // - the resources gained
    // - the associated dice #
    // - whether there is a knight
    // - a 2D array storing the 6 node #s


    //The build map will store information about the nodes and edges between them.
    // Ports will be incorporated by 'unlocking' the ability to trade if the player
    // builds a settlement on one of the port nodes (certain nodes between 0-29)

    //PROPERTIES
    private byte resource;
    private byte diceNo;
    private boolean robber;
    private byte[] Nodes = new byte[6];

    //CONSTRUCTOR
    public Hex(byte res, byte dice, byte[] nodes){
        this.resource = res;
        this.diceNo = dice;
        for (int i = 0; i < 6; i++)
            this.Nodes[i] = nodes[i];
        robber = false;
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
