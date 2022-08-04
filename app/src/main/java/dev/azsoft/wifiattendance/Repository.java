package dev.azsoft.wifiattendance;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.global.PermissionStatus;
import dev.azsoft.wifiattendance.interfaces.OnComplete;
import dev.azsoft.wifiattendance.models.Employee;

public class Repository {
    private static Repository repository;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String url = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + Const.API_KEY;


    public static Repository getInstance() {
        if (repository == null)
            repository = new Repository();

        return repository;
    }

    public void onLogIn(Boolean fetchUserDetails, String email, String password, OnComplete onComplete) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AuthResult authResult = task.getResult();
                if (fetchUserDetails) {
                    fetchUserDetails(authResult.getUser().getUid(), onComplete);
                } else {
                    onComplete.onResult(task.getResult());
                }
            }
        }).addOnFailureListener(e -> onComplete.onResult(e));
    }

    public void fetchUserDetails(String uid, OnComplete onComplete) {
        firestore.collection(Const.EMPLOYEES).document(uid).get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();
            Employee user = snapshot.toObject(Employee.class);
            onComplete.onResult(user);
        }).addOnFailureListener(e -> onComplete.onResult(e));
    }

    public void fetchADocument(String id, String collection, OnComplete onComplete) {
        firestore.collection(collection).document(id).get().addOnCompleteListener(task -> {
            ;
            onComplete.onResult(task.getResult());
        }).addOnFailureListener(e -> onComplete.onResult(e));
    }

    public void onCreateDocument(String uid, Object object, String collection, OnComplete onComplete) {
        firestore.collection(collection).document(uid).set(object).addOnCompleteListener(
                task -> onComplete.onResult(object)
        ).addOnFailureListener(onComplete::onResult);
    }

    public void onUpdateDocument(String uid, Object object, String collection, OnComplete onComplete) {
        firestore.collection(collection).document(uid).set(object, SetOptions.merge()).addOnCompleteListener(
                task -> onComplete.onResult(object)
        ).addOnFailureListener(onComplete::onResult);
    }

    public void fetchAllDocuments(String collection, OnComplete onComplete) {
        firestore.collection(collection).get().addOnCompleteListener(
                task -> onComplete.onResult(task.getResult())
        ).addOnFailureListener(onComplete::onResult);
    }


    public void signOut() {
        firebaseAuth.signOut();
        SharedPrefs.getInstance().clear();
    }

    public void onListenChangeDocument(String id, String collection, OnComplete onComplete) {
        firestore.collection(collection).document(id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            onComplete.onResult(value);
                        } else {
                            onComplete.onResult(error);
                        }
                    }

                });
    }


    public void onPermissionHandler(Context context, String permission, OnComplete onComplete) {
        Dexter
                .withContext(context)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                                  @Override
                                  public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                      onComplete.onResult(PermissionStatus.permissionGranted);
                                  }

                                  @Override
                                  public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                      if (permissionDeniedResponse.isPermanentlyDenied()) {
                                          onComplete.onResult(PermissionStatus.permissionDeniedPermanently);
                                      } else {
                                          onComplete.onResult(PermissionStatus.permissionDenied);
                                      }
                                  }

                                  @Override
                                  public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                      permissionToken.continuePermissionRequest();
                                  }


                              }
                ).check();
    }


    public String getUid() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    }

    public String getEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }



}

