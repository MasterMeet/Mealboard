package android.Mealboard.MealBoard;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.valdesekamdem.library.mdtoast.MDToast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        RelativeLayout rel= findViewById(R.id.animatedlayout);
        /*AnimationDrawable animation=(AnimationDrawable) rel.getBackground();
        animation.setEnterFadeDuration(2000);
        animation.setExitFadeDuration(4000);
        animation.start();
*/
        MDToast.makeText(AboutActivity.this,"Please Give 5 Star In PlayStore",Toast.LENGTH_LONG,MDToast.TYPE_INFO).show();
    }
}
