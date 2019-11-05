package uk.ac.qub.eeecs.game.catan;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.catan.World.BuildMap;
import uk.ac.qub.eeecs.game.catan.World.ClickableObject;
import uk.ac.qub.eeecs.game.catan.World.Hex;
import uk.ac.qub.eeecs.game.catan.World.HexMap;
import uk.ac.qub.eeecs.game.catan.World.Node;
import uk.ac.qub.eeecs.game.catan.World.Road;

public class CatanGameScreen extends GameScreen {

    //Game properties
    private byte diceRoll;
    private boolean diceRolledThisTurn;
    public static short turnNo;
    public static boolean setupSettlementPlaced;
    public static byte currentPlayer;
    public static byte UIMode = 0; //0 - default view; 1 - buildUI; //TODO made the UIMode static to allow resetting the ui back to default after a touch event occurs on a node/road, could change back if better option is found
    private static byte buildMode; //buildMode 0 - nothing; 1 - settlement ; 2 - city ; 3 - road
    public static byte NoOfPlayers = 2;
    public static Player[] PlayerList = new Player[NoOfPlayers];
    //Board Elements
    private HexMap HM; private BuildMap BM;
    private GameObject[] tokens = new GameObject[19];
    //UI Elements
    private PushButton btnBuild, btnRoll, btnEndTurn, btnSettlement, btnRoad, btnBack;
    private PushButton[] buildUI = new PushButton[3];
    private PushButton[] defaultUI = new PushButton[1];
    //Viewport Properties
    private final static float WORLD_WIDTH = 1210.0f;
    private final static float WORLD_HEIGHT = 1200.0f;
    private LayerViewport mGameLayerViewport;
    private ScreenViewport mGameScreenViewport;
    private final static float FOCUSED_VIEWPORT_WIDTH = 1500.0f; //TODO The focused viewport width is the width of the game layer viewport, how much of the map can be seen at once, will need to change this to allow zoom


