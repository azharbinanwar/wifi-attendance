package dev.azsoft.wifiattendance.activities.fragments.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StatsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}