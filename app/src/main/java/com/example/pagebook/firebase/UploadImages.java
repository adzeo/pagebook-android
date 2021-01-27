package com.example.pagebook.firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class UploadImages {

//    public static void uploadFile(Uri filePath) {
//        if (filePath != null) {
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference storageRef = storage.getReference();
//            StorageReference riversRef = storageRef.child("images/" + new Date().toString() + ".jpg");
//            riversRef.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            //if the upload is successfull
//                            //hiding the progress dialog
//
//                            //and displaying a success toast
//                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            //if the upload is not successfull
//                            //hiding the progress dialog
//                            progressDialog.dismiss();
//
//                            //and displaying error message
//                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            //calculating progress percentage
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                            //displaying percentage in progress dialog
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
//                        }
//                    });
//        }
//        //if there is not any file
//        else {
//            //you can display an error toast
//        }
//    }
}
