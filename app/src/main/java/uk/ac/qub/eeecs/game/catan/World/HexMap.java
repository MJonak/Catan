package uk.ac.qub.eeecs.game.catan.World;

import java.lang.System;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class HexMap {

    //The HexMap class is used to store the 19 hexes that make up the actual map alongside all related methods

    // 0 is brick, 1 is sheep, 2 is ore, 3 is wheat, 4 is wood, 5 is desert

    //The predefined list of resources will be based off of the beginner set-up in the settlers of catan rule book
        // 4, 1, 3, 1, 3, 2, 4, 3, 2, 0, 5, 0; 2, 0, 4, 1, 1, 4, 3
    // and the list of dice rolls
        //11, 12, 9, 10, 8, 3, 6, 2, 5, 8, {0} , 4, 6, 5, 4, 9, 10, 3, 11
    final private int NoOfHexes = 19;
    final float XCos30 = 87f; //(float)(100*Math.cos((30*Math.PI)/180));
    final float X = 100f;
    public Hex[] Hexes = new Hex[NoOfHexes];

    public HexMap(GameScreen gameScreen){
        int[] resArr = new int[]{4, 1, 3, 1, 3, 2, 4, 3, 2, 0, 5, 0, 2, 0, 4, 1, 1, 4, 3};
        int[] diceArr = new int[]{11, 12, 9, 10, 8, 3, 6, 2, 5, 8, 0, 4, 6, 5, 4, 9, 10, 3, 11};
        //Tried generating the numbers, however they need to be stored in the correct node field in the Hex object making it far far harder.

        Hexes[0] = new Hex(resArr[0], diceArr[0], 0, 1, 2, 31, 30, 29, gameScreen);
        Hexes[1] = new Hex(resArr[1], diceArr[1], 2, 3, 4, 33, 32, 31, gameScreen);
        Hexes[2] = new Hex(resArr[2], diceArr[2], 4, 5, 6, 7, 34, 33, gameScreen);
        Hexes[3] = new Hex(resArr[3], diceArr[3], 34, 7, 8, 9, 36, 35, gameScreen);
        Hexes[4] = new Hex(resArr[4], diceArr[4], 36, 9, 10, 11, 12, 37, gameScreen);
        Hexes[5] = new Hex(resArr[5], diceArr[5], 38, 37, 12, 13, 14, 39, gameScreen);
        Hexes[6] = new Hex(resArr[6], diceArr[6], 40, 39, 14, 15, 16, 17, gameScreen);
        Hexes[7] = new Hex(resArr[7], diceArr[7], 42, 41, 40, 17, 18, 19, gameScreen);
        Hexes[8] = new Hex(resArr[8], diceArr[8], 22, 43, 42, 19, 20, 21, gameScreen);
        Hexes[9] = new Hex(resArr[9], diceArr[9], 24, 45, 44, 43, 22, 23, gameScreen);
        Hexes[10] = new Hex(resArr[10], diceArr[10], 26, 27, 46, 45, 24, 25, gameScreen);
        Hexes[11] = new Hex(resArr[11], diceArr[11], 28, 29, 30, 47, 46, 27, gameScreen);
        Hexes[12] = new Hex(resArr[12], diceArr[12], 30, 31, 32, 49, 48, 47, gameScreen);
        Hexes[13] = new Hex(resArr[13], diceArr[13], 32, 33, 34, 35, 50, 49, gameScreen);
        Hexes[14] = new Hex(resArr[14], diceArr[14], 50, 35, 36, 37, 38, 51, gameScreen);
        Hexes[15] = new Hex(resArr[15], diceArr[15], 52, 51, 38, 39, 40, 41, gameScreen);
        Hexes[16] = new Hex(resArr[16], diceArr[16], 44, 53, 52, 41, 42, 43, gameScreen);
        Hexes[17] = new Hex(resArr[17], diceArr[17], 46, 47, 48, 53, 44, 45, gameScreen);
        Hexes[18] = new Hex(resArr[18], diceArr[18], 48, 49, 50, 51, 52, 53, gameScreen);


        //SETTING CENTERPOINTS

        //Define Center
        //Was (609, 600)
        Hexes[18].setPosition(609f, 600f);
        //First Row
        Hexes[0].setPosition(Hexes[18].getX()-2*XCos30,Hexes[18].getY()+300f);
        Hexes[1].setPosition(Hexes[0].getX()+2*XCos30, Hexes[0].getY());
        Hexes[2].setPosition(Hexes[0].getX()+4*XCos30, Hexes[0].getY());
        //Second Row
        Hexes[11].setPosition(Hexes[0].getX()-XCos30, Hexes[0].getY()-150f);
        Hexes[12].setPosition(Hexes[11].getX()+2*XCos30, Hexes[11].getY());
        Hexes[13].setPosition(Hexes[11].getX()+4*XCos30, Hexes[11].getY());
        Hexes[3].setPosition(Hexes[11].getX()+6*XCos30, Hexes[11].getY());
        //Third Row
        Hexes[10].setPosition(Hexes[18].getX()-4*XCos30, Hexes[18].getY());
        Hexes[17].setPosition(Hexes[18].getX()-2*XCos30, Hexes[18].getY());
        Hexes[14].setPosition(Hexes[18].getX()+2*XCos30, Hexes[18].getY());
        Hexes[4].setPosition(Hexes[18].getX()+4*XCos30, Hexes[18].getY());
        //Fourth Row
        Hexes[9].setPosition(Hexes[11].getX(), Hexes[18].getY()-150f);
        Hexes[16].setPosition(Hexes[9].getX()+2*XCos30, Hexes[9].getY());
        Hexes[15].setPosition(Hexes[9].getX()+4*XCos30, Hexes[9].getY());
        Hexes[5].setPosition(Hexes[9].getX()+6*XCos30, Hexes[9].getY());
        //Fifth Row
        Hexes[8].setPosition(Hexes[0].getX(), Hexes[9].getY()-150f);
        Hexes[7].setPosition(Hexes[8].getX()+2*XCos30, Hexes[8].getY());
        Hexes[6].setPosition(Hexes[8].getX()+4*XCos30, Hexes[8].getY());


        //SETTING BITMAPS
        for (Hex h: Hexes) {
            switch (h.getResource()){
                case 0:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("HexBrick"));
                    break;
                case 1:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("HexSheep"));
                    break;
                case 2:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("HexOre"));
                    break;
                case 3:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("HexWheat"));
                    break;
                case 4:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("HexWood"));
                    break;
                case 5:
                    h.setBitmap(gameScreen.getGame().getAssetManager().getBitmap("TempHex"));
                    break;
            }

        }

        //TESTING  V V V V V V V V V V V

        for (int i = 0; i<NoOfHexes; i++){
            for (int j = 0; j<6; j++) {
                System.out.print(Hexes[i].getNode(j) + " ");
            }
            System.out.println("|");
        }

    }

}
