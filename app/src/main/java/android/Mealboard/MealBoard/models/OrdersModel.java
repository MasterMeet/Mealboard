package android.Mealboard.MealBoard.models;

import com.google.gson.annotations.SerializedName;

public class OrdersModel {
    @SerializedName("id")
    private String id;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("total_amount")
    private String total_amount;

    @SerializedName("products")
    private String products;

    @SerializedName("order_date")
    private String order_date;

    public String getId() {
        return id;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getTotal_ammount() {
        return total_amount;
    }

    public String setTotal_ammount(String total_amount) {
        return this.total_amount;
    }

    public String getProducts() {
        return products;
    }

    public String getOrder_date() {
        return order_date;
    }
}
