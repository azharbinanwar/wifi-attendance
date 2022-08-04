package dev.azsoft.wifiattendance.activities;

import static dev.azsoft.wifiattendance.items.ForgotPasswordItem.TAG;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.Repository;
import dev.azsoft.wifiattendance.activities.dashboard.DashboardActivity;
import dev.azsoft.wifiattendance.activities.user.LoginActivity;
import dev.azsoft.wifiattendance.databasehelper.DatabaseHelper;
import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.databinding.ActivitySplashBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.interfaces.OnComplete;
import dev.azsoft.wifiattendance.models.Employee;
import dev.azsoft.wifiattendance.models.PrivateNetwork;


public class SplashActivity extends AppCompatActivity {

    private final Repository repository = Repository.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    // TODO: not going to use this variable because it module is not complete yet

    private SharedPrefs sharedPrefs;
    private Boolean isFirstLaunch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivitySplashBinding.inflate(getLayoutInflater()).getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(this);
        final FirebaseUser user = auth.getCurrentUser();

        repository.fetchAllDocuments(Const.PRIVATE_NETWORKS, ob -> {
            if (ob instanceof QuerySnapshot) {
                if (((QuerySnapshot) ob).isEmpty()) {
                    Toast.makeText(SplashActivity.this, getString(R.string.try_to_contact_admin), Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.deleteAllRecords(Const.NETWORKS_TABLE_NAME);
                    for (int i = 0; i < ((QuerySnapshot) ob).size(); i++) {
                        PrivateNetwork pn = ((QuerySnapshot) ob).getDocuments().get(i).toObject(PrivateNetwork.class);
                        if (pn != null) {
                            databaseHelper.updateAllRecords(PrivateNetwork.toContentValue(pn));
                        }
                    }
                    if (user == null) {
                        navigateTo(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        repository.fetchUserDetails(user.getUid(), ob1 -> fetchUserDetails(ob1));
                    }
                }
            } else if (ob instanceof Exception) {
                Toast.makeText(SplashActivity.this, ((Exception) ob).getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "onResult: General error");
                Toast.makeText(SplashActivity.this, Const.SOME_THING_WENT_WRONG + " " + getString(R.string.try_again_later), Toast.LENGTH_SHORT).show();
            }
        });


    }


    void init() {
        SharedPrefs.getInstance().Initialize(getApplicationContext());
        sharedPrefs = SharedPrefs.getInstance();
        isFirstLaunch = sharedPrefs.getBoolean(Const.FIRST_LAUNCH, false);

    }


    private void navigateTo(Intent intent) {
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1000);
    }

    private void fetchUserDetails(Object ob) {
        if (ob instanceof Employee) {
            String val = ob.toString();
            sharedPrefs.setString(Const.CURRENT_USER, val);
            navigateTo(new Intent(this, DashboardActivity.class));
            finish();
        } else {
            navigateTo(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }


}