package android.Mealboard.MealBoard.models;

import com.google.gson.annotations.SerializedName;

public class ItemModel {

    @SerializedName("id")
    private String id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("price")
    private String price;

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setId(String Id) {
        id = Id;
    }

    public void setCategory(String Category) {
        category = Category;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setImage(String Image) {
        image = Image;
    }


    public void setQuantity(String Quantity) {
        quantity = Quantity;
    }

    public void setPrice(String Price) {
        price = Price;
    }
}
