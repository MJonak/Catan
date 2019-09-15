package uk.ac.qub.eeecs.game.catan.World;

import java.lang.System;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class HexMap {

    //The HexMap class is used to store the 19 hexes that make up the actual map alongside all related methods

    //The predefined list of resources will be based off of the beginner set-up in the settlers of catan rule book
        // 5, 2, 4, 2, 4, 3, 5, 4, 3, 1, 0, 1; 3, 1, 5, 2, 2, 5, 4
    // and the list of dice rolls
        //11, 12, 9, 10, 8, 3, 6, 2, 5, 8, {0} , 4, 6, 5, 4, 9, 10, 3, 11
    final short NoOfHexes = 19;
    final float XCos30 = 87f; //(float)(100*Math.cos((30*Math.PI)/180));
    final float X = 100f;
    public Hex[] Hexes = new Hex[NoOfHexes];

    public HexMap(GameScreen gameScreen){
        byte[] resArr = new byte[]{5, 2, 4, 2, 4, 3, 5, 4, 3, 1, 0, 1, 3, 1, 5, 2, 2, 5, 4};
        byte[] diceArr = new byte[]{11, 12, 9, 10, 8, 3, 6, 2, 5, 8, 0, 4, 6, 5, 4, 9, 10, 3, 11};
        //Tried generating the numbers, however they need to be stored in the correct node field in the Hex object making it far far harder.

        Hexes[0] = new Hex(resArr[0], diceArr[0], (byte)0, (byte)1, (byte)2, (byte)31, (byte)30, (byte)29, gameScreen);
        Hexes[1] = new Hex(resArr[1], diceArr[1], (byte)2, (byte)3, (byte)4, (byte)33, (byte)32, (byte)31, gameScreen);
        Hexes[2] = new Hex(resArr[2], diceArr[2], (byte)4, (byte)5, (byte)6, (byte)7, (byte)34, (byte)33, gameScreen);
        Hexes[3] = new Hex(resArr[3], diceArr[3], (byte)34, (byte)7, (byte)8, (byte)9, (byte)36, (byte)35, gameScreen);
        Hexes[4] = new Hex(resArr[4], diceArr[4], (byte)36, (byte)9, (byte)10, (byte)11, (byte)12, (byte)37, gameScreen);
        Hexes[5] = new Hex(resArr[5], diceArr[5], (byte)38, (byte)37, (byte)12, (byte)13, (byte)14, (byte)39, gameScreen);
        Hexes[6] = new Hex(resArr[6], diceArr[6], (byte)40, (byte)39, (byte)14, (byte)15, (byte)16, (byte)17, gameScreen);
        Hexes[7] = new Hex(resArr[7], diceArr[7], (byte)42, (byte)41, (byte)40, (byte)17, (byte)18, (byte)19, gameScreen);
        Hexes[8] = new Hex(resArr[8], diceArr[8], (byte)22, (byte)43, (byte)42, (byte)19, (byte)20, (byte)21, gameScreen);
        Hexes[9] = new Hex(resArr[9], diceArr[9], (byte)24, (byte)45, (byte)44, (byte)43, (byte)42, (byte)19, gameScreen);
        Hexes[10] = new Hex(resArr[10], diceArr[10], (byte)26, (byte)27, (byte)46, (byte)45, (byte)24, (byte)25, gameScreen);
        Hexes[11] = new Hex(resArr[11], diceArr[11], (byte)28, (byte)29, (byte)30, (byte)47, (byte)46, (byte)27, gameScreen);
        Hexes[12] = new Hex(resArr[12], diceArr[12], (byte)30, (byte)31, (byte)32, (byte)49, (byte)48, (byte)47, gameScreen);
        Hexes[13] = new Hex(resArr[13], diceArr[13], (byte)32, (byte)33, (byte)34, (byte)35, (byte)50, (byte)49, gameScreen);
        Hexes[14] = new Hex(resArr[14], diceArr[14], (byte)50, (byte)35, (byte)36, (byte)37, (byte)38, (byte)51, gameScreen);
        Hexes[15] = new Hex(resArr[15], diceArr[15], (byte)52, (byte)51, (byte)38, (byte)39, (byte)40, (byte)41, gameScreen);
        Hexes[16] = new Hex(resArr[16], diceArr[16], (byte)49, (byte)53, (byte)52, (byte)41, (byte)42, (byte)43, gameScreen);
        Hexes[17] = new Hex(resArr[17], diceArr[17], (byte)46, (byte)47, (byte)48, (byte)53, (byte)44, (byte)45, gameScreen);
        Hexes[18] = new Hex(resArr[18], diceArr[18], (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, gameScreen);


        //SETTING CENTERPOINTS

        //Define Center
        Hexes[18].setPosition(609f, 600f);
        //First Row
        Hexes[0].setPosition(Hexes[18].getX()-2*XCos30,Hexes[18].getY()-300f);
        Hexes[1].setPosition(Hexes[0].getX()+2*XCos30, Hexes[0].getY());
        Hexes[2].setPosition(Hexes[0].getX()+4*XCos30, Hexes[0].getY());
        //Second Row
        Hexes[11].setPosition(Hexes[0].getX()-XCos30, Hexes[0].getY()+150f);
        Hexes[12].setPosition(Hexes[11].getX()+2*XCos30, Hexes[11].getY());
        Hexes[13].setPosition(Hexes[11].getX()+4*XCos30, Hexes[11].getY());
        Hexes[3].setPosition(Hexes[11].getX()+6*XCos30, Hexes[11].getY());
        //Third Row
        Hexes[10].setPosition(Hexes[18].getX()-4*XCos30, Hexes[18].getY());
        Hexes[17].setPosition(Hexes[18].getX()-2*XCos30, Hexes[18].getY());
        Hexes[14].setPosition(Hexes[18].getX()+2*XCos30, Hexes[18].getY());
        Hexes[4].setPosition(Hexes[18].getX()+4*XCos30, Hexes[18].getY());
        //Fourth Row
        Hexes[9].setPosition(Hexes[11].getX(), Hexes[18].getY()+150f);
        Hexes[16].setPosition(Hexes[9].getX()+2*XCos30, Hexes[9].getY());
        Hexes[15].setPosition(Hexes[9].getX()+4*XCos30, Hexes[9].getY());
        Hexes[5].setPosition(Hexes[9].getX()+6*XCos30, Hexes[9].getY());
        //Fifth Row
        Hexes[8].setPosition(Hexes[0].getX(), Hexes[9].getY()+150f);
        Hexes[7].setPosition(Hexes[8].getX()+2*XCos30, Hexes[8].getY());
        Hexes[6].setPosition(Hexes[8].getX()+4*XCos30, Hexes[8].getY());
        
        //SETTING BITMAPS - Just setting all bitmaps to the placeholder
        //TODO change this to a switch statement and set the appropriate bitmap for each hex depending on the resource value
        for (Hex h: Hexes) {
            h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("TempHex"));
        }

        //TESTING  V V V V V V V V V V V

        for (int i = 0; i<NoOfHexes; i++){
            for (byte j = 0; j<6; j++) {
                System.out.print(Hexes[i].getNode(j) + " ");
            }
            System.out.println("|");
        }

    }

}
