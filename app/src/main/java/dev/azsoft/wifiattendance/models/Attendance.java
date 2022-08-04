package dev.azsoft.wifiattendance.models;

import com.google.gson.GsonBuilder;

public class Attendance {
    String id;

    String uid;

    long startTimestamp;
    long endTimestamp;
    long timestamp;

    int workingMinutes;
    String networkUid;
    String networkName;
    long lastTrackTimestamp;
//    Boolean isPause;
    Boolean isCheckedOut;

    public Attendance() {
    }

    public Attendance(String id, String uid, long startTimestamp, long endTimestamp, long timestamp, int workingMinutes, String networkUid, String networkName, long lastTrackTimestamp,/* Boolean isPause,*/ Boolean isCheckedOut) {
        this.id = id;
        this.uid = uid;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.timestamp = timestamp;
        this.workingMinutes = workingMinutes;
        this.networkUid = networkUid;
        this.networkName = networkName;
        this.lastTrackTimestamp = lastTrackTimestamp;
//        this.isPause = isPause;
        this.isCheckedOut = isCheckedOut;
    }

    public Attendance(String id, String uid, long startTimestamp, long timestamp, int workingMinutes, String networkUid, String networkName, long lastTrackTimestamp, /*boolean isPause, */boolean isCheckedOut) {
        this.id = id;
        this.uid = uid;
        this.startTimestamp = startTimestamp;
        this.timestamp = timestamp;
        this.workingMinutes = workingMinutes;
        this.networkUid = networkUid;
        this.networkName = networkName;
        this.lastTrackTimestamp = lastTrackTimestamp;
//        this.isPause = isPause;
        this.isCheckedOut = isCheckedOut;
    }

    public String getId() {
        return id;
    }

    public Attendance setId(String id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Attendance setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public Attendance setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
        return this;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public Attendance setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Attendance setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getWorkingMinutes() {
        return workingMinutes;
    }

    public Attendance setWorkingMinutes(int workingMinutes) {
        this.workingMinutes = workingMinutes;
        return this;
    }

    public String getNetworkUid() {
        return networkUid;
    }

    public Attendance setNetworkUid(String networkUid) {
        this.networkUid = networkUid;
        return this;
    }

    public String getNetworkName() {
        return networkName;
    }

    public Attendance setNetworkName(String networkName) {
        this.networkName = networkName;
        return this;
    }

    public long getLastTrackTimestamp() {
        return lastTrackTimestamp;
    }

    public Attendance setLastTrackTimestamp(long lastTrackTimestamp) {
        this.lastTrackTimestamp = lastTrackTimestamp;
        return this;
    }

//    public Boolean getPause() {
//        if (isPause == null) return false;
//        return isPause;
//    }
//
//    public Attendance setPause(Boolean pause) {
//        isPause = pause;
//        return this;
//    }

    public Boolean getCheckedOut() {
        if (isCheckedOut == null) return false;
        return isCheckedOut;
    }

    public Attendance setCheckedOut(Boolean checkedOut) {
        isCheckedOut = checkedOut;
        return this;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Attendance.class);
    }

    static public Attendance fromString(String value) {
        return new GsonBuilder().create().fromJson(value, Attendance.class);
    }
}