    // ----------------
    public CatanGameScreen(Game game) {
        super("CatanGameScreen", game);

        //Game constructor
        mGame.getAssetManager().loadAndAddBitmap("TempHex", "img/catan/HexPH.png");
        mGame.getAssetManager().loadAndAddBitmap("HexWood", "img/catan/HexPHWood.png");
        mGame.getAssetManager().loadAndAddBitmap("HexBrick", "img/catan/HexPHBrick.png");
        mGame.getAssetManager().loadAndAddBitmap("HexOre", "img/catan/HexPHOre.png");
        mGame.getAssetManager().loadAndAddBitmap("HexWheat", "img/catan/HexPHWheat.png");
        mGame.getAssetManager().loadAndAddBitmap("HexSheep", "img/catan/HexPHSheep.png");
        mGame.getAssetManager().loadAndAddBitmap("Node0", "img/catan/SettlementPH.png");
        mGame.getAssetManager().loadAndAddBitmap("Node1", "img/catan/SettlementPH1.png");
        mGame.getAssetManager().loadAndAddBitmap("Node2", "img/catan/SettlementPH2.png");
        mGame.getAssetManager().loadAndAddBitmap("Node3", "img/catan/SettlementPH3.png");
        mGame.getAssetManager().loadAndAddBitmap("Road12-0", "img/catan/Road12PH.png");
        mGame.getAssetManager().loadAndAddBitmap("Road12-1", "img/catan/Road12PH1.png");
        mGame.getAssetManager().loadAndAddBitmap("Road12-2", "img/catan/Road12PH2.png");
        mGame.getAssetManager().loadAndAddBitmap("Road12-3", "img/catan/Road12PH3.png");
        mGame.getAssetManager().loadAndAddBitmap("Road23-0", "img/catan/Road23PH.png");
        mGame.getAssetManager().loadAndAddBitmap("Road23-1", "img/catan/Road23PH1.png");
        mGame.getAssetManager().loadAndAddBitmap("Road23-2", "img/catan/Road23PH2.png");
        mGame.getAssetManager().loadAndAddBitmap("Road23-3", "img/catan/Road23PH3.png");
        mGame.getAssetManager().loadAndAddBitmap("Road34-0", "img/catan/Road34PH.png");
        mGame.getAssetManager().loadAndAddBitmap("Road34-1", "img/catan/Road34PH1.png");
        mGame.getAssetManager().loadAndAddBitmap("Road34-2", "img/catan/Road34PH2.png");
        mGame.getAssetManager().loadAndAddBitmap("Road34-3", "img/catan/Road34PH3.png");
        mGame.getAssetManager().loadAndAddBitmap("btnBuild", "img/catan/btnBuild.png");
        mGame.getAssetManager().loadAndAddBitmap("btnRoll", "img/catan/btnRollDice.png");
        mGame.getAssetManager().loadAndAddBitmap("btnEndTurn", "img/catan/btnEndTurn.png");
        mGame.getAssetManager().loadAndAddBitmap("btnSettlement", "img/catan/btnSettlement.png");
        mGame.getAssetManager().loadAndAddBitmap("btnRoad", "img/catan/btnRoad.png");
        mGame.getAssetManager().loadAndAddBitmap("btnBack", "img/catan/btnBack.png");
        mGame.getAssetManager().loadAndAddBitmap("token2", "img/catan/token2.png");
        mGame.getAssetManager().loadAndAddBitmap("token3", "img/catan/token3.png");
        mGame.getAssetManager().loadAndAddBitmap("token4", "img/catan/token4.png");
        mGame.getAssetManager().loadAndAddBitmap("token5", "img/catan/token5.png");
        mGame.getAssetManager().loadAndAddBitmap("token6", "img/catan/token6.png");
        mGame.getAssetManager().loadAndAddBitmap("token0", "img/catan/token.png"); //Desert Token
        mGame.getAssetManager().loadAndAddBitmap("token8", "img/catan/token8.png");
        mGame.getAssetManager().loadAndAddBitmap("token9", "img/catan/token9.png");
        mGame.getAssetManager().loadAndAddBitmap("token10", "img/catan/token10.png");
        mGame.getAssetManager().loadAndAddBitmap("token11", "img/catan/token11.png");
        mGame.getAssetManager().loadAndAddBitmap("token12", "img/catan/token12.png");
        HM = new HexMap(this);
        BM = new BuildMap(HM,this);
        currentPlayer = 0;
        turnNo = 1;
        setupSettlementPlaced = false;
        diceRolledThisTurn = false;
        buildMode = 0;
        for (byte i = 0; i<NoOfPlayers;i++){
            PlayerList[i] = new Player(i);
        }

        //Populate the tokens array with a token for each hex - using a fori instead of a foreach loop as i is needed to iterate through the tokens array
        for (int i = 0; i <19 ; i++) {
            tokens[i] = new GameObject(HM.Hexes[i].position.x, HM.Hexes[i].position.y, 40f, 40f, mGame.getAssetManager().getBitmap("token" + HM.Hexes[i].getDiceNo()), this);
        }
 /*      //Fake touch events to simulate the placing of settlements & roads at the start of the game
        currentPlayer = 0;
        TouchEvent te = new TouchEvent(); Vector2 v2 = new Vector2();
        BM.nodes[34].updateTriggerActions(te, v2); BM.roads[56].updateTriggerActions(te, v2); BM.nodes[36].updateTriggerActions(te, v2);BM.roads[36].updateTriggerActions(te, v2);
        currentPlayer = 1;
        BM.nodes[30].updateTriggerActions(te, v2); BM.roads[30].updateTriggerActions(te, v2); BM.nodes[52].updateTriggerActions(te, v2); BM.roads[39].updateTriggerActions(te, v2);
        currentPlayer = 0;
        for (int i = 0; i < NoOfPlayers; i++) {
            for (int j = 0; j < 5; j++) {
                PlayerList[i].resources[j] = 0;
            }
        }
 */
        //Viewport constructor
        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        float aspectRatio = screenHeight / screenWidth;
        //Game layer viewport defined to be centered with the center of the map
        mGameLayerViewport = new LayerViewport(
                WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f,
                FOCUSED_VIEWPORT_WIDTH / 2.0f,
                aspectRatio * FOCUSED_VIEWPORT_WIDTH / 2.0f);

        //Game screen viewport defined to take up all the drawable space
        mGameScreenViewport = new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);


