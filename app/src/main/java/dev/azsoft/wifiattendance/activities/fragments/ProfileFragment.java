package dev.azsoft.wifiattendance.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.activities.SettingsActivity;
import dev.azsoft.wifiattendance.databinding.FragmentHomeBinding;
import dev.azsoft.wifiattendance.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.topAppBar.setOnMenuItemClickListener(this::onMenuItemClick);


        return binding.getRoot();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (R.id.menu_settings == menuItem.getItemId()) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return false;
    }
}