package internship.nov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    GridView listView;
    //String[] nameArray = {"John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim","John","Richard","Ibrahim"};
    SQLiteDatabase db;
    ArrayList<UserList> arrayList;

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

        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

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
        /*ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Richard");
        arrayList.add("Ibrahim");
        arrayList.add("Demo");
        arrayList.add("Joi");
        arrayList.add("John");

        arrayList.remove(2);
        arrayList.set(2,"Joy");

        arrayList.add(0,"Arpit");

        UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
        listView.setAdapter(adapter);*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UserListActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });*/

        //Fourth Method
        String selectData = "SELECT * FROM USERS";
        Cursor cursor = db.rawQuery(selectData,null);
        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
           while (cursor.moveToNext()){
               String sUserId = cursor.getString(0);
               String sName = cursor.getString(1);
               String sEmail = cursor.getString(2);
               String sPassword = cursor.getString(3);
               String sGender = cursor.getString(4);
               String sCity = cursor.getString(5);

               UserList list = new UserList();
               list.setUserId(sUserId);
               list.setName(sName);
               list.setEmail(sEmail);
               list.setGender(sGender);
               list.setCity(sCity);

               arrayList.add(list);
           }
            UserListAdapter adapter = new UserListAdapter(UserListActivity.this,arrayList);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
        }

    }
}