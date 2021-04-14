package android.Mealboard.MealBoard;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Mealboard.MealBoard.models.ItemModel;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView OrderRecyclerView;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        List<ItemModel> items = new ArrayList<>();
        OrderRecyclerView = findViewById(R.id.order_detail_recycler_view);
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String products = getIntent().getStringExtra("products");
        Gson gson=new Gson();
        ItemModel[] item = gson.fromJson(products,ItemModel[].class);
        items = Arrays.asList(item);
        items = new ArrayList(items);
        OrderRecyclerView.setAdapter(new ItemAdapter(items));


    }
    private class ItemAdapter extends RecyclerView.Adapter< ItemAdapter.WatchViewHolder> {

        List<ItemModel> item;
        ItemAdapter(List<ItemModel> watch){
            this.item = watch;
        }

        @NonNull
        @Override
        public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new WatchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container,parent,false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
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
            holder.CardItem.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ItemsDetailsActivity.class);
                intent.putExtra("image", item.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("price", item.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("name", item.get(holder.getAdapterPosition()).getName());
                intent.putExtra("id", item.get(holder.getAdapterPosition()).getId());
                intent.putExtra("category", item.get(holder.getAdapterPosition()).getCategory());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        public class WatchViewHolder extends RecyclerView.ViewHolder{
            CardView CardItem;
            ImageView imageItem;
            TextView textName,textQuantity,textPrice;
            public WatchViewHolder(@NonNull View view) {
                super(view);

                CardItem = view.findViewById(R.id.item_card_view);
                imageItem = view.findViewById(R.id.image_item);
                textName = view.findViewById(R.id.text_itemName);
                textQuantity = view.findViewById(R.id.text_quantity);
                textPrice = view.findViewById(R.id.text_price);
            }
        }
    }

}