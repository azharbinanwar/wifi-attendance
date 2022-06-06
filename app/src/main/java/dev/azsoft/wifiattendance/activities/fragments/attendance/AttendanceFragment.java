package dev.azsoft.wifiattendance.activities.fragments.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.azsoft.wifiattendance.databinding.FragmentAttendanceBinding;


public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AttendanceViewModel dashboardViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}