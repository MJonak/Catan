package uk.ac.qub.eeecs.game.catan;

import java.lang.System;
public class HexMap {
    //The HexMap class is used to store the 19 hexes that make up the actual map alongside all related methods
    final short NoOfHexes = 19;
    Hex Hexes[] = new Hex[NoOfHexes];
    public HexMap(){
        //Same as the build map, here we need to find an efficient way of calculating all the
        // nodes associated with each hex (method will probably be related to step 2 in BuildMap constructor)

        //Step 1 - hexes 1-10
        byte[] arr0 = new byte[]{2, 3, 4, 31, 32, 33};
        byte[] arr1 = new byte[]{4, 5, 6, 7, 33, 34};
        for (int i = 1; i<10; i+=2){
            Hexes[i] = new Hex((byte)0, (byte)0, arr0.clone());
            Hexes[i+1] = new Hex((byte)0, (byte)0, arr1.clone());
            //Increment the values in the arrays
            for(int a = 0; a<3;a++){
                arr0[a] += 5;
                arr0[5-a] += 3;
                arr1[a] += 5;
                arr1[5-a] += 3;
            }
            arr1[3] += 2;   //arr1 isn't symmetric so the 4th value needs offset
        }

        //Step 2 - hexes 13-16
            //2a) Prep arr1 for hex 13
        arr1[0] = 32;
        for (int a = 1; a<4; a++){
            arr1[a] = (byte)(arr1[a-1]+1);
        }
        arr1[4] = 49; arr1[5] = 50;
            //2b) create hexes
        for(int i = 13; i<17; i++){
            Hexes[i] = new Hex((byte)0, (byte)0, arr1.clone());
            for (int a = 0; a<4;a++) {
                arr1[a]+=3;
            }
            arr1[4] += 1; arr1[5] += 1;
        }
        //Step 3 - exceptions (0, 11, 12, 17, 18)
        Hexes[0] = new Hex((byte)0, (byte)0, (byte)0, (byte)1, (byte)2, (byte)29, (byte)30, (byte)31);
        Hexes[11] = new Hex((byte)0, (byte)0, (byte)27, (byte)28, (byte)29, (byte)30, (byte)46, (byte)47);
        Hexes[12] = new Hex((byte)0, (byte)0, (byte)30, (byte)31, (byte)32, (byte)47, (byte)48, (byte)49);
        Hexes[17] = new Hex((byte)0, (byte)0, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)53);
        Hexes[18] = new Hex((byte)0, (byte)0, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53);

        //TESTING  V V V V V V V V V V V
        for (int i = 0; i<NoOfHexes; i++){
            for (short j = 0; j<6; j++) {
                System.out.print(Hexes[i].getNode(j) + " ");
            }
            System.out.println("|");
        }
    }

}
