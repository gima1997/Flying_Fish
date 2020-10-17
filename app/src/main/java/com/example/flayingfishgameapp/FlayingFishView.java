package com.example.flayingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlayingFishView extends View {

    private Bitmap fish [] = new Bitmap[2];
    private int fishx = 10;
    private int fishy ;
    private int fishSpeed;

    private int canvaWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed =16;
    private Paint yellowPaint = new Paint();

    private int greenX ,greenY,greenSpeed =20;
    private Paint greenPaint = new Paint();

    private int redX ,redY ,redSpeed =35;
    private Paint redPaint = new Paint();

    private int score , lifeCounterOfFish;

    private boolean touch = false;

    private Bitmap BackgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];
    public FlayingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);


        BackgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW); //set the color yellow
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);  // set the color green
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);  //set the color red
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishy = 550;
        score = 0;
        lifeCounterOfFish=3  ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvaWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(BackgroundImage,0,0,null);
        int minimumFish = fish[0].getHeight();
        int maximumFish = canvasHeight-fish[0].getHeight()*3;
        fishy = fishy+fishSpeed;

        if(fishy < minimumFish)
        {
            fishy=minimumFish;
        }
        if (fishy > maximumFish)
        {
            fishy = maximumFish;
        }
        fishSpeed = fishSpeed+2;

        if(touch)
        {
            canvas.drawBitmap(fish[1],fishx,fishy,null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }

        yellowX= yellowX-yellowSpeed;

        if(hitBallChecker(yellowX,yellowY)){
            score = score+10;
            yellowX = -100;
        }

        if(yellowX <0)
        {
            yellowX = canvaWidth + 21 ;
            yellowY = (int) Math.floor(Math.random() * (maximumFish-minimumFish)) + minimumFish;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);



        greenX= greenX-greenSpeed;

        if(hitBallChecker(greenX,greenY)){
            score = score+20;
            greenX = -100;
        }

        if(greenX<0)
        {
            greenX = canvaWidth + 21 ;
            greenY = (int) Math.floor(Math.random() * (maximumFish-minimumFish)) + minimumFish;
        }
        canvas.drawCircle(greenX,greenY,35,greenPaint);

        redX= redX-redSpeed;

        if(hitBallChecker(redX,redY)){

            redX = -100;
            lifeCounterOfFish--;

            if(lifeCounterOfFish == 0)
            {
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                getContext().startActivity(gameOverIntent);
            }
        }

        if(redX<0)
        {
            redX = canvaWidth + 21 ;
            redY = (int) Math.floor(Math.random() * (maximumFish-minimumFish)) + minimumFish;
        }
        canvas.drawCircle(redX,redY,35,redPaint);

        canvas.drawText("Score : "+score,20,60,scorePaint);


        for(int i=0;i<3;i++){
            int x =(int) (450+life[0].getWidth()*1.5*i);
            int y=30;
            if(i < lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0], x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
         }



    }

    public boolean hitBallChecker(int x,int y)
    {
        if(fishx<x && x <(fishx+fish[0].getWidth()) && fishy <y && y < (fishy+fish[0].getHeight()))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch= true;

            fishSpeed = -25;
        }
        return true;
    }
}
