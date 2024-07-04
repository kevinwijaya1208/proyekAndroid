package com.example.proyekandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.util.UUID

class tambahJurnal : AppCompatActivity() {

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
            Log.d("gambar setelah dipilih", "${selectedImageUri}")
        }

        _etJudul = findViewById(R.id.etJudul)
        _etDeskripsi = findViewById(R.id.etDeskripsi)
        _etTanggal = findViewById(R.id.etTanggal)

        val btnEdit = findViewById<Button>(R.id.btnEdit)

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        btnSimpan.setOnClickListener {
            if (_etJudul.text.toString().isNotEmpty() && _etDeskripsi.text.toString().isNotEmpty() && _etTanggal.text.toString().isNotEmpty() && selectedImageUri != null) {
                uploadImageAndSaveData(selectedImageUri!!)
            } else {
                Log.d("Simpan", "Semua field harus diisi dan gambar harus dipilih")
            }
        }

        var iAddEdit : Int = 0
        var iJudul : String = ""
        var iDeskripsi : String = ""
        var iTanggal : String = ""
        var iGambar : String = ""
        var iFavorite : String =""

        iAddEdit = intent.getIntExtra("addEdit", 0)
        iJudul = intent.getStringExtra("judul").toString()
        iDeskripsi = intent.getStringExtra("deskripsi").toString()
        iTanggal = intent.getStringExtra("tanggal").toString()
        iGambar = intent.getStringExtra("gambar").toString()
        iFavorite = intent.getStringExtra("favorite").toString()

        Log.d("gambar", iGambar)

        if (iAddEdit == 0) {
            btnSimpan.visibility = View.VISIBLE
            btnEdit.visibility = View.GONE
        } else {
            btnSimpan.visibility = View.GONE
            btnEdit.visibility = View.VISIBLE

            _etJudul.setText(iJudul)
            _etDeskripsi.setText(iDeskripsi)
            _etTanggal.setText(iTanggal)
            Picasso.get().load(iGambar).into(imageView)
            selectedImageUri = Uri.parse(iGambar)
            val imageUri = selectedImageUri

//            btnEdit.setOnClickListener {
//                if (imageUri != null) {
//                    Log.d("imageUri", imageUri.toString())
//                    Log.d("selected image", "${selectedImageUri.toString()}")
//                    editData(imageUri, db, iJudul, _etJudul.text.toString(), _etDeskripsi.text.toString(), _etTanggal.text.toString(), selectedImageUri.toString(), iFavorite)
//                }
//            }
            btnEdit.setOnClickListener {
                if (selectedImageUri != null) {
                    Log.d("selected image", "${selectedImageUri.toString()}")
                    editData(selectedImageUri, db, iJudul, _etJudul.text.toString(), _etDeskripsi.text.toString(), _etTanggal.text.toString(), iGambar, iFavorite)
                }
//                else {
//                    Log.d("3", "4")
//                    editData(null, db, iJudul, _etJudul.text.toString(), _etDeskripsi.text.toString(), _etTanggal.text.toString(), iGambar, iFavorite)
//                }
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
                Log.d("gambar setelah diganti diedit", "$imageUri")
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
            .document(dataBaru.judul)
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

    private fun tambahData2(db: FirebaseFirestore, Judul: String, Deskripsi: String, Tanggal: String, Gambar: String, Favorite: String) {
        val dataBaru = jurnalTravel(Judul, Deskripsi, Tanggal, Gambar, Favorite)
        db.collection("listJurnal")
            .document(dataBaru.judul)
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

    private fun editData(
        imageUri: Uri?,
        db: FirebaseFirestore,
        originalJudul: String,
        judul: String,
        deskripsi: String,
        tanggal: String,
        gambar: String,
        favorite: String
    ) {
        db.collection("listJurnal")
            .document(originalJudul)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase", "Document deleted successfully")
                if (imageUri != null) {
                    if (imageUri.scheme == "content") {
                        val storageRef = FirebaseStorage.getInstance().reference
                        val imageName = UUID.randomUUID().toString()
                        val imageRef = storageRef.child("images/$imageName")

                        val uploadTask = if (imageUri != null) {
                            imageRef.putFile(imageUri)
                        } else {
                            imageRef.putFile(Uri.parse(gambar))
                        }

                        uploadTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let { throw it }
                            }
                            imageRef.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUri = task.result
                                val downloadUrl = downloadUri.toString()
                                tambahData2(
                                    db,
                                    judul,
                                    deskripsi,
                                    tanggal,
                                    downloadUrl,
                                    favorite
                                )
                            } else {
                                Log.e("Firebase", "Upload failed", task.exception)
                            }
                        }.addOnFailureListener { exception ->
                            Log.e("Firebase", "Upload failed", exception)
                        }
                    } else {
                        tambahData2(
                        db,
                        _etJudul.text.toString(),
                        _etDeskripsi.text.toString(),
                        _etTanggal.text.toString(),
                        imageUri.toString(),
                        favorite
                    )
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error deleting document", e)
            }
    }



//    private fun editData(
//        imageUri: Uri?,
//        db: FirebaseFirestore,
//        originalJudul: String,
//        judul: String,
//        deskripsi: String,
//        tanggal: String,
//        gambar: String,
//        favorite: String
//    ) {
//        db.collection("listJurnal")
//            .document(originalJudul)
//            .delete()
//            .addOnSuccessListener {
//                Log.d("Firebase", "Document deleted successfully")
//                if (imageUri != null) {
//                    Log.d("Gambar tidak kosong", "${imageUri}")
//                    tambahData2(
//                        db,
//                        _etJudul.text.toString(),
//                        _etDeskripsi.text.toString(),
//                        _etTanggal.text.toString(),
//                        imageUri.toString(),
//                        favorite
//                    )
//                } else {
//                val storageRef = FirebaseStorage.getInstance().reference
//                val imageName = UUID.randomUUID().toString()
//                val imageRef = storageRef.child("images/$imageName")
//
//                val uploadTask = imageRef.putFile(selectedImageUri!!)
//                uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        task.exception?.let { throw it }
//                    }
//                    imageRef.downloadUrl
//                }.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val downloadUri = task.result
//                        val downloadUrl = downloadUri.toString()
//                        Log.d("Gambar tidak baru", "${downloadUrl}")
//                        tambahData2(
//                            db,
//                            _etJudul.text.toString(),
//                            _etDeskripsi.text.toString(),
//                            _etTanggal.text.toString(),
//                            downloadUrl,
//                            favorite
//                        )
//                    } else {
//                        Log.e("Firebase", "Upload failed", task.exception)
//                    }
//                }.addOnFailureListener { exception ->
//                    Log.e("Firebase", "Upload failed", exception)
//                }
//            }
//            }
//            .addOnFailureListener { e ->
//                Log.w("Firebase", "Error deleting document", e)
//            }
//    }
}
