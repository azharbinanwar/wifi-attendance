package dev.azsoft.wifiattendance.activities.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private View view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.topAppBar.setOnMenuItemClickListener(this::onMenuItemClick);
        binding.btn.setOnClickListener(this::onClick);
        view = binding.getRoot();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicking() {
        Toast.makeText(getActivity(), "WIthIN", Toast.LENGTH_SHORT).show();

    }


    public boolean onMenuItemClick(MenuItem menuItem) {
        System.out.println("HomeFragment.onMenuItemClick");
        if (R.id.menu_notification == menuItem.getItemId()) {
            Toast.makeText(getActivity(), "HomeFragment.onMenuItemClick TRY", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void onClick(View view) {
        onClicking();
    }
}