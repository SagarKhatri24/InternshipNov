package internship.nov;

import static internship.nov.ProductActivity.arrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    TextView discount,rating,unit,title,newPrice,oldPrice,description;
    ImageView add,image;

    SharedPreferences sp;
    int iPosition;

    Button buyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        iPosition = sp.getInt(ConstantSp.PRODUCT_INDEX,0);

        discount = findViewById(R.id.product_detail_discount);
        rating = findViewById(R.id.product_detail_rating);
        unit = findViewById(R.id.product_detail_unit);
        title = findViewById(R.id.product_detail_title);
        newPrice = findViewById(R.id.product_detail_new_price);
        oldPrice = findViewById(R.id.product_detail_old_price);
        description = findViewById(R.id.product_detail_description);

        add = findViewById(R.id.product_detail_plus);
        image = findViewById(R.id.product_detail_image);

        buyNow = findViewById(R.id.product_detail_buy_now);

        discount.setText(arrayList.get(iPosition).getDiscount());
        rating.setText(arrayList.get(iPosition).getRating());
        unit.setText(arrayList.get(iPosition).getPacking());
        title.setText(arrayList.get(iPosition).getName());
        newPrice.setText(ConstantSp.PRICE_SYMBOL+ arrayList.get(iPosition).getNewPrice());
        oldPrice.setText(ConstantSp.PRICE_SYMBOL+ arrayList.get(iPosition).getOldPrice());
        description.setText(arrayList.get(iPosition).getDescription());

        Glide.with(ProductDetailActivity.this).load(arrayList.get(iPosition).getImage()).placeholder(R.drawable.app_icon).into(image);

        oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

    }

    public void startPayment() {
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_xsiOz9lYtWKHgF");
        try {
            JSONObject options = new JSONObject();
            options.put("name", getApplicationContext().getString(R.string.app_name));
            //options.put("description", "Demoing Charges");
            options.put("description", "Buy Products");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher);
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(arrayList.get(iPosition).getNewPrice()) * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@gmail.com");
            preFill.put("contact", "7878232386");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Log.d("RESPONSE", e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Success : "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Error : "+s, Toast.LENGTH_SHORT).show();
    }
}