package android.Mealboard.MealBoard;

import android.Mealboard.MealBoard.models.OrderResponseModel;
import android.Mealboard.MealBoard.models.OrdersModel;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;
import android.Mealboard.MealBoard.utils.Constants;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {
    ImageView imageBack;
    RecyclerView OrderRecyclerView;
    private final String TAG = "MyOrdersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(v -> onBackPressed());
        OrderRecyclerView = findViewById(R.id.orders_recycler_view);
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getorders();
    }
    private void getorders(){
        SpotsDialog alertDialog = new SpotsDialog(MyOrdersActivity.this,R.style.Custom2);
        alertDialog.show();
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENSE_NAME, MODE_PRIVATE);
        NetworkService services= NetworkClient.getClient().create(NetworkService.class);
        Call<OrderResponseModel> getorderscall=services.getOrders(preferences.getString(Constants.KEY_EMAIL,"N/A"));
        getorderscall.enqueue(new Callback< OrderResponseModel>() {

            public void onResponse(@NonNull Call<OrderResponseModel> call, @NonNull Response<OrderResponseModel> response) {
                OrderResponseModel orderResponse=response.body();
                if (orderResponse!=null){
                    OrderRecyclerView.setAdapter(new OrderAdapter(orderResponse.getOrders()));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Your Order List is Empty", Toast.LENGTH_SHORT).show();
                }
                alertDialog.cancel();

            }

            public void onFailure(Call<OrderResponseModel> call, Throwable t) {
                alertDialog.cancel();
            }
        });

    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

        List<OrdersModel> orders;
        OrderAdapter(List<OrdersModel> orders){
            this.orders=orders;
        }
        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item_container, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

            holder.textid.setText("Order Id :"+orders.get(holder.getAdapterPosition()).getId());
            holder.textdate.setText("Order Date :"+orders.get(holder.getAdapterPosition()).getOrder_date());
            holder.textprice.setText("Order Total Prices :"+"\u20B9"+orders.get(holder.getAdapterPosition()).getTotal_ammount());

            holder.layoutorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MyOrdersActivity.this,OrderDetailActivity.class);
                    intent.putExtra("products",orders.get(holder.getAdapterPosition()).getProducts());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }
    private static class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView textid,textdate,textprice;
        CardView layoutorder;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutorder=itemView.findViewById(R.id.order_layout);
            textid = itemView.findViewById(R.id.text_order_id);
            textdate = itemView.findViewById(R.id.text_order_date);
            textprice = itemView.findViewById(R.id.text_order_total);
        }
    }
}