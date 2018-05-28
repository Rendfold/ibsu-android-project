package com.example.leo_pc.android_project;

/**
 * Created by LEO-PC on 5/28/2018.
 */
import java.util.logging.Logger;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.logging.Level;

public class GameThread extends Thread {
    private final Logger logger = Logger.getAnonymousLogger();
    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();

        while(running)  {
            Canvas canvas= null;
            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (canvas)  {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch(Exception err) {
                //Log.e("" + err.getCause()); why? I thought we were friends LOG!
                logger.log(Level.SEVERE, "an exception was thrown", err);
            } finally {
                if(canvas!= null)  {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // we want it to be in miliseconds
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 80)  {
                waitTime= 80; // Millisecond.
            }

            try {
                // Sleep.
                this.sleep(waitTime);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}