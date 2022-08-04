package dev.azsoft.wifiattendance.activities.dashboard;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.Repository;
import dev.azsoft.wifiattendance.activities.SettingsActivity;
import dev.azsoft.wifiattendance.activities.user.LoginActivity;
import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.databinding.FragmentProfileBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.models.Employee;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Repository repository = Repository.getInstance();
    private FragmentProfileBinding binding;
    private final SharedPrefs prefs = SharedPrefs.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.topAppBar.setOnMenuItemClickListener(this::onMenuItemClick);
        binding.btnLogout.setOnClickListener(this);
        String currentUser = prefs.getString(Const.CURRENT_USER);
        if (currentUser.isEmpty()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            setUpUserProfile(Employee.fromString(currentUser));
        }


        return binding.getRoot();
    }


    void setUpUserProfile(Employee employee) {
        Glide.with(this).load(employee.getProfileImage()).centerCrop()
                .placeholder(R.drawable.ic_person_circle).into(binding.profileImage);
        binding.tvUserDesignation.setText(employee.getDesignation());
        binding.tvUserName.setText(employee.getName());
        binding.tvUserEmail.setText(employee.getEmail());
        binding.tvUserPhoneNumber.setText(employee.getPhoneNumber());
        Date d = new Date(employee.getTimestamp());
        DateFormat f = new SimpleDateFormat("yyyy-MMM-dd");
        binding.tvUserJoinedDate.setText(f.format(d));
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (R.id.menu_settings == menuItem.getItemId()) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int clickedId = view.getId();
        if (clickedId == R.id.btn_logout) onUserLogout();
    }

    void onUserLogout() {
        repository.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}