package uk.ac.qub.eeecs.game.catan;

import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.World.BuildMap;
import uk.ac.qub.eeecs.game.catan.World.ClickableObject;
import uk.ac.qub.eeecs.game.catan.World.Hex;
import uk.ac.qub.eeecs.game.catan.World.HexMap;

public class CatanGameScreen extends GameScreen {
    private HexMap HM;
    private BuildMap BM;
    public CatanGameScreen(Game game) {

        super("CatanGameScreen", game);
        mGame.getAssetManager().loadAndAddBitmap("TempHex", "img/catan/HexPH.png");
        mGame.getAssetManager().loadAndAddBitmap("TempNode", "img/catan/SettlementPH.png");
        mGame.getAssetManager().loadAndAddBitmap("TempRoad12", "img/catan/Road12PH.png");
        mGame.getAssetManager().loadAndAddBitmap("TempRoad23", "img/catan/Road23PH.png");
        mGame.getAssetManager().loadAndAddBitmap("TempRoad34", "img/catan/Road34PH.png");
        HM = new HexMap(this);
        BM = new BuildMap(HM,this);




    }


    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();
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
        paint.setTextSize(20f); //Temporary resource and die display
        graphics2D.clear(Color.BLUE);
        for (Hex object: HM.Hexes
        ) {
            object.draw(elapsedTime, graphics2D);
            graphics2D.drawText(object.getDiceNo() + " " + object.getResource(),object.getX()-20f, object.getY(), paint);
            //Temporary resource and die display ^^
        }
        for (ClickableObject object: BM.roads
        ){
            object.draw(elapsedTime, graphics2D);
        }
        for (ClickableObject object: BM.nodes
        ) {
            object.draw(elapsedTime, graphics2D);
        }
    }
}
