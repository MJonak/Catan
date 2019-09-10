package uk.ac.qub.eeecs.game.catan;

import android.graphics.Color;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.catan.World.ClickableObject;
import uk.ac.qub.eeecs.game.catan.World.HexMap;

public class CatanGameScreen extends GameScreen {
    private HexMap HM;
    protected CatanGameScreen(Game game) {
        super("CatanGameScreen", game);
        mGame.getAssetManager().loadAndAddBitmap("TempHex", "img/catan/HexPH.png");
        HM = new HexMap(this);
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

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLUE);
        for (ClickableObject object: HM.Hexes
             ) {
            object.draw(elapsedTime, graphics2D);
        }
    }
}
