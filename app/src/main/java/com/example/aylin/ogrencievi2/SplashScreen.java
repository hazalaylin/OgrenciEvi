package com.example.aylin.ogrencievi2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread mSplashThread;//thread classdan obje olustrduk uygulamann 4 saniye uyutulmasi icin
        mSplashThread = new Thread(){
            @Override public void run(){
                try {

                    synchronized(this){
                        wait(3000);
                    }
                }catch(InterruptedException ex){

                }
                finally{

                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };//thread objesini olustrduk ve istedmz sekilde sekillendrdik
        mSplashThread.start();// thread objesini calistriyoruz

    }
}
