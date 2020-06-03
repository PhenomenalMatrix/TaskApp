package com.example.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskapp.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName;
    private ImageView imageViewAvatar;
    private Uri imgUri;
    private ProgressBar progressBar;
    private String avatarUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

//        getData();
        getData2();
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 228);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 228 && resultCode == RESULT_OK && data.getData() != null) {
            imgUri = data.getData();
            Glide.with(this).load(imgUri).circleCrop().into(imageViewAvatar);
            upload(imgUri);

        }
    }


    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            editName.setText(user.getName());
                            showImage(user.getAvatar());
                        }
                    }
                });
    }

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(imageViewAvatar);
    }

    private void getData() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            editName.setText(user.getName());
                        }
                    }
                });
    }

    public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getUid();
        String name = editName.getText().toString().trim();
        User user = new User(name, 18, avatarUrl);
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void upload(Uri imgUri) {
        progressBar.setVisibility(View.VISIBLE);
        String uid = FirebaseAuth.getInstance().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child(uid + ".jpg");
        UploadTask uploadTas = reference.putFile(imgUri);
        uploadTas.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    Log.e("profile", "downloadUrl: "+ downloadUrl);
                    avatarUrl = downloadUrl.toString();
                    updateAvatarInfo(downloadUrl);
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAvatarInfo(Uri downloadUrl) {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid)
                .update("avatar",downloadUrl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
