package android.Mealboard.MealBoard.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("success")
    private String success;

    @SerializedName("message")
    private String message;

    @SerializedName("user_details")
    private UserDetailModel userDetailsObject;

    public UserDetailModel getUserDetailsObject() {
        return userDetailsObject;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
