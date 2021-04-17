package android.Mealboard.MealBoard;

import android.Mealboard.MealBoard.database.DatabaseHandler;
import android.Mealboard.MealBoard.models.ItemModel;
import android.Mealboard.MealBoard.models.PlaceOrderResponse;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;
import android.Mealboard.MealBoard.utils.Constants;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    RecyclerView ItemRecyclerView;
    ImageView imageBack;
    TextView textTotalAmount;
    List<ItemModel> cartitems;
    Button buttonPlaceOrder;
    int TotalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textTotalAmount = findViewById(R.id.text_total_amount);
        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(v -> onBackPressed());
        ItemRecyclerView = findViewById(R.id.cart_recycler_view);
        ItemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ItemRecyclerView.setHasFixedSize(true);

        buttonPlaceOrder = findViewById(R.id.btn_placeorder);
        buttonPlaceOrder.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENSE_NAME, MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean(Constants.KEY_IS_LOGGEDIN, false);
            if(!isLoggedIn) {
                Toast.makeText(CartActivity.this,"Please Login First",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            else{
                if(preferences.getBoolean(Constants.KEY_IS_LOGGEDIN,false)) {
                    HashMap< String, String > params = new HashMap<>();
                    params.put("user_email", preferences.getString(Constants.KEY_EMAIL, "N/A"));
                    params.put("total_amount", String.valueOf(TotalAmount));
                    params.put("products", new Gson().toJson(cartitems));
                    params.put("order_date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                    Placeorder(params);
                }
            }
        });

        getCartItems();
    }

    private void Placeorder(HashMap <String, String> params){

        final ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Placing Order");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<PlaceOrderResponse> placeorderCall = networkService.placeorder(params);
        placeorderCall.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(@NonNull  Call<PlaceOrderResponse> call,@NonNull Response<PlaceOrderResponse> response) {
                PlaceOrderResponse responseBody = response.body();
                if(responseBody != null){
                    if(responseBody.getSuccess().equals("1")){
                        Toast.makeText(CartActivity.this, "Ordered Successfully!",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(CartActivity.this, "Order Could placed",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                        Toast.makeText(CartActivity.this, "Some Error Accured!",Toast.LENGTH_LONG).show();
                    }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull  Call<PlaceOrderResponse> call,@NonNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void getCartItems(){
        cartitems = new DatabaseHandler(getApplicationContext()).getcartitems();
        ItemRecyclerView.setAdapter(new ItemAdapter(cartitems));
        CalculateTotal();
    }
    private  void CalculateTotal(){
        TotalAmount = 0;
        for(int i=0;i<=cartitems.size()-1;i++){

            TotalAmount = TotalAmount + Integer.parseInt(cartitems.get(i).getPrice());

        }
        textTotalAmount.setText("Total Amt. \u20B9"+String.valueOf(TotalAmount));
    }
    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        List<ItemModel> item;
        ItemAdapter(List<ItemModel> item){
            this.item = item;
        }

        @NonNull
        @Override
        public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemAdapter.ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            if(item.get(position).getName() !=null && !item.get(position).getName().equals(""))
            {
                holder.textName.setText(item.get(position).getName());
            }else {
                holder.textName.setVisibility(View.GONE);
            }
            if(item.get(position).getQuantity() !=null && !item.get(position).getQuantity().equals(""))
            {
                holder.textQuantity.setText(item.get(position).getQuantity());
            }else {
                holder.textQuantity.setVisibility(View.GONE);
            }
            if(item.get(position).getPrice() !=null && !item.get(position).getPrice().equals(""))
            {
                holder.textPrice.setText("\u20B9" + item.get(position).getPrice());
            }else {
                holder.textPrice.setVisibility(View.GONE);
            }
            if(item.get(position).getImage() !=null && !item.get(position).getImage().equals(""))
            {
                Picasso.with(getApplicationContext()).load(item.get(position).getImage()).into(holder.imageItem);
            }else {
                holder.imageItem.setVisibility(View.GONE);
            }

            holder.textRemoveItem.setPaintFlags(holder.textRemoveItem.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.textRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatabaseHandler(getApplicationContext()).removeItem(item.get(position).getId());
                    cartitems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,cartitems.size());
                    CalculateTotal();
                    Toast.makeText(CartActivity.this,"Item Removed From Cart!", Toast.LENGTH_SHORT).show();
                }
            });
        }



        @Override
        public int getItemCount() {
            return item.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{
            ImageView imageItem;
            TextView textName,textQuantity,textPrice,textRemoveItem;
            public ItemViewHolder(@NonNull View view) {
                super(view);

                imageItem = view.findViewById(R.id.image_item);
                textName = view.findViewById(R.id.text_item_name);
                textQuantity = view.findViewById(R.id.text_item_quantity);
                textPrice = view.findViewById(R.id.text_item_price);
                textRemoveItem = view.findViewById(R.id.text_remove_item);
            }
        }
    }

}