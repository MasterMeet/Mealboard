package android.Mealboard.MealBoard;

import android.Mealboard.MealBoard.models.LoginResponseModel;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;
import android.Mealboard.MealBoard.utils.Constants;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.valdesekamdem.library.mdtoast.MDToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView CrateAccount;
    EditText inputEmail, inputPassword;
    Button LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.input_emaillogin);
        inputPassword = findViewById(R.id.input_passwordlogin);
        LoginButton = findViewById(R.id.btnLogin) ;
        CrateAccount  = findViewById(R.id.create_account);
        CrateAccount.setPaintFlags(CrateAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        CrateAccount.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),RegisterActivity.class)));

        LoginButton.setOnClickListener(v -> {
            if(inputEmail.getText().toString().equals("")){
                MDToast.makeText(LoginActivity.this,"Please Enter Email",Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
            }
            else if (inputPassword.getText().toString().equals("")){
                MDToast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
            }
            else {
               login();
            }

        });
    }

    private void login(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<LoginResponseModel> login = networkService.login(inputEmail.getText().toString(),inputPassword.getText().toString());
        login.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseModel> call,@NonNull Response<LoginResponseModel> response) {
                LoginResponseModel responseBody = response.body();
                if (responseBody != null) {

                    if (responseBody.getSuccess().equals("1")) {
                        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENSE_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(Constants.KEY_IS_LOGGEDIN,true);
                        editor.putString(Constants.KEY_USERNAME,responseBody.getUserDetailsObject().getUserDetails().get(0).getFirst_name() + " " + responseBody.getUserDetailsObject().getUserDetails().get(0).getLast_name());
                        editor.putString(Constants.KEY_EMAIL,responseBody.getUserDetailsObject().getUserDetails().get(0).getEmail());
                        editor.apply();
                    MDToast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                    startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));

                    } else {
                    MDToast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseModel> call,@NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
}
