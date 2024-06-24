package com.example.proyectodam1.firebasestorage

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class SubirStorage {
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    fun uploadImage(context: Context, imageUri: Uri, folderName: String, onComplete: (Boolean, String?) -> Unit) {
        val fileName = getFileName(context, imageUri)
        val imageRef = storageReference.child("$folderName/$fileName")

        val uploadTask: UploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onComplete(true, uri.toString())
            }.addOnFailureListener {
                onComplete(false, null)
            }
        }.addOnFailureListener {
            onComplete(false, null)
        }
    }

    fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (columnIndex != -1) {
                        result = it.getString(columnIndex)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result ?: "unknown"
    }
}