package online.ukapps.raspi_drone;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class onSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    private final class GestureListener extends SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private GestureListener() {
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        /*
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffY = e2.getY() - e1.getY();
                if (Math.abs(diffY) <= 100.0f || Math.abs(velocityY) <= 100.0f) {
                    return false;
                }
                if (diffY < 0.0f) {
                    onSwipeTouchListener.this.onSwipeTop();
                }
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
        }
        */

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY){
            boolean result = false;

            try {
                float diffX = motionEvent1.getX() - motionEvent.getX();
                float diffY = motionEvent1.getY() - motionEvent.getY();
                if (Math.abs(diffX) > Math.abs(diffY)){
                    if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                        if (diffX > 0)
                            onSwipeRight();
                        else
                            onSwipeLeft();
                        result = true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(diffY) > SWIPE_VELOCITY_THRESHOLD){
                    if (diffY > 0)
                        onSwipeBottom();
                    else
                        onSwipeTop();
                    result = true;
                } else {
                    onTouch();
                    result = true;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

    }

    public void onSwipeRight(){

    }

    public void onSwipeLeft(){

    }

    public void onSwipeBottom(){

    }

    public void onTouch(){

    }


    onSwipeTouchListener(Context ctx) {
        this.gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        return gestureDetector.onTouchEvent(event);
    }

    public void onSwipeTop() {

    }
}