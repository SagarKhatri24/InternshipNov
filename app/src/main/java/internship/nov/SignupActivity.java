package internship.nov;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword;
    CheckBox terms;
    Button signup;
    TextView login;

    //RadioButton male,female;
    RadioGroup gender;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String[] cityArray = {"Ahmedabad", "Vadodara", "Surat", "Rajkot", "Gandhinagar"};
    Spinner spinner;

    SQLiteDatabase db;
    String sGender;
    String sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);

        terms = findViewById(R.id.signup_terms);
        spinner = findViewById(R.id.signup_city);

        ArrayAdapter adapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCity = cityArray[i];
                Toast.makeText(SignupActivity.this, sCity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender = findViewById(R.id.signup_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                sGender = radioButton.getText().toString();
                Toast.makeText(SignupActivity.this, sGender, Toast.LENGTH_SHORT).show();
            }
        });

        /*male = findViewById(R.id.signup_male);
        female = findViewById(R.id.signup_female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignupActivity.this, male.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignupActivity.this, female.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        signup = findViewById(R.id.signup_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().equals("")) {
                    username.setError("Username Required");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else if (confirmPassword.getText().toString().trim().equals("")) {
                    confirmPassword.setError("Confirm Password Required");
                } else if (confirmPassword.getText().toString().trim().length() < 6) {
                    confirmPassword.setError("Min. 6 Char Confirm Password Required");
                } else if (!password.getText().toString().trim().matches(confirmPassword.getText().toString().trim())) {
                    confirmPassword.setError("Password Does Not Match");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignupActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                } else if (!terms.isChecked()) {
                    Toast.makeText(SignupActivity.this, "Please Accept Terms & Conditions", Toast.LENGTH_SHORT).show();
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='" + email.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        Toast.makeText(SignupActivity.this, "Email Id Already Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        String insertQuery = "INSERT INTO USERS VALUES(NULL,'" + username.getText().toString() + "','" + email.getText().toString() + "','" + password.getText().toString() + "','" + sGender + "','" + sCity + "')";
                        db.execSQL(insertQuery);
                        Toast.makeText(SignupActivity.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });

        login = findViewById(R.id.signup_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}