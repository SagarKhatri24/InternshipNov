package internship.nov;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class DashboardActivity extends AppCompatActivity {

    TextView email;
    SharedPreferences sp;

    Button profile,deleteProfile,logout;

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

        email = findViewById(R.id.dashboard_email);
        email.setText(sp.getString(ConstantSp.NAME,""));

        profile = findViewById(R.id.dashboard_profile);
        deleteProfile = findViewById(R.id.dashboard_delete_profile);
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