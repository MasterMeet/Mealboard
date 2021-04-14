package android.Mealboard.MealBoard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.Mealboard.MealBoard.database.DatabaseHandler;
import android.Mealboard.MealBoard.models.ItemModel;
import java.util.List;

public class ItemsDetailsActivity extends AppCompatActivity {
    ImageView imageaddtocart;
    ImageView imageItem;
    ImageView imageBack;
    TextView txtQuantity,txtPrice,textToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_details);

        imageaddtocart = (ImageView) findViewById(R.id.addtocart) ;
        imageItem = findViewById(R.id.image_item);
        txtQuantity = findViewById(R.id.text_quantity);
        txtPrice = findViewById(R.id.text_price);
        textToolbarTitle = findViewById(R.id.text_toolbar_title);
        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(imageItem);
        textToolbarTitle.setText(getIntent().getStringExtra("name"));
        txtPrice.setText("\u20B9"+getIntent().getStringExtra("price"));
        txtQuantity.setText(getIntent().getStringExtra("quantity"));

        imageaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel itemModel = new ItemModel();
                itemModel.setId(getIntent().getStringExtra("id"));
                itemModel.setCategory(getIntent().getStringExtra("category"));
                itemModel.setName(getIntent().getStringExtra("name"));
                itemModel.setImage(getIntent().getStringExtra("image"));
                itemModel.setQuantity(getIntent().getStringExtra("quantity"));
                itemModel.setPrice(getIntent().getStringExtra("price"));

                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.AddToCart(itemModel);

                Toast.makeText(ItemsDetailsActivity.this,"Added To Cart !", Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        List<ItemModel> cartitems = databaseHandler.getcartitems();

        for (int i=0; i<=cartitems.size()-1; i++){
            Log.d("CART_DATA", cartitems.get(i).getName() + " - " + cartitems.get(i).getPrice());

        }
    }
}