package android.Mealboard.MealBoard.database;
import android.Mealboard.MealBoard.models.ItemModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler<List> extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABSE_NAME="MealBoardDb";
    private static final String TABLE_CART="cart";
    private static final String KEY_ID="id";
    private static final String KEY_CATEGORY="category";
    private static final String KEY_NAME="name";
    private static final String KEY_IMAGE="image";
    private static final String KEY_QUANTITY="quantity";
    private static final String KEY_PRICE="price";


    public DatabaseHandler(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE "+ TABLE_CART + "(" + KEY_ID + " TEXT PRIMARY KEY, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_NAME + " TEXT, "
                + KEY_IMAGE + " TEXT, "
                + KEY_QUANTITY+ " TEXT, "
                + KEY_PRICE + " TEXT ) ";

        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_CART);
        onCreate(db);
    }
    public void AddToCart(ItemModel itemModel){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(KEY_ID, itemModel.getId());
        contentValue.put(KEY_CATEGORY, itemModel.getCategory());
        contentValue.put(KEY_NAME, itemModel.getName());
        contentValue.put(KEY_IMAGE, itemModel.getImage());
        contentValue.put(KEY_QUANTITY, itemModel.getQuantity());
        contentValue.put(KEY_PRICE, itemModel.getPrice());

        sqLiteDatabase.insert(TABLE_CART,null, contentValue);
        sqLiteDatabase.close();
    }
    public java.util.List<ItemModel> getcartitems(){
        String selectQuery = "SELECT * FROM " +TABLE_CART;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        java.util.List<ItemModel> cartitems = new ArrayList<ItemModel>();

        Cursor cursor= sqLiteDatabase.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                ItemModel itemModel = new ItemModel();
                itemModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                itemModel.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                itemModel.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                itemModel.setImage(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                itemModel.setQuantity(cursor.getString(cursor.getColumnIndex(KEY_QUANTITY)));
                itemModel.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                cartitems.add(itemModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cartitems;
    }
    public  void deletecart(){
        String selectQuery = "DELETE FROM " +TABLE_CART;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(selectQuery);

    }
    public void removeItem(String id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CART, KEY_ID + "= ?", new String[]{id});
        sqLiteDatabase.close();
    }
}
