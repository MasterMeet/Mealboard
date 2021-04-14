package android.Mealboard.MealBoard.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsResponseModel {

    @SerializedName("items")
    private List<ItemModel> items;

    public List<ItemModel> getItems(){
        return items;
    }
}


