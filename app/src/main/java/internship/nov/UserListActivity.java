package internship.nov;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    ListView listView;
    //String[] nameArray = {"John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.user_listview);

        //First Method
        /*ArrayAdapter adapter = new ArrayAdapter(UserListActivity.this, android.R.layout.simple_list_item_1,nameArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UserListActivity.this, nameArray[i], Toast.LENGTH_SHORT).show();
            }
        });*/

        //Second Method
        /*ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Richard");
        arrayList.add("Ibrahim");
        arrayList.add("Demo");
        arrayList.add("Joi");
        arrayList.add("John");

        arrayList.remove(2);
        arrayList.set(2,"Joy");

        arrayList.add(0,"Arpit");

        ArrayAdapter adapter = new ArrayAdapter(UserListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UserListActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });*/

        //Third Method
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Richard");
        arrayList.add("Ibrahim");
        arrayList.add("Demo");
        arrayList.add("Joi");
        arrayList.add("John");

        arrayList.remove(2);
        arrayList.set(2,"Joy");

        arrayList.add(0,"Arpit");

        UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UserListActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}