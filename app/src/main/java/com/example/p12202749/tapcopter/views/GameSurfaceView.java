package com.example.p12202749.tapcopter.views;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.p12202749.tapcopter.R;
import com.example.p12202749.tapcopter.game.objects.Helicopter;
import com.example.p12202749.tapcopter.utils.Background;

/**
 * Created by sam on 16/04/2016.
 */
public class GameSurfaceView extends SurfaceView implements Runnable {

    private final static int MAX_FPS = 30;                   // desired fps
    private final static int MAX_FRAME_SKIPS = 5;            // maximum number of frames to be skipped
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period
    public final static int MOVE_SPEED = 0;                // the speed of the player
    private final Helicopter helicopter;

    SurfaceHolder holder;                                       //Surface holder for the canvas
    private boolean ok = false;                                 //Boolean to control pause and resume
    Thread t = null;                                            //Thread for the game logic

    private Point screenSize;                                   //Holds the screen size
    private Paint paint = new Paint();                          //Paint need to be able to draw the blocks

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped
    private Background bg;


    public GameSurfaceView(Context context, Point screenS) {
        //Place items in here for the constructor
        super(context);
        holder = getHolder(); //Used for the screenview
        screenSize = screenS;

        // Create an instance of the Background class to draw on the canvas
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.game_view_bg), screenSize);
        helicopter = new Helicopter(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 66, 25, 3, screenSize);
    }

    private void updateCanvas() {
        if (helicopter.getPlaying()) {
            bg.update();
            helicopter.update();
        }
    }

    protected void drawCanvas(Canvas canvas) {
        bg.draw(canvas);
        helicopter.draw(canvas);
    }

    public void run() {
        //Remove conflict between the UI thread and the game thread.
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        while (ok) {
            //perform canvas drawing
            if (!holder.getSurface().isValid()) {//if surface is not valid
                continue;//skip anything below it
            }

            Canvas c = holder.lockCanvas(); //Lock canvas, paint canvas, unlock canvas
            synchronized (holder) {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;  // resetting the frames skipped
                // update game state
                this.updateCanvas();
                // render state to the screen
                // draws the canvas on the panel
                this.drawCanvas(c);
                // calculate how long did the cycle take
                timeDiff = System.currentTimeMillis() - beginTime;
                // calculate sleep time
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    // if sleepTime > 0 put to sleep for short period of time
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }

//                Log.d("Sleep Time", String.valueOf(sleepTime));
                //ADD THIS IF WE ARE DOING LOTS OF WORK
                //If sleeptime is greater than a frame length, skip a number of frames
                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    // we need to catch up
                    // update without rendering
                    this.updateCanvas();
                    // add frame period to check if in next frame
                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
//                    Log.d("Skipping", "Skip");
                }

                holder.unlockCanvasAndPost(c);
            }
        }
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    public void pause() {
        ok = false;

        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }

        t = null;
    }

    public void resume() {
        ok = true;
        t = new Thread(this);
        t.start();
    }
}
