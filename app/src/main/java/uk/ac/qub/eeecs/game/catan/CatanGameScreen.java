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
    private HexMap HM;
    private BuildMap BM;
    private byte NoOfPlayers = 2;
    private static byte buildMode, currentPlayer; //buildMode 0 - nothing; 1 - settlement ; 2 - road ; 3 - town
    private boolean setup, diceRolledThisTurn;
    private Player[] PlayerList = new Player[NoOfPlayers];
    private PushButton btnBuild, btnRoll, btnEndTurn;

    //Viewport Properties
    private final static float WORLD_WIDTH = 1210.0f;
    private final static float WORLD_HEIGHT = 1200.0f;
    private LayerViewport mGameLayerViewport;
    private ScreenViewport mGameScreenViewport;
    //TODO The focused viewport width is the width of the game layer viewport, how much of the map can be seen at once, will need to change this to allow zoom
    private final static float FOCUSED_VIEWPORT_WIDTH = 1500.0f;


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
        HM = new HexMap(this);
        BM = new BuildMap(HM,this);
        setup = true;
        currentPlayer = 0;
        diceRolledThisTurn = false;
        buildMode = 1;
        for (byte i = 0; i<NoOfPlayers;i++){
            PlayerList[i] = new Player(i);
        }

        currentPlayer = 0;
        TouchEvent te = new TouchEvent(); Vector2 v2 = new Vector2();
        BM.nodes[7].updateTriggerActions(te, v2); BM.roads[56].updateTriggerActions(te, v2); BM.nodes[36].updateTriggerActions(te, v2);BM.roads[36].updateTriggerActions(te, v2);
        currentPlayer = 1;
        BM.nodes[30].updateTriggerActions(te, v2); BM.roads[30].updateTriggerActions(te, v2); BM.nodes[39].updateTriggerActions(te, v2); BM.roads[39].updateTriggerActions(te, v2);
        currentPlayer = 0;

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


        btnBuild = new PushButton(screenWidth * 0.1f, screenHeight*0.1f, screenWidth*0.18f, screenHeight*0.14f, "btnBuild",  this);
        btnRoll = new PushButton(screenWidth* 0.1f, screenHeight * 0.9f, screenWidth*0.18f, screenHeight * 0.14f, "btnRoll",  this);
        btnEndTurn = new PushButton(screenWidth* 0.1f, screenHeight * 0.9f, screenWidth*0.18f, screenHeight * 0.14f, "btnEndTurn",  this);

    }

    public static byte getCurrentPlayer(){return currentPlayer;}
    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();
        btnBuild.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
        //If the dice have already been rolled, update the "End Turn" button, else update the "Roll Dice" button
        if(diceRolledThisTurn){
            btnEndTurn.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
        }else{
            btnRoll.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
        }
        //TODO Implement UI buttons
        if (btnBuild.isPushTriggered()){
            //open build UI
        }

        if (btnRoll.isPushTriggered()){
            //Roll the dice
            diceRolledThisTurn = true;
        }
        if(btnEndTurn.isPushTriggered()) {
            //End the turn
            diceRolledThisTurn = false;
            currentPlayer+=1;currentPlayer%=NoOfPlayers;
            System.out.println(currentPlayer); //TODO This line is for testing to make sure that the modulo approach works, remove
        }

        switch(buildMode){
            case 1: //Settlement
                for (Node node: BM.nodes) {
                    node.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
                break;
            case 2: //Road
                for (Road road: BM.roads){
                    road.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
                break;
            case 3: //Town
                for (Node node: BM.nodes) {
                    node.update(elapsedTime, mGameLayerViewport, mGameScreenViewport);
                }
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

        btnBuild.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        //Depending on whether the dice have been rolled this turn or not, display the correct button
        if (diceRolledThisTurn){
            btnEndTurn.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }else{
            btnRoll.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }
    }
}
