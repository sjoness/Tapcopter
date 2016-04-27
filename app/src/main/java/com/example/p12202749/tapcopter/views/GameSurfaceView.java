package com.example.p12202749.tapcopter.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.p12202749.tapcopter.R;
import com.example.p12202749.tapcopter.game.objects.Explosion;
import com.example.p12202749.tapcopter.game.objects.Helicopter;
import com.example.p12202749.tapcopter.game.objects.Missile;
import com.example.p12202749.tapcopter.realm.models.Score;
import com.example.p12202749.tapcopter.realm.models.Settings;
import com.example.p12202749.tapcopter.utils.Background;
import com.example.p12202749.tapcopter.utils.Collision;
import com.example.p12202749.tapcopter.utils.DrawUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by sam on 16/04/2016.
 */
@SuppressLint("ViewConstructor")
public class GameSurfaceView extends SurfaceView implements Runnable {

    private final static int MAX_FPS = 30;                   // desired fps
    private final static int MAX_FRAME_SKIPS = 5;            // maximum number of frames to be skipped
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period
    public final static int MOVE_SPEED = -5;                 // the speed of the background

    private long missileStartTime;
    private Background bg;
    private Helicopter helicopter;
    private ArrayList<Missile> missiles;
    private Explosion explosion;
    private Random rand = new Random();

    private final SurfaceHolder holder;                               //Surface holder for the canvas
    private boolean ok = false;                                 //Boolean to control pause and resume
    private Thread t = null;                                    //Thread for the game logic

    private Point screenSize;                                   //Holds the screen size

    private long startReset;
    private boolean reset;
    private boolean disappear;
    private boolean started;
    private int best;
    private boolean newGameCreated;
    private boolean firstPlay;
    private int difficulty;

    public GameSurfaceView(Context context, Point screenS) {
        super(context);
        holder = getHolder(); //Used for the screenview
        screenSize = screenS;
        firstPlay = true;

        // Create an instance of the Background class to draw on the canvas
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.game_bg), screenSize);
        helicopter = new Helicopter(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 66, 25, screenSize);
        missiles = new ArrayList<>();
        missileStartTime = System.nanoTime();

        try {
            // Get the previous best score from the database
            best = Realm.getDefaultInstance().where(Score.class).findAllSorted("date", Sort.DESCENDING).get(0).getValue();
        } catch (ArrayIndexOutOfBoundsException e) {
            best = 0;
        }

        difficulty = Realm.getDefaultInstance().where(Settings.class).findFirst().getDifficulty();
    }

    private void updateCanvas() {
        if (helicopter.getPlaying()) {
            bg.update();
            helicopter.update();

            // check for collision with bottom of screen
            if (Collision.withBottom(helicopter, screenSize)) {
                helicopter.setPlaying(false);
            }

            //add missiles on timer
            long missileElapsed = (System.nanoTime() - missileStartTime) / 1000000;

            if (missileElapsed > ((2000 / difficulty) - helicopter.getScore() / 4)) {
                //first missile always goes down the middle
                if (missiles.size() == 0) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile),
                            screenSize.x + 10, screenSize.y / 2, 45, 15, helicopter.getScore()));
                } else {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile),
                            screenSize.x + 10, (int) (rand.nextDouble() * (screenSize.y)), 45, 15, helicopter.getScore()));
                }

                //reset timer
                missileStartTime = System.nanoTime();
            }

            //loop through every missile and check collision and remove
            for (int i = 0; i < missiles.size(); i++) {
                //update missile
                missiles.get(i).update();

                if (Collision.checkRectIntersection(missiles.get(i), helicopter)) {
                    missiles.remove(i);
                    helicopter.setPlaying(false);
                    break;
                }

                //remove missile if it is way off the screen
                if (missiles.get(i).getX() < -100) {
                    missiles.remove(i);
                    break;
                }
            }
        } else {
            helicopter.resetDY();

            if (!reset) {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                disappear = true;
                explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion), helicopter.getX(),
                        helicopter.getY() - 30, 134, 134);
            }

            explosion.update();
            long resetElapsed = (System.nanoTime() - startReset) / 1000000;

            if (firstPlay && !newGameCreated) {
                firstPlay = false;
                newGame();
            } else if (resetElapsed > 2500 && !newGameCreated) {
                newGame();
            }
        }
    }

    protected void drawCanvas(Canvas canvas) {
        bg.draw(canvas);

        if (!disappear) {
            helicopter.draw(canvas);
        }

        //draw missiles
        for (Missile m : missiles) {
            m.draw(canvas);
        }

        //draw explosion
        if (started) {
            explosion.draw(canvas);
        }

//        drawText(canvas);
        DrawUI.drawNewGameText(canvas, helicopter, screenSize, best, newGameCreated, reset);
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
                long beginTime = System.currentTimeMillis();
                int framesSkipped = 0;
                // update game state
                this.updateCanvas();
                // render state to the screen
                // draws the canvas on the panel
                this.drawCanvas(c);
                // calculate how long did the cycle take
                long timeDiff = System.currentTimeMillis() - beginTime;
                // calculate sleep time
                int sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    // if sleepTime > 0 put to sleep for short period of time
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }

                //ADD THIS IF WE ARE DOING LOTS OF WORK
                //If sleeptime is greater than a frame length, skip a number of frames
                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    // we need to catch up
                    // update without rendering
                    this.updateCanvas();
                    // add frame period to check if in next frame
                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
                }

                holder.unlockCanvasAndPost(c);
            }
        }
    }

    public void pause() {
        ok = false;

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t = null;
    }

    public void resume() {
        ok = true;
        t = new Thread(this);
        t.start();
    }

    public void newGame() {
        if (helicopter.getScore() * 3 > best) {
            best = helicopter.getScore() * 3;

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Score score = realm.createObject(Score.class);
            score.setValue(best);
            score.setDate(new Date());
            realm.commitTransaction();
            realm.close();
        }

        disappear = false;

        missiles.clear();

        helicopter.resetDY();
        helicopter.resetScore();
        helicopter.setY(screenSize.y / 2);

        newGameCreated = true;
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    public boolean isNewGameCreated() {
        return newGameCreated;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
