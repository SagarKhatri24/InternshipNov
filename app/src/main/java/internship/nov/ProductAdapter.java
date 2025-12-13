package internship.nov;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;

    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSp.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView discount,rating,unit,title,newPrice,oldPrice;
        ImageView add,image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            discount = itemView.findViewById(R.id.custom_product_discount);
            rating = itemView.findViewById(R.id.custom_product_rating);
            unit = itemView.findViewById(R.id.custom_product_unit);
            title = itemView.findViewById(R.id.custom_product_title);
            newPrice = itemView.findViewById(R.id.custom_product_new_price);
            oldPrice = itemView.findViewById(R.id.custom_product_old_price);
            add = itemView.findViewById(R.id.custom_product_plus);
            image = itemView.findViewById(R.id.custom_product_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.discount.setText(arrayList.get(position).getDiscount());
        holder.rating.setText(arrayList.get(position).getRating());
        holder.unit.setText(arrayList.get(position).getPacking());
        holder.title.setText(arrayList.get(position).getName());
        holder.newPrice.setText(ConstantSp.PRICE_SYMBOL+arrayList.get(position).getNewPrice());
        holder.oldPrice.setText(ConstantSp.PRICE_SYMBOL+arrayList.get(position).getOldPrice());

        Glide.with(context).load(arrayList.get(position).getImage()).placeholder(R.drawable.app_icon).into(holder.image);

        holder.oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putInt(ConstantSp.PRODUCT_INDEX,position).commit();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
