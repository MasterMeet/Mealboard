package android.Mealboard.MealBoard;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MyTeamActivity extends AppCompatActivity {
    CardView meet,meetraj,chintan,parth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        meet = findViewById(R.id.meet);
        chintan = findViewById(R.id.chintan);
        meetraj = findViewById(R.id.meetraj);
        parth = findViewById(R.id.parth);

        meet.setOnClickListener(v -> startActivity(new Intent(MyTeamActivity.this,AboutActivity.class)));
        chintan.setOnClickListener(v -> startActivity(new Intent(MyTeamActivity.this,ChintanAboutActivity.class)));
        meetraj.setOnClickListener(v -> startActivity(new Intent(MyTeamActivity.this,MeetrajAboutActivity.class)));
        parth.setOnClickListener(v -> startActivity(new Intent(MyTeamActivity.this,ParthAboutActivity.class)));
    }
}