        //Define all the UI buttons
        //TODO calculate optimal spacing & use it
        btnBuild = new PushButton(screenWidth * 0.1f, screenHeight*0.1f, screenWidth*0.18f, screenHeight*0.14f, "btnBuild",  this);
        btnRoll = new PushButton(screenWidth* 0.1f, screenHeight * 0.9f, screenWidth*0.18f, screenHeight * 0.14f, "btnRoll",  this);
        btnEndTurn = new PushButton(screenWidth* 0.1f, screenHeight * 0.9f, screenWidth*0.18f, screenHeight * 0.14f, "btnEndTurn",  this);
        btnSettlement = new PushButton(screenWidth * 0.1f, screenHeight*0.1f, screenWidth*0.18f, screenHeight*0.14f, "btnSettlement",  this);
        btnRoad = new PushButton(screenWidth * 0.1f, screenHeight*0.3f, screenWidth*0.18f, screenHeight*0.14f, "btnRoad",  this);
        btnBack = new PushButton(screenWidth * 0.9f, screenHeight*0.9f, screenWidth*0.08f, screenHeight*0.1f, "btnBack",  this);
        buildUI[0] = btnSettlement; buildUI[1] = btnRoad; buildUI[2] = btnBack;
        defaultUI[0] = btnBuild;
    }

    //Method used to allow the node&road classes to access the current player number when a touch event occurs
    public static Player getCurrentPlayer(){return PlayerList[currentPlayer];}

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        if(turnNo>2) {
            switch (UIMode) {
                case 0://Update the default UI
                    for (int i = 0; i < defaultUI.length; i++) {
                        defaultUI[i].update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                    }
                    if (btnBuild.isPushTriggered()) {
                        //Open build UI
                        UIMode = 1;
                        buildMode = 0;
                    }
                    break;
                case 1://Update the buildUI
                    for (int i = 0; i < buildUI.length; i++) {
                        buildUI[i].update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                    }
                    //Implement button functionality
                    if (btnSettlement.isPushTriggered()) {
                        if (PlayerList[currentPlayer].hasEnoughResourcesFor((byte) 1))
                            buildMode = 1;
                        else System.out.println("Not enough resources!");
                    }
                    if (btnRoad.isPushTriggered()) {
                        if (PlayerList[currentPlayer].hasEnoughResourcesFor((byte) 3)) {
                            buildMode = 3;
                        } else {
                            System.out.println("Not enough resources!");
                        }
                    }
                    if (btnBack.isPushTriggered()) {
                        UIMode = 0;
                    }
                    //Update the clickable map elements for building
                    //TODO probably best to have a separate UI mode for actually building where the only visible button is btnBack
                    switch (buildMode) {
                        case 1: //Settlement
                            for (Node node : BM.nodes) {
                                node.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                            }
                            break;
                        case 2: //City
                            for (Node node : BM.nodes) {
                                node.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                            }
                            break;
                        case 3: //Road
                            for (Road road : BM.roads) {
                                road.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                            }
                            break;
                    }
                    break;
            }
            //If the dice have already been rolled, update the "End Turn" button, else update the "Roll Dice" button
            if (diceRolledThisTurn) {
                btnEndTurn.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
            } else {
                btnRoll.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
            }


            if (btnRoll.isPushTriggered()) {
                //Roll the dice
                //Using 3 byte casts as the inner two are required to round the doubles before adding (to prevent 13/14 from being reached in the event of both random() calls returning the max value of 1.0)
                //And the outer cast is due to 2 bytes making an int apparently, even though the maximum possible value this function could be before the cast is 12
                diceRoll = (byte) ((byte) (Math.random() * 6 + 1) + (byte) (Math.random() * 6 + 1));
                //Give out resources
                for (Hex h : HM.Hexes) {
                    if (h.getDiceNo() == diceRoll) {                         //Found a hex with the matching # token
                        for (byte i = 0; i < 6; i++) {                      //Iterate through the nodes
                            if (BM.nodes[h.getNode(i)].getBuildState() != 0) {  //Found a node on this hex which has a building
                                // Give the Player who owns the building the resource(s)
                                PlayerList[BM.nodes[h.getNode(i)].getPlayer()].addResource(h.getResource(), BM.nodes[h.getNode(i)].getBuildState());
                            }
                        }
                    }
                }
                diceRolledThisTurn = true;
                System.out.println(diceRoll);
            }
            if (btnEndTurn.isPushTriggered()) {
                //End the turn
                diceRolledThisTurn = false;
                UIMode = 0;
                currentPlayer++;
                // If the last player has had their go, increase the turn timer and reset back to player 0
                if(currentPlayer>=NoOfPlayers){
                    currentPlayer %= NoOfPlayers;
                    turnNo++;
                }

                System.out.println("Plyr#: " + currentPlayer + "|" + PlayerList[currentPlayer].getResource((byte) 0) + "-Brick " + PlayerList[currentPlayer].getResource((byte) 1) + "-Wool " + PlayerList[currentPlayer].getResource((byte) 2) + "-Ore " + PlayerList[currentPlayer].getResource((byte) 3) + "-Grain " + PlayerList[currentPlayer].getResource((byte) 4) + "-Wood" + "|| VP:" + PlayerList[currentPlayer].getVictoryPoints());
            }
        } else { //Setup
            //If the settlement hasn't been placed yet update all nodes to allow player to build a settlement
            if(!setupSettlementPlaced){
                for (Node n: BM.nodes) {
                    n.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
            } else { //Otherwise update all the roads
                for (Road r: BM.roads) {
                    r.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
            }
        }

    }

    Paint paint = new Paint();
    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLUE);
        for (Hex object: HM.Hexes
        ) {
            object.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }
        for (Road object: BM.roads
        ) {
            if (object.getBuildState()!=0)
            object.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }
        for (Node object: BM.nodes
        ) {
            if (object.getBuildState()!=0)
            object.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }
        for (GameObject token:tokens
        ) {
            token.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }

        switch (UIMode){
            case 0://Draw the default UI
                for (int i = 0; i < defaultUI.length; i++) {
                    defaultUI[i].draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                }
                break;
            case 1://Draw the buildUI
                for (int i = 0; i < buildUI.length; i++) {
                    buildUI[i].draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                }
                break;
        }

        //Depending on whether the dice have been rolled this turn or not, display the correct button
        paint.setTextSize(20f);
        if (diceRolledThisTurn){
            btnEndTurn.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
            graphics2D.drawText(String.valueOf(diceRoll), 300f, 100f, paint);
        }else{
            btnRoll.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }
        graphics2D.drawText(String.valueOf(currentPlayer), 330f, 100f, paint);
    }
}
