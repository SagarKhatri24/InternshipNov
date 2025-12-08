package internship.nov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class UserRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase db;
    ArrayList<UserList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

        recyclerView = findViewById(R.id.user_recyclerview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(UserRecyclerActivity.this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

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
            UserRecyclerAdapter adapter = new UserRecyclerAdapter(UserRecyclerActivity.this,arrayList);
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
        }


    }
}