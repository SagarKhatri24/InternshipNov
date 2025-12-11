package internship.nov;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] discountPercentageArray = {"29", "38", "21", "43", "33"};
    String[] packingArray = {"4 x 48 g", "1 x 500 g", "5 kg", "300 g Pack of 2", "300 g Pack of 2"}
    String[] ratingArray = {"4.1", "4.2", "4.4", "4.3", "4.4"};
    String[] nameArray = {
            "Odonil Bathroom Air Freshener | Assorted Blocks | Mixed Fragrance | Long Lasting Odour Blocks",
            "Nutraj Round Kishmish Raisins",
            "FORTUNE Chakki Fresh Atta",
            "PEPSODENT Cavity Protection Toothpaste",
            "Closeup Long lasting 18 Hours Of Fresh Breath & White Teeth Toothpaste"
    };
    String[] oldPriceArray = {"212", "399", "277", "226", "270"};
    String[] newPriceArray = {"150", "249", "218", "128", "180"};
    String[] imageArray = {
            "https://rukminim2.flixcart.com/image/832/832/xif0q/air-freshener/9/b/q/192-bathroom-air-freshener-assorted-blocks-mixed-fragrance-long-original-imahgb9wyjtkkfvh.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/832/832/xif0q/nut-dry-fruit/q/y/j/500-round-kishmish-1-pouch-nutraj-original-imah82t2ekjgkmp4.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/832/832/kqidx8w0/flour/3/j/5/chakki-fresh-atta-whole-wheat-flour-fortune-original-imag4gb4gkcggbre.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/832/832/xif0q/toothpaste/i/a/9/-original-imahdhdzycwnumyq.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/832/832/xif0q/toothpaste/n/j/7/-original-imahahnsnt6ha4gy.jpeg?q=70"
    };
    String[] descriptionArray = {
            "Let the small spaces in your home get the attention they deserve with easy-to-use::nature-inspired fragrances::After a hectic day of work and travel dulls your mood::just a calm and normal living area and bedroom is not enough to lift your dull mood . To enhance your living area and bedrooms you need something premium. And for that we have Odonil Room Freshening Spray which keeps your bedrooms and living rooms fresh and fragrant with its premium fragrances.",
            "Indulge in the sweetness of Nutraj Special Raisins and fall in love, all over again. Raisins generally have a dry texture that makes them look simple and subtle but they are rich in iron, copper and vitamins that may help prevent anemia and have cancer fighting properties. Mix these plump, moist and organic raisins into a simple bowl of oatmeal or bag of trail mix to double the deliciousness of your food bowl. Storage Information: Store in cool and dry place in an air tight container.",
            "Handpicked from the finest wheat fields across India, every golden amber grain of wheat used for Fortune chakki fresh atta goes through a traditional chakki process that retains all the goodness and natural taste while also giving you the nutritional benefits of dietary fibre. This natural process makes sure that your rotis stay soft and fluffy for long. Made with superior wheat blend. Traditional Chakki Process. Absorbs water more efficiently to make your rotis soft and fluffy. Intensive wheat cleaning process and hygienic packaging. High perfection in granulation. Absolutely pristine and untouched by hand. No preservatives added. Assurance of consistent quality.",
            "Pepsodent Germicheck Anti-Cavity Toothpaste has the power of CPC Clay Technology & Fluoride that provides effective Anti-Cavity protection many hours after brushing!",
            "Do you brush your teeth hurriedly? Do you often forget to use mouthwash or not use it at all? Are your teeth dull and covered with a yellowish coating of plaque? Do you have bad breath? If your answer to the above questions is yes, then New Closeup Gel Toothpaste with Zinc fresh technology is for you! Bad breath is a common condition that affects many people and has health as well as social implications. It can be caused by the millions of bacteria that live in your mouth. If you don't brush everyday plaque & germs can form resulting in bad breath. Closeup Gel Toothpaste has Zinc Fresh Technology which fights against 99% germs to give you 18 hrs of long-lasting fresh breath. Its proprietary technology gives intense freshness & burst of minty cooling. So, kiss goodbye to bad breath and say hello to longer lasting fresh breath for up to 18 hours*. Its purifying gel cleans to the deepest corners of your mouth. It also contains natural extracts of tea tree and eucalyptus which leave you feeling refreshed. Add Closeup Red Gel Toothpaste with Zinc Fresh Technology to your daily oral care routine and step out into the world with confidence! *Based on in- study with regular use over 4 weeks."
    };

    ArrayList<ProductList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(nameArray[i]);
            list.setDiscount(discountPercentageArray[i]);
            list.setImage(imageArray[i]);
            list.setRating(ratingArray[i]);
            list.setPacking(packingArray[i]);
            list.setOldPrice(oldPriceArray[i]);
            list.setNewPrice(newPriceArray[i]);
            list.setDescription(descriptionArray[i]);
            arrayList.add(list);
        }
        ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
    }
}