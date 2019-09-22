package uk.ac.qub.eeecs.game.catan;

public class Player {
    //TODO figure out how im going to store dev cards and trading - possibly, might just ignore those for the time being
    private final byte playerNo;
    private byte victoryPoints;
    private byte[] resources = new byte[4];
    //Dev cards
    //trading
    public Player(byte playerNumber){
        this.playerNo = playerNumber;
        victoryPoints = 0;
    }

    public void addResource(byte resourceNo, byte quantity){this.resources[resourceNo] += quantity;}
    public void addVictoryPoints(byte quantity){this.victoryPoints += quantity;}
    public byte getPlayerNo(){return this.playerNo;}
    public byte getVictoryPoints(){return this.victoryPoints;}
    public byte getNoOfResources(){
        byte sum = 0;
        for (int i = 0; i<resources.length; i++){
            sum += resources[i];
        }
        return sum;
    }


}
