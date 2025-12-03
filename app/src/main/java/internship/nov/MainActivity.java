package internship.nov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    public static EditText email;
    EditText password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView signup;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);

        signup = findViewById(R.id.main_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.main_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Email Id Required");
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Min. 6 Char Password Required");
                }
                else {
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){

                        while (cursor.moveToNext()){
                            String sUserId = cursor.getString(0);
                            String sName = cursor.getString(1);
                            String sEmail = cursor.getString(2);
                            String sPassword = cursor.getString(3);
                            String sGender = cursor.getString(4);
                            String sCity = cursor.getString(5);

                            Log.d("RESPONSE",sUserId+"\n"+sName+"\n"+sEmail+"\n"+sPassword+"\n"+sGender+"\n"+sCity);

                            sp.edit().putString(ConstantSp.USERID,sUserId).commit();
                            sp.edit().putString(ConstantSp.NAME,sName).commit();
                            sp.edit().putString(ConstantSp.EMAIL,sEmail).commit();
                            sp.edit().putString(ConstantSp.PASSWORD,sPassword).commit();
                            sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                            sp.edit().putString(ConstantSp.CITY,sCity).commit();

                        }

                        System.out.println("Login Successfully");
                        Log.d("RESPONSE", "Login Successfully");
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "Login Successfully", Snackbar.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Email Id/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}