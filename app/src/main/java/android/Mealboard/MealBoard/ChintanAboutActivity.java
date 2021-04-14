package android.Mealboard.MealBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChintanAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chintan_about);
        RelativeLayout rel= findViewById(R.id.animatedlayout);
        /*AnimationDrawable animation=(AnimationDrawable) rel.getBackground();
        animation.setEnterFadeDuration(2000);
        animation.setExitFadeDuration(4000);
        animation.start();
*/
        Toast.makeText(ChintanAboutActivity.this,"Please Give 5 Star In PlayStore",Toast.LENGTH_LONG).show();
    }
}
