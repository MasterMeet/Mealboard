package android.Mealboard.MealBoard;

import android.Mealboard.MealBoard.utils.Constants;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.valdesekamdem.library.mdtoast.MDToast;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView imageMenu;
    TextView textUsername;
    CardView Cart, Grocery, myorder,myteam;
    TextView textEmail;
    LinearLayout login,logout,layout_grocery,layout_about,rate;
    View loginview,logoutview;
    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageMenu = findViewById(R.id.image_menu);
        textUsername = findViewById(R.id.text_username) ;
        textEmail = findViewById(R.id.text_email);
        login = findViewById(R.id.layou_login);
        logout = findViewById(R.id.layout_logout);
        loginview = findViewById(R.id.login_view);
        logoutview = findViewById(R.id.logout_view);
        imageMenu = findViewById(R.id.image_menu);
        Cart = findViewById(R.id.card_cart);
        Grocery  = findViewById(R.id.card_grocery);
        rate = findViewById(R.id.rate);
        rate.setOnClickListener(v -> MDToast.makeText(DashboardActivity.this,"Sorry! Our App Not Available In PlayStore",Toast.LENGTH_LONG,MDToast.TYPE_INFO).show());
        layout_about = findViewById(R.id.layout_about) ;
        layout_about.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MyTeamActivity.class)));
        layout_grocery = findViewById(R.id.layout_grocery);
        myorder = findViewById(R.id.myorders);
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyOrdersActivity.class));
            }
        });
        layout_grocery.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CategoriesActivity.class)));
        findViewById(R.id.card_grocery).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CategoriesActivity.class)));

        findViewById(R.id.card_cart).setOnClickListener(v ->  startActivity(new Intent(getApplicationContext(),CartActivity.class)));
        myteam = findViewById(R.id.myteam);
        myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,MyTeamActivity.class));
            }
        });

        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENSE_NAME, MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(Constants.KEY_IS_LOGGEDIN, false);
        if(!isLoggedIn){
            textUsername.setVisibility(View.VISIBLE);
            textUsername.setText("Welcome, Guest");
            login.setVisibility(View.VISIBLE);
            loginview.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            logoutview.setVisibility(View.GONE);
        }
        else{
            textEmail.setVisibility(View.VISIBLE);
            textUsername.setVisibility(View.VISIBLE);
            textUsername.setText(preferences.getString(Constants.KEY_USERNAME, "N/A"));
            textEmail.setText(preferences.getString(Constants.KEY_EMAIL, "N/A"));
            login.setVisibility(View.GONE);
            loginview.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            logoutview.setVisibility(View.VISIBLE);
        }

        imageMenu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));
        Cart.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });
        Grocery.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CategoriesActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            AlertDialog.Builder alert=new AlertDialog.Builder(DashboardActivity.this);
            alert.setTitle("Alert");
            alert.setMessage("Do you Really Want to Log Out ?");
            alert.setPositiveButton("YES", (dialog, which) -> {
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(DashboardActivity.this,"You Have Been Logged Out!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                finish();
            });
            alert.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
            alert.show();
        });
        login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        });
    }


}
