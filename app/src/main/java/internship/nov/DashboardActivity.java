package internship.nov;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {

    TextView email;
    SharedPreferences sp;
    SQLiteDatabase db;

    Button profile,deleteProfile,logout,userList,userRecycler,activityFragment,whatsappTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

        email = findViewById(R.id.dashboard_email);
        email.setText(sp.getString(ConstantSp.NAME,""));

        whatsappTab = findViewById(R.id.dashboard_whatsapp_tab);
        whatsappTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,WhatsappTabActivity.class);
                startActivity(intent);
            }
        });

        activityFragment = findViewById(R.id.dashboard_activity_fragment);
        activityFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,ActivityToFragmentActivity.class);
                startActivity(intent);
            }
        });

        userList = findViewById(R.id.dashboard_users);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });

        userRecycler = findViewById(R.id.dashboard_users_recycler);
        userRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,UserRecyclerActivity.class);
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.dashboard_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        deleteProfile = findViewById(R.id.dashboard_delete_profile);
        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setIcon(R.drawable.app_icon);
                builder.setTitle("Delete Account!");
                builder.setMessage("Are You Sure Want To Delete Your Account!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String deleteQuery = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                        db.execSQL(deleteQuery);
                        Toast.makeText(DashboardActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();

                        sp.edit().clear().commit();
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setCancelable(false);
                builder.show();
            }
        });

        logout = findViewById(R.id.dashboard_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setIcon(R.drawable.app_icon);
                builder.setTitle("Logout!");
                builder.setMessage("Are You Sure Want To Logout!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sp.edit().clear().commit();
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DashboardActivity.this, "Rate Us", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                builder.setCancelable(false);
                builder.show();
            }
        });

    }
}