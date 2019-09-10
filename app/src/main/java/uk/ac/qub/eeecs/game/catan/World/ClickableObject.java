package uk.ac.qub.eeecs.game.catan.World;

import android.graphics.Bitmap;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.GraphicsHelper;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

public class ClickableObject extends GameObject {

    private boolean mIsTouched;

    private Vector2 mTouchLocation = new Vector2();

    protected ClickableObject(float x, float y, float width, float height,
                           Bitmap bitmap, GameScreen gameScreen){
        super(x, y, width, height, bitmap, gameScreen);
    }

    private void getTouchLocation(Vector2 touchLocation, float x, float y,
                                  LayerViewport layerViewport,
                                  ScreenViewport screenViewport) {
            // Convert and store the touch location
            ViewportHelper.convertScreenPosIntoLayer(screenViewport,
                    x, y, layerViewport, touchLocation);
    }

    private void updateTriggerActions(TouchEvent touchEvent, Vector2 touchLocation){
        mIsTouched = true;
    }

    private boolean isTouched(){

        if (mIsTouched){
            mIsTouched = false;
            return true;
        }
        return false;
    }

    public void update(ElapsedTime elapsedTime,
                       LayerViewport layerViewport, ScreenViewport screenViewport) {
        // Consider any touch events occurring in this update
        Input input = mGameScreen.getGame().getInput();

        BoundingBox bound = getBound();

        // Check for a trigger event on this button
        for (TouchEvent touchEvent : input.getTouchEvents()) {
            getTouchLocation(mTouchLocation, touchEvent.x, touchEvent.y,
                    layerViewport, screenViewport);
            if (bound.contains(mTouchLocation.x, mTouchLocation.y)) {
                // Check if a trigger has occurred and invoke specific behaviour
                updateTriggerActions(touchEvent, mTouchLocation);
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        //Determine an appropriate screen space bound
        if (GraphicsHelper.getClippedSourceAndScreenRect(this, layerViewport, screenViewport, drawSourceRect, drawScreenRect))
            graphics2D.drawBitmap(mBitmap, drawSourceRect, drawScreenRect, null);
    }
}
