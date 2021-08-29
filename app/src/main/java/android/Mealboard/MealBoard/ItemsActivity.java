package android.Mealboard.MealBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Mealboard.MealBoard.models.ItemModel;
import android.Mealboard.MealBoard.models.ItemsResponseModel;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;


public class ItemsActivity extends AppCompatActivity {
    RecyclerView ItemRecyclerView;
    TextView textToolBarTitle;
    ImageView imageBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textToolBarTitle = findViewById(R.id.text_toolbar_title);
        textToolBarTitle.setText(getIntent().getStringExtra("category"));
        ItemRecyclerView = findViewById(R.id.items_recycler_view);
        ItemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ItemRecyclerView.setHasFixedSize(true);
        getItem();
    }
    private void getItem(){

        ProgressDialog progressDialog = new ProgressDialog(ItemsActivity.this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setMessage("Getting List....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<ItemsResponseModel> getItems = networkService.getItemsByCategories(getIntent().getStringExtra("category"));
        getItems.enqueue(new Callback<ItemsResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ItemsResponseModel> call,@NonNull Response<ItemsResponseModel> response) {
                progressDialog.cancel();
                ItemAdapter itemAdapter = new ItemAdapter(response.body().getItems());
                ItemRecyclerView.setAdapter(itemAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<ItemsResponseModel> call,@NonNull Throwable t) {
                progressDialog.cancel();
            }
        });
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        List<ItemModel> item;
        ItemAdapter(List<ItemModel> item){
            this.item = item;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container,parent,false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.textName.setText(item.get(position).getName());
            holder.textPrice.setText("\u20B9"+item.get(position).getPrice());
            holder.textQuantity.setText(item.get(position).getQuantity());
            Picasso.with(getApplicationContext()).load(item.get(position).getImage()).into(holder.imageItem);
            holder.cardItem.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ItemsDetailsActivity.class);
                intent.putExtra("image", item.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("quantity", item.get(holder.getAdapterPosition()).getQuantity());
                intent.putExtra("price", item.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("name", item.get(holder.getAdapterPosition()).getName());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{
            CardView cardItem;
            ImageView imageItem;
            TextView textName,textQuantity,textPrice;
            ItemViewHolder(@NonNull View view) {
                super(view);

                cardItem = view.findViewById(R.id.item_card_view);
                imageItem = view.findViewById(R.id.image_item);
                textName = view.findViewById(R.id.text_itemName);
                textQuantity = view.findViewById(R.id.text_quantity);
                textPrice = view.findViewById(R.id.text_price);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}