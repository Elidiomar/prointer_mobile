package pitagoras.prointer.mobile.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pitagoras.prointer.mobile.AppLoader;
import pitagoras.prointer.mobile.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    AppLoader.setInitial();
                    Thread.sleep(500);
                    //runOnUiThread(new Runnable() {
                    //public void run() {
                    //Toast.makeText(AppLoader.getContext(),"Carregando...", Toast.LENGTH_SHORT).show();
                    //    }
                    //});
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();
    }
}
