package internship.nov;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] subCategoryIdArray = {"1","2","3","4","5","6","7","8","9"};
    String[] categoryIdArray = {"2","2","3","3","3","3","4","4","4"};
    String[] subCategoryNameArray = {"Apple","Google","Shorts","Jeans","Tshirt","Shirt","Selfie Sticks","Mobile Cleaning Kits","Mobile Cables"};

    ArrayList<SubCategoryList> arrayList;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.sub_category_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<>();
        for(int i=0;i<subCategoryNameArray.length;i++){
            if(sp.getString(ConstantSp.CATEGORYID,"").equals(categoryIdArray[i])) {
                SubCategoryList list = new SubCategoryList();
                list.setSubCategoryId(subCategoryIdArray[i]);
                list.setCategoryId(categoryIdArray[i]);
                list.setName(subCategoryNameArray[i]);
                arrayList.add(list);
            }
        }
        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}