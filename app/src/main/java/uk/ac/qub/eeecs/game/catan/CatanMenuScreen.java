package uk.ac.qub.eeecs.game.catan;

import android.graphics.Color;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class CatanMenuScreen extends GameScreen {

    private PushButton mPlayGameButton;
    private PushButton mInstructionsButton;
    private PushButton mOptionsButton;

    /**
     * Create the Catan menu screen
     *
     * @param game Game to which this screen belongs
     */
    public CatanMenuScreen(Game game) {
        super("CardScreen", game);

        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("PlayBtn", "img/catan/btnPlay.png");
        assetManager.loadAndAddBitmap("OptionsBtn", "img/catan/btnOptions.png");
        assetManager.loadAndAddBitmap("InstructionsBtn", "img/catan/btnInstructions.png");

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 2;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 4;

        mPlayGameButton = new PushButton(spacingX * 1.0f, spacingY * 3.0f, spacingX, spacingY/1.5f, "PlayBtn", this);
        mOptionsButton = new PushButton(spacingX * 1.0f, spacingY * 2.0f, spacingX, spacingY/1.5f, "OptionsBtn", this);
        mInstructionsButton = new PushButton(spacingX * 1.0f, spacingY * 1.0f, spacingX, spacingY/1.5f, "InstructionsBtn", this);
    }


    /**
     * Update the catan menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            //Update each button and transition if needed
            mPlayGameButton.update(elapsedTime);
            mOptionsButton.update(elapsedTime);
            mInstructionsButton.update(elapsedTime);

            if (mPlayGameButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new CatanGameScreen(mGame));
            else if(mOptionsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new CatanOptionsScreen(mGame));
            else if(mInstructionsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new CatanInstructionsScreen(mGame));
        }
    }

    /**
     * Draw the catan menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLACK);

        mPlayGameButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mInstructionsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mOptionsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
