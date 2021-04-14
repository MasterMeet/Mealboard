package android.Mealboard.MealBoard;


import android.Mealboard.MealBoard.models.CityModel;
import android.Mealboard.MealBoard.models.CountryModel;
import android.Mealboard.MealBoard.models.RegistrationResponseModel;
import android.Mealboard.MealBoard.models.StateModel;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class RegisterActivity extends AppCompatActivity {

    Spinner countrySpinner, stateSpinner, citySpinner;
    EditText inputEmail, inputPassword, CPasswordreg, inputFirstName, inputLastName, inputMobile;
    RadioButton radioMale, radioFemale;
    Button RegisterNow;
    Boolean isgenderSelected;
    String selectedGender;
    TextView textLogInNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textLogInNow = findViewById(R.id.text_login_now);
        inputEmail = (EditText) findViewById(R.id.input_email_register);
        inputPassword = (EditText) findViewById(R.id.input_password_register);
        CPasswordreg = (EditText) findViewById(R.id.input_confirmPassword_register);
        inputFirstName = (EditText) findViewById(R.id.input_fname_register);
        inputLastName = (EditText) findViewById(R.id.input_lname_register);
        RegisterNow = (Button) findViewById(R.id.btnLogin);
        inputMobile = (EditText) findViewById(R.id.input_mobileNo_register);
        radioMale = (RadioButton) findViewById(R.id.radioButton);
        radioFemale = (RadioButton) findViewById(R.id.radioButton2);
        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        stateSpinner = (Spinner) findViewById(R.id.state_spinner);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isgenderSelected = true;
                    selectedGender = "Male";
                }
            }
        });
        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isgenderSelected = true;
                    selectedGender = "Female";
                }
            }
        });
        initCountrySpinner();
    }

    private void initCountrySpinner() {
        ArrayList<StateModel> states = new ArrayList<>();
        states.add(new StateModel("Select State"));
        ArrayAdapter<StateModel> statesAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, states);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(statesAdapter);
        ArrayList<CityModel> cities = new ArrayList<>();
        cities.add(new CityModel("Select City"));
        cities.add(new CityModel("Rajkot"));
        cities.add(new CityModel("Ahmadabad"));
        cities.add(new CityModel("Junagadh"));
        ArrayAdapter<CityModel> citiesAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, cities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(citiesAdapter);
        textLogInNow.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        });
        RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputFirstName.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter First Name", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (inputLastName.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Last Name", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (inputMobile.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (inputMobile.getText().toString().length()<10) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                }
                else if (countrySpinner.getSelectedItem().toString().equals("Select Country")) {
                    MDToast.makeText(RegisterActivity.this, "Please Select Country", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (stateSpinner.getSelectedItem().toString().equals("Select State")) {
                    MDToast.makeText(RegisterActivity.this, "Please Select State", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (citySpinner.getSelectedItem().toString().equals("Select City")) {
                    MDToast.makeText(RegisterActivity.this, "Please Select City", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (!isgenderSelected) {
                    MDToast.makeText(RegisterActivity.this, "Please select Gender", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (inputEmail.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Email", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (inputPassword.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Password", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (CPasswordreg.getText().toString().equals("")) {
                    MDToast.makeText(RegisterActivity.this, "Please Enter Confirm Password", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (!inputPassword.getText().toString().equals(CPasswordreg.getText().toString())) {
                    MDToast.makeText(RegisterActivity.this, "PassWord Not Matched!", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else if (!emailValidator(inputEmail.getText().toString())) {
                    MDToast.makeText(RegisterActivity.this, "Enter Valid Email!", Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("first_name", inputFirstName.getText().toString());
                    params.put("last_name", inputLastName.getText().toString());
                    params.put("gender", selectedGender);
                    params.put("mobile", inputMobile.getText().toString());
                    params.put("country", countrySpinner.getSelectedItem().toString());
                    params.put("state", stateSpinner.getSelectedItem().toString());
                    params.put("city", citySpinner.getSelectedItem().toString());
                    params.put("email", inputEmail.getText().toString());
                    params.put("password", inputPassword.getText().toString());

                    register(params);
                }
            }

        });

        ArrayList<CountryModel> countries = new ArrayList<>();
        countries.add(new CountryModel("Select Country"));
        countries.add(new CountryModel("Australia"));
        countries.add(new CountryModel("India"));
        countries.add(new CountryModel("United States"));
        countries.add(new CountryModel("United Kingdom"));

        ArrayAdapter<CountryModel> countriesAdapter = new ArrayAdapter<>(RegisterActivity.this, simple_spinner_item, countries);
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setAdapter(countriesAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("New South Wales"));
                    states.add(new StateModel("Queensland"));
                    states.add(new StateModel("South Australia"));
                    states.add(new StateModel("Tasmania"));
                    states.add(new StateModel("Victoria"));
                    states.add(new StateModel("Western Australia"));
                    states.add(new StateModel("Jervis Bay Territory"));
                    states.add(new StateModel("Northern Territory"));
                    states.add(new StateModel("Christmas Island"));
                    ArrayAdapter<StateModel> statesAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    stateSpinner.setAdapter(statesAdapter);

                } else if (position == 2) {
                    ArrayList<StateModel> states = new ArrayList<>();
                    states.add(new StateModel("Select State"));
                    states.add(new StateModel("Gujarat"));
                    states.add(new StateModel("Maharastra"));
                    states.add(new StateModel("TamilNadu"));
                    states.add(new StateModel("Delhi"));
                    states.add(new StateModel("Uttar Pradesh"));
                    states.add(new StateModel("Andhr Pradesh"));
                    states.add(new StateModel("odisha"));
                    states.add(new StateModel("Hariyana"));
                    states.add(new StateModel("Jammu-Kashmir"));
                    ArrayAdapter<StateModel> statesAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, states);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    stateSpinner.setAdapter(statesAdapter);
                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 1) {
                                ArrayList<CityModel> cities = new ArrayList<>();
                                cities.add(new CityModel("Select City"));
                                cities.add(new CityModel("Rajkot"));
                                cities.add(new CityModel("Surat"));
                                cities.add(new CityModel("Vadodara"));
                                cities.add(new CityModel("Junagadh"));
                                cities.add(new CityModel("Ahmadabad"));
                                cities.add(new CityModel("Bhavnagar"));
                                cities.add(new CityModel("Jamnagar"));
                                ArrayAdapter<CityModel> citiesAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, cities);
                                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                citySpinner.setAdapter(citiesAdapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void register(HashMap<String, String> param){

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Registering");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<RegistrationResponseModel> registerCall = networkService.register(param);
        registerCall.enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(@NonNull  Call<RegistrationResponseModel> call,@NonNull Response<RegistrationResponseModel> response) {
                RegistrationResponseModel responseBody = response.body();
                if(responseBody != null){
                    if(responseBody.getSuccess().equals("1")){
                        MDToast.makeText(RegisterActivity.this, responseBody.getMessage(),Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                        finish();
                    }
                    else{
                        MDToast.makeText(RegisterActivity.this, responseBody.getMessage(),Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull  Call<RegistrationResponseModel> call,@NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
