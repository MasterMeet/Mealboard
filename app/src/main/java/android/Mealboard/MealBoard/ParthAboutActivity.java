package android.Mealboard.MealBoard;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.valdesekamdem.library.mdtoast.MDToast;

public class ParthAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parth_about);
        RelativeLayout rel= findViewById(R.id.animatedlayout);
        /*AnimationDrawable animation=(AnimationDrawable) rel.getBackground();
        animation.setEnterFadeDuration(2000);
        animation.setExitFadeDuration(4000);
        animation.start();
*/
        MDToast.makeText(ParthAboutActivity.this,"Parth Chauhan",MDToast.LENGTH_LONG).show();
    }
}
