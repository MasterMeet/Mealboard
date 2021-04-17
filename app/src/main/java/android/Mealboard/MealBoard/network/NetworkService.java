package android.Mealboard.MealBoard.network;

import android.Mealboard.MealBoard.models.CategoryResponseModel;
import android.Mealboard.MealBoard.models.ItemsResponseModel;
import android.Mealboard.MealBoard.models.LoginResponseModel;
import android.Mealboard.MealBoard.models.OrderResponseModel;
import android.Mealboard.MealBoard.models.PlaceOrderResponse;
import android.Mealboard.MealBoard.models.RegistrationResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkService {
    @POST("categories.php")
    Call<CategoryResponseModel> getCategoriesFromServer();

    @FormUrlEncoded
    @POST("items.php")
    Call<ItemsResponseModel> getItemsByCategories(@Field("category") String category);

    @FormUrlEncoded
    @POST("registration.php")
    Call<RegistrationResponseModel> register(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponseModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("place_order.php")
    Call<PlaceOrderResponse> placeorder(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("order_history.php")
    Call<OrderResponseModel> getOrders(@Field("email") String email);
}
