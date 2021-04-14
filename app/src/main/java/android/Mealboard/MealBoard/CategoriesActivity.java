package android.Mealboard.MealBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Mealboard.MealBoard.models.CategoryModel;
import android.Mealboard.MealBoard.models.CategoryResponseModel;
import android.Mealboard.MealBoard.network.NetworkClient;
import android.Mealboard.MealBoard.network.NetworkService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CategoriesActivity extends AppCompatActivity {

    RecyclerView categoriesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoriesRecyclerView.setHasFixedSize(true);
        getCategories();

        findViewById(R.id.image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            }
        });
    }

    private void getCategories(){

        final ProgressDialog progressDialog = new ProgressDialog(CategoriesActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Getting Categories");
        progressDialog.setCancelable(false);
        progressDialog.show();
            NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
            Call<CategoryResponseModel> categoryResponseModelCall = networkService.getCategoriesFromServer();
            categoryResponseModelCall.enqueue(new Callback<CategoryResponseModel>() {

            @Override
            public void onResponse(@NonNull Call<CategoryResponseModel> call,@NonNull Response<CategoryResponseModel> response) {

                Toast.makeText(CategoriesActivity.this, "Success", Toast.LENGTH_SHORT).show();
                CategoryResponseModel categoryResponseModel = response.body();
                CategoriesAdapter categoriesAdapter= new CategoriesAdapter(categoryResponseModel.getCategories());
                categoriesRecyclerView.setAdapter(categoriesAdapter);
                progressDialog.cancel();
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponseModel> call,@NonNull Throwable t) {
                Toast.makeText(CategoriesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });
    }




    class CategoryViewHolder extends RecyclerView.ViewHolder {
             CardView categoryItemLayout;
            TextView textCategory;

            CategoryViewHolder(View view) {
                 super(view);
                categoryItemLayout = (CardView) view.findViewById(R.id.category_card_view);
                textCategory = (TextView) view.findViewById(R.id.text_category);
        }

    }

       public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
            List<CategoryModel> categories;
            CategoriesAdapter(List<CategoryModel> categories){
                this.categories = categories;
            }

            @Override
            public int getItemCount() {
                return categories.size();
            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_container,parent, false));
            }

            @Override
            public void onBindViewHolder(final CategoryViewHolder holder, int position) {
                holder.textCategory.setText(categories.get(holder.getAdapterPosition()).getCategory());
                holder.categoryItemLayout.setOnClickListener((View v) ->{
                    Intent intent = new Intent(getApplicationContext(), ItemsActivity.class);
                    intent.putExtra("category", categories.get(holder.getAdapterPosition()).getCategory());
                    Toast.makeText(CategoriesActivity.this, categories.get(holder.getAdapterPosition()).getCategory(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                });

            }

        }
    }

