package dev.azsoft.wifiattendance.activities.fragments.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dev.azsoft.wifiattendance.adapter.AttendanceAdapter;
import dev.azsoft.wifiattendance.databinding.FragmentAttendanceBinding;
import dev.azsoft.wifiattendance.models.Attendance;


public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        init();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void init() {
        binding.rlAttendance.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Attendance> items = new ArrayList<>();
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        items.add(new Attendance());
        AttendanceAdapter attendanceAdapter = new AttendanceAdapter(items);
        binding.rlAttendance.setAdapter(attendanceAdapter);
    }
}