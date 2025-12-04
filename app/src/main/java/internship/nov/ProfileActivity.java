package internship.nov;

import android.content.SharedPreferences;
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
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    EditText username, email, password, confirmPassword;
    Button submit,editProfile;

    CardView confirmPasswordCard;
    TextView confirmPasswordText;
 
    RadioButton male,female;
    RadioGroup gender;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String[] cityArray = {"Ahmedabad", "Vadodara", "Surat", "Rajkot", "Gandhinagar"};
    Spinner spinner;

    SQLiteDatabase db;
    String sGender;
    String sCity;
    SharedPreferences sp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("Internship.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),PASSWORD VARCHAR(50),GENDER VARCHAR(10),CITY VARCHAR(20))";
        db.execSQL(tableQuery);

        username = findViewById(R.id.profile_username);
        email = findViewById(R.id.profile_email);
        password = findViewById(R.id.profile_password);
        confirmPassword = findViewById(R.id.profile_confirm_password);
        confirmPasswordCard = findViewById(R.id.profile_confirm_password_card);
        confirmPasswordText = findViewById(R.id.profile_confirm_password_title);

        spinner = findViewById(R.id.profile_city);

        ArrayAdapter adapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1, cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCity = cityArray[i];
                Toast.makeText(ProfileActivity.this, sCity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender = findViewById(R.id.profile_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                sGender = radioButton.getText().toString();
                Toast.makeText(ProfileActivity.this, sGender, Toast.LENGTH_SHORT).show();
            }
        });

        male = findViewById(R.id.profile_male);
        female = findViewById(R.id.profile_female);

        /*male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, male.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, female.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/

        editProfile = findViewById(R.id.profile_edit);
        submit = findViewById(R.id.profile_button);

        submit.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(ProfileActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }  else {
                    String selectQuery = "SELECT * FROM USERS WHERE USERID='" + sp.getString(ConstantSp.USERID,"") + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        String insertQuery = "UPDATE USERS SET NAME='"+username.getText().toString()+"',EMAIL='"+email.getText().toString()+"',PASSWORD='"+password.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"' WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                        db.execSQL(insertQuery);
                        Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();

                        sp.edit().putString(ConstantSp.NAME,username.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstantSp.CITY,sCity).commit();

                        setData(false);

                    } else {
                        Toast.makeText(ProfileActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);
            }
        });

        setData(false);

    }

    private void setData(boolean b) {
        if(b){
            editProfile.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
            confirmPasswordCard.setVisibility(View.VISIBLE);
            confirmPasswordText.setVisibility(View.VISIBLE);
        }
        else{
            editProfile.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
            confirmPassword.setVisibility(View.GONE);
            confirmPasswordCard.setVisibility(View.GONE);
            confirmPasswordText.setVisibility(View.GONE);
        }

        username.setEnabled(b);
        email.setEnabled(b);
        password.setEnabled(b);
        confirmPassword.setEnabled(b);

        male.setEnabled(b);
        female.setEnabled(b);

        spinner.setEnabled(b);

        username.setText(sp.getString(ConstantSp.NAME,""));
        email.setText(sp.getString(ConstantSp.EMAIL,""));
        password.setText(sp.getString(ConstantSp.PASSWORD,""));
        confirmPassword.setText(sp.getString(ConstantSp.PASSWORD,""));

        sGender = sp.getString(ConstantSp.GENDER,"");
        if(sGender.equalsIgnoreCase("Male")){
            male.setChecked(true);
            female.setChecked(false);
        }
        else if(sGender.equalsIgnoreCase("Female")){
            male.setChecked(false);
            female.setChecked(true);
        }
        else{
            male.setChecked(false);
            female.setChecked(false);
        }

        sCity = sp.getString(ConstantSp.CITY,"");
        int iCityPosition = 0;
        for (int i=0;i<cityArray.length;i++){
            if(cityArray[i].equalsIgnoreCase(sCity)){
                iCityPosition = i;
                break;
            }
        }
        spinner.setSelection(iCityPosition);
    }
}