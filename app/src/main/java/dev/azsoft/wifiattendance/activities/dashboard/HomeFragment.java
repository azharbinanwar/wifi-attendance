package dev.azsoft.wifiattendance.activities.dashboard;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.Repository;
import dev.azsoft.wifiattendance.databasehelper.DatabaseHelper;
import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.databinding.FragmentHomeBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.global.PermissionStatus;
import dev.azsoft.wifiattendance.interfaces.OnComplete;
import dev.azsoft.wifiattendance.models.Attendance;
import dev.azsoft.wifiattendance.models.Employee;
import dev.azsoft.wifiattendance.models.PrivateNetwork;
import dev.azsoft.wifiattendance.services.TimeRecordingService;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private SharedPrefs prefs = SharedPrefs.getInstance();
    private final Repository repository = Repository.getInstance();
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.topAppBar.setOnMenuItemClickListener(this::onMenuItemClick);
        setUpCurrentUserTile();
        binding.btnCheckIn.setOnClickListener(view -> checkPermissions());
//        binding.btnPausePlay.setOnClickListener(view -> onPausePlay());
        binding.btnCheckOut.setOnClickListener(view -> onCheckOut());
        binding.btnOpenSettings.setOnClickListener(view -> showSettingsDialog());
        databaseHelper = new DatabaseHelper(getContext());
        onTimerTrackingListener();
        return binding.getRoot();
    }
//
//    void onPausePlay() {
//        String at = prefs.getString(Const.ATTENDANCE);
//        if (at.isEmpty()) {
//            onStartTimeRecordingWithServices();
//        } else {
//            if (isMyServiceRunning(TimeRecordingService.class)) {
//                Intent intent = new Intent(getActivity(), TimeRecordingService.class);
//                getActivity().stopService(intent);
//                Attendance attendance = Attendance.fromString(at);
//                attendance.setPause(true);
//                repository.onUpdateDocument(attendance.getId() );
//            }
//        }
//
//    }

    void onCheckOut() {
        if (isMyServiceRunning(TimeRecordingService.class)) {
            Intent intent = new Intent(getActivity(), TimeRecordingService.class);
            getActivity().stopService(intent);
        }
        Date date = new Date();
        String attendanceId = repository.getUid().substring(0, repository.getUid().length() / 2) + date.getDay() + date.getMonth() + date.getYear();
        repository.fetchADocument(attendanceId, Const.ATTENDANCES, new OnComplete() {
            @Override
            public void onResult(Object ob) {
                if (ob instanceof DocumentSnapshot) {
                    Attendance attendance = ((DocumentSnapshot) ob).toObject(Attendance.class);
                    attendance.setCheckedOut(true);
                    repository.onUpdateDocument(attendanceId, attendance, Const.ATTENDANCES, new OnComplete() {
                        @Override
                        public void onResult(Object ob) {
                            Toast.makeText(getActivity(), "Your timer is stopped", Toast.LENGTH_SHORT).show();
                            binding.btnCheckOut.setVisibility(View.GONE);
                            binding.btnCheckIn.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), Const.SOME_THING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkPermissions() {
        repository.onPermissionHandler(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, ob -> {
                    if (ob == PermissionStatus.permissionGranted) {
                        checkWifiConnectionStatus();
                    } else if (ob == PermissionStatus.permissionDenied) {
                        Toast.makeText(getActivity(), R.string.you_ve_denied_permission, Toast.LENGTH_SHORT).show();
                    } else if (ob == PermissionStatus.permissionDeniedPermanently) {
                        Toast.makeText(getActivity(), R.string.permanently_denied_permission_message, Toast.LENGTH_LONG).show();
                        binding.btnCheckIn.setVisibility(View.GONE);
                        binding.btnCheckOut.setVisibility(View.GONE);
//                        binding.btnPausePlay.setVisibility(View.GONE);
                        binding.btnOpenSettings.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), Const.SOME_THING_WENT_WRONG + " " + Const.TRY_CONTACT_ADMIN, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void checkWifiConnectionStatus() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiManager.isWifiEnabled()) {
            System.out.println("AddWifiActivity.showWifiDetails: " + wifiManager.isWifiEnabled());
            String bssid = wifiInfo.getBSSID();
            PrivateNetwork privateNetwork = databaseHelper.getARecord(bssid);
            if (privateNetwork != null) {
                onStartTimeRecordingWithServices();
                Toast.makeText(getActivity(), "Your time has been started!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "You're not connected with private network!", Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.mainCard.setVisibility(View.GONE);
            binding.placeHolder.getRoot().setVisibility(View.VISIBLE);
            binding.placeHolder.placeHolderIcon.setImageResource(R.drawable.ic_wifi);
            binding.placeHolder.placeHolderTitle.setText(R.string.you_re_not_connected_to_any_wifi);
        }
    }

    void onStartTimeRecordingWithServices() {
        Intent intent = new Intent(getActivity(), TimeRecordingService.class);
        ContextCompat.startForegroundService(getActivity(), intent);

    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.need_permissions);
        builder.setMessage(R.string.open_settings_for_permission_message);
        builder.setPositiveButton(R.string.open_settings, (dialog, which) -> {
            dialog.cancel();

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    void setUpCurrentUserTile() {
        Employee currentUser = Employee.fromString(prefs.getString(Const.CURRENT_USER));
        Glide.with(this).load(currentUser.getProfileImage()).centerCrop()
                .placeholder(R.drawable.ic_person_circle).into(binding.profileImage);
        binding.tvUserName.setText(currentUser.getName());
        binding.tvUserDesignation.setText(currentUser.getDesignation());

    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        if (R.id.menu_notification == menuItem.getItemId()) {
            Toast.makeText(getActivity(), "HomeFragment.onMenuItemClick TRY", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    void onTimerTrackingListener() {
        Date date = new Date();
        String attendanceId = repository.getUid().substring(0, repository.getUid().length() / 2) + date.getDay() + date.getMonth() + date.getYear();
        repository.onListenChangeDocument(attendanceId, Const.ATTENDANCES, ob -> {
            if (ob instanceof DocumentSnapshot) {
                if (((DocumentSnapshot) ob).exists()) {
                    binding.trackingTile.setVisibility(View.VISIBLE);
                    Attendance attendance = ((DocumentSnapshot) ob).toObject(Attendance.class);
                    binding.trackingTile.setText("Time: " + attendance.getWorkingMinutes() + " minutes");
//                    if (attendance.getPause() != null || attendance.getPause()) {
////                        binding.btnPausePlay.setText(getString(R.string.start));
//                    } else {
////                        binding.btnPausePlay.setText(getString(R.string.pause));
//                    }
                    if (attendance.getCheckedOut()) {
                        binding.btnCheckIn.setVisibility(View.VISIBLE);
                        binding.btnCheckOut.setVisibility(View.GONE);
                        if (!isMyServiceRunning(TimeRecordingService.class)) {
                            onStartTimeRecordingWithServices();
                        }
//                        binding.btnPausePlay.setVisibility(View.GONE);
                    } else {
                        binding.btnCheckIn.setVisibility(View.GONE);
                        binding.btnCheckOut.setVisibility(View.VISIBLE);
//                        binding.btnPausePlay.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.btnCheckIn.setVisibility(View.VISIBLE);
                }
            } else if (ob instanceof Exception) {
                Toast.makeText(getActivity(), ((Exception) ob).getMessage(), Toast.LENGTH_SHORT).show();
                binding.trackingTile.setVisibility(View.GONE);
            } else {
                Toast.makeText(getActivity(), Const.SOME_THING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                binding.trackingTile.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(getActivity().getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}