package com.example.proyekandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class tambahJurnal : AppCompatActivity() {

    var counter = 0
    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()

    private lateinit var _etJudul: EditText
    private lateinit var _etDeskripsi: EditText
    private lateinit var _etTanggal: EditText

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_jurnal)

        imageView = findViewById(R.id.imageView)
        val selectImageButton: Button = findViewById(R.id.selectImageButton)
        selectImageButton.setOnClickListener {
            openImageChooser()
        }

        _etJudul = findViewById(R.id.etJudul)
        _etDeskripsi = findViewById(R.id.etDeskripsi)
        _etTanggal = findViewById(R.id.etTanggal)

        val btnSimpan = findViewById<ImageButton>(R.id.btnSimpan)
        btnSimpan.setOnClickListener {
            if (_etJudul.text.toString().isNotEmpty() && _etDeskripsi.text.toString().isNotEmpty() && _etTanggal.text.toString().isNotEmpty() && selectedImageUri != null) {
                uploadImageAndSaveData(selectedImageUri!!)
                counter ++
            } else {
                Log.d("Simpan", "Semua field harus diisi dan gambar harus dipilih")
            }
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            if (imageUri != null) {
                selectedImageUri = imageUri
                imageView.setImageURI(imageUri)
            }
        }
    }

    private fun uploadImageAndSaveData(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$imageName")

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val downloadUrl = downloadUri.toString()
                tambahData(db, _etJudul.text.toString(), _etDeskripsi.text.toString(), _etTanggal.text.toString(), downloadUrl)
            } else {
                Log.e("Firebase", "Upload failed", task.exception)
            }
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Upload failed", exception)
        }
    }

    private fun tambahData(db: FirebaseFirestore, Judul: String, Deskripsi: String, Tanggal: String, Gambar: String) {
        val dataBaru = jurnalTravel(Judul, Deskripsi, Tanggal, Gambar, "false")
        db.collection("listJurnal")
            .document(counter.toString())
            .set(dataBaru)
            .addOnSuccessListener { documentReference ->
                Log.d("Firebase", "Data berhasil disimpan")
                _etJudul.setText("")
                _etDeskripsi.setText("")
                _etTanggal.setText("")
                finish()
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error menambahkan dokumen", e)
            }
    }
}
