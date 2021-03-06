package uk.ac.qub.eeecs.game.catan;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.catan.World.BuildMap;
import uk.ac.qub.eeecs.game.catan.World.Hex;
import uk.ac.qub.eeecs.game.catan.World.HexMap;
import uk.ac.qub.eeecs.game.catan.World.Node;
import uk.ac.qub.eeecs.game.catan.World.Road;

public class CatanGameScreen extends GameScreen {

    //TODO This screen should only be responsible for displaying the game, create a separate class to do the actual heavy lifting of the game
    //Game properties
    private int diceRoll;
    private boolean diceRolledThisTurn;
    public static short turnNo;
    private static int currentPlayer;

    public static int UIMode = 0; //0 - default view; 1 - buildUI; //TODO made the UIMode static to allow resetting the ui back to default after a touch event occurs on a node/road, could change back if better option is found
    public final int UI_DEFAULT = 0;
    public final int UI_BUILD = 1;
    public final int UI_SETUP_SETTLEMENT = -1;
    public final int UI_SETUP_ROAD = -2;
    public final int UI_SETUP_END_TURN = -3;

    private static int buildMode; //buildMode 0 - nothing; 1 - settlement ; 2 - city ; 3 - road  //TODO Buildmode may need to be reworked, only need to know whether to update roads or node
    public final int BUILD_MODE_OFF = 0;
    public final int BUILD_MODE_SETTLEMENT = 1;
    public final int BUILD_MODE_CITY = 2;
    public final int BUILD_MODE_ROAD = 3;

    private static int NoOfPlayers = 2;
    private static Player[] PlayerList = new Player[NoOfPlayers];
    //Board Elements
    private HexMap HM;
    public static BuildMap BM;
    private GameObject[] tokens = new GameObject[19];
    //UI Elements
    private PushButton btnBuild, btnRoll, btnEndTurn, btnSettlement, btnTown, btnRoad, btnBack;
    private PushButton[] buildUI = new PushButton[4];
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

        //Game constructor  //TODO This really needs to be a json
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
        mGame.getAssetManager().loadAndAddBitmap("btnTown", "img/catan/btnTown.png");
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
        mGame.getAssetManager().loadAndAddBitmap("Town0", "img/catan/TownPH.png");
        mGame.getAssetManager().loadAndAddBitmap("Town1", "img/catan/TownPH1.png");
        mGame.getAssetManager().loadAndAddBitmap("Town2", "img/catan/TownPH2.png");
        mGame.getAssetManager().loadAndAddBitmap("Town3", "img/catan/TownPH3.png");
        HM = new HexMap(this);
        BM = new BuildMap(HM,this);
        currentPlayer = 0;
        turnNo = 1;
        diceRolledThisTurn = false;
        buildMode = BUILD_MODE_SETTLEMENT;
        UIMode = UI_SETUP_SETTLEMENT; //Meaning first stage of setup
        for (int i = 0; i<NoOfPlayers;i++){
            PlayerList[i] = new Player(i);
            PlayerList[i].setStartingResources();
        }

        //Populate the tokens array with a token for each hex - using a fori instead of a foreach loop as i is needed to iterate through the tokens array
        for (int i = 0; i <19 ; i++) {
            tokens[i] = new GameObject(HM.Hexes[i].position.x, HM.Hexes[i].position.y, 40f, 40f, mGame.getAssetManager().getBitmap("token" + HM.Hexes[i].getDiceNo()), this);
        }

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
        btnTown = new PushButton(screenWidth * 0.1f, screenHeight*0.5f, screenWidth*0.18f, screenHeight*0.14f, "btnTown",  this);
        btnRoad = new PushButton(screenWidth * 0.1f, screenHeight*0.3f, screenWidth*0.18f, screenHeight*0.14f, "btnRoad",  this);
        btnBack = new PushButton(screenWidth * 0.9f, screenHeight*0.9f, screenWidth*0.08f, screenHeight*0.1f, "btnBack",  this);
        buildUI[0] = btnSettlement; buildUI[1] = btnRoad; buildUI[2] = btnBack; buildUI[3] = btnTown;
        defaultUI[0] = btnBuild;
    }

    //Method used to allow the node&road classes to access the current player number when a touch event occurs
    public static Player getCurrentPlayer(){return PlayerList[currentPlayer];}

    private void endTurn(){
        //Next player
        currentPlayer++;
        // If the last player has had their go, increase the turn counter and reset back to player 0
        if(currentPlayer>=NoOfPlayers){
            currentPlayer %= NoOfPlayers;
            turnNo++;
        }
    }

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        if(turnNo>2) {
            postGameSetupTurn(elapsedTime);
        } else {
            preGameSetupTurn(elapsedTime);
        }
    }

    //TODO Recursive method (call itself at the end of turn one, then call a normal game turn method which calls itself recursively over and over til the end of the game?)
    //      probably not ideal as we'd end up with a weird ladder of methods by turn like 50
    private void preGameSetupTurn(ElapsedTime elapsedTime) {
        switch(UIMode){
            case UI_SETUP_ROAD:        //Road button -> Place road
                btnRoad.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                if(btnRoad.isPushTriggered()){
                    buildMode = BUILD_MODE_ROAD;
                }
                break;
            case UI_SETUP_END_TURN:        //End Turn -> settlement button
                btnEndTurn.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                if(btnEndTurn.isPushTriggered()){
                    buildMode = BUILD_MODE_SETTLEMENT;
                    if(turnNo==2 && currentPlayer==NoOfPlayers-1){
                        UIMode = UI_DEFAULT;
                        for (Hex h: HM.Hexes) {
                            for (int n = 0; n < 6; n++) {
                                if(BM.nodes[h.getNode(n)].getBuildState()==1){
                                    PlayerList[BM.nodes[h.getNode(n)].getPlayer()].addResource(h.getResource(), 1);
                                }
                            }
                        }
                    }else{
                        UIMode = UI_SETUP_SETTLEMENT;
                    }
                    endTurn();
                }
                break;
        }

        //Update relevant map elements
        updateMapElements(elapsedTime);
    }

    private void updateMapElements(ElapsedTime elapsedTime) {
        if(buildMode == BUILD_MODE_SETTLEMENT || buildMode == BUILD_MODE_CITY) {
            for (Node n:BM.nodes) {
                n.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
            }
        }
        if(buildMode == BUILD_MODE_ROAD){
            for (Road r:BM.roads) {
                r.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
            }
        }
    }

    private void postGameSetupTurn(ElapsedTime elapsedTime){
        updateImplementButtons(elapsedTime);
        //If the dice have already been rolled, update the "End Turn" button, else update the "Roll Dice" button
        if (diceRolledThisTurn) {
            btnEndTurn.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
        } else {
            btnRoll.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
        }

        //Rolling dice
        if (btnRoll.isPushTriggered()) {
            //Roll the dice  //TODO REVIEW THIS AFTER CHANGING EVERY byte TO INT
            diceRoll =  ((int)(Math.random() * 5 + 1)+(int)(Math.random() * 5 + 1));
            giveOutResources();
            diceRolledThisTurn = true;
            System.out.println(diceRoll);
        }

        //Ending turn
        if (btnEndTurn.isPushTriggered()) {
            //End the turn
            diceRolledThisTurn = false;
            UIMode = UI_DEFAULT;
            endTurn();
            System.out.println("Plyr#: " + currentPlayer + "|" + PlayerList[currentPlayer].getResource( 0) + "-Brick " + PlayerList[currentPlayer].getResource( 1) + "-Wool " + PlayerList[currentPlayer].getResource( 2) + "-Ore " + PlayerList[currentPlayer].getResource( 3) + "-Grain " + PlayerList[currentPlayer].getResource( 4) + "-Wood" + "|| VP:" + PlayerList[currentPlayer].getVictoryPoints());
        }
    }

    private void giveOutResources() {
        for (Hex h : HM.Hexes) {
            if (h.getDiceNo() == diceRoll) {                         //Found a hex with the matching # token
                for (int i = 0; i < 6; i++) {                      //Iterate through the nodes
                    if (BM.nodes[h.getNode(i)].getBuildState() != 0) {  //Found a node on this hex which has a building
                        // Give the Player who owns the building the resource(s)
                        PlayerList[BM.nodes[h.getNode(i)].getPlayer()].addResource(h.getResource(), BM.nodes[h.getNode(i)].getBuildState());
                    }
                }
            }
        }
    }

    //TODO This method literally states that it does 2 things
    private void updateImplementButtons(ElapsedTime elapsedTime) {
        switch (UIMode) {
            case UI_DEFAULT://Update the default UI
                for (PushButton pushButton1 : defaultUI) {
                    pushButton1.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
                if (btnBuild.isPushTriggered()) {
                    //Open build UI, turn off build mode to prevent accidental building
                    UIMode = UI_BUILD;
                    buildMode = BUILD_MODE_OFF;
                }
                break;
            case 1://Update the buildUI
                for (PushButton pushButton : buildUI) {
                    pushButton.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
                //Implement button functionality
                if (btnSettlement.isPushTriggered()) {
                    if (PlayerList[currentPlayer].hasEnoughResourcesFor( 1))
                        buildMode = BUILD_MODE_SETTLEMENT;
                    else System.out.println("Not enough resources!");
                }
                if (btnRoad.isPushTriggered()) {
                    if (PlayerList[currentPlayer].hasEnoughResourcesFor( 3)) {
                        buildMode = BUILD_MODE_ROAD;
                    } else {
                        System.out.println("Not enough resources!");
                    }
                }
                if(btnTown.isPushTriggered()){
                    if (PlayerList[currentPlayer].hasEnoughResourcesFor( 2)) {
                        buildMode = BUILD_MODE_CITY;
                    } else {
                        System.out.println("Not enough resources!");
                    }
                }
                if (btnBack.isPushTriggered()) {
                    UIMode = UI_DEFAULT;
                }
                
                updateMapElements(elapsedTime);
                break;
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
        //Draw the board
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

        //Draw the UI elements
        switch (UIMode){
            case UI_DEFAULT:
                for (PushButton pushButton1 : defaultUI) {
                    pushButton1.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                }
                break;
            case UI_BUILD:
                for (PushButton pushButton : buildUI) {
                    pushButton.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                }
                break;
                            //SETUP PHASE
            case UI_SETUP_SETTLEMENT:
                btnSettlement.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                break;
            case UI_SETUP_ROAD:
                btnRoad.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                break;
            case UI_SETUP_END_TURN:
                btnEndTurn.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                break;


        }

        //Depending on whether the dice have been rolled this turn or not, display the correct button
        if(turnNo>2) {
            paint.setTextSize(20f);
            if (diceRolledThisTurn) {
                btnEndTurn.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
                graphics2D.drawText(String.valueOf(diceRoll), 300f, 100f, paint);
            } else {
                btnRoll.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
            }
            graphics2D.drawText(String.valueOf(currentPlayer), 330f, 100f, paint);
            paint.setTypeface(Typeface.MONOSPACE);
            float pos = 30f;
            graphics2D.drawText("  | Br| Wl| Or| Gr| Wd|| VP|", mGameScreenViewport.width*0.70f, pos, paint);
            for (Player p:PlayerList) {
                pos+=25;
                graphics2D.drawText(String.format("%1$s | %2$s | %3$d | %4$d | %5$d | %6$d || %7$d", p.getPlayerNo(), p.getResource(0), p.getResource(1), p.getResource(2), p.getResource(3), p.getResource(4), p.getVictoryPoints()  ), mGameScreenViewport.width*0.70f, pos, paint);
            }
        }
    }
}
