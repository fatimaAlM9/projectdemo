package com.example.project
//fatima Almufti S00038508
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_admin__add__images.*
import org.json.JSONObject
import java.io.File


class ADMIN_ADD_IMAGES : AppCompatActivity() {

    private var animal_answer = true;

//    lateinit var aa_name : TextView;
//    lateinit var aa_question : TextView;
//    lateinit var aa_answer : TextView;

    val url = important_value.ip +"add_animal.php";


    var file_path : String = "";

    val context : Context = this;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin__add__images)

            //aa_img click listener
        aa_img.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
            //aa_add_animal button click listener to add image to database
        aa_add_animal.setOnClickListener {
                var name = aa_name.text.toString();
                var question = aa_question.text.toString();
                var answer = aa_answer.text.toString();

                // if field name, question, answer are empty show alert mssg
            if (name.length == 0 || question.length == 0 || answer.length ==0)
            {
                MY_ALERT.SET_MY_ALERT(context,"Note " , "Fill All detilas for animal ","Ok")
            }
            else UPLOAD_FILE(name,question,answer)




        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;

    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            aa_img.setImageURI(data?.data)
            file_path = ImageFilePath.getPath(context, data!!.getData());
        }
    }


    fun UPLOAD_FILE(
        name: String?,
        question: String?,
        answer: String?
    ) {
        if (!Connectivity.isConnected(context)) {
            MY_ALERT.SET_MY_ALERT_TO_CLOSE(
                context,
                "Note",
                "You have not connected internet",
                "Back"
            )
        } else {
            if (file_path.length == 0) {
                MY_ALERT.SET_MY_ALERT(context, "NOTE", "Set your shop visiting card first", "OK")
            } else {
                val dialog: AlertDialog = MY_ALERT.progress_loading_show(context)!!
                AndroidNetworking.upload(url) // .addMultipartFile("file_name", new File(path))
                    .addMultipartFile("file", File(file_path))
                    .addMultipartParameter("name", name)
                    .addMultipartParameter("animal_question", question)
                    .addMultipartParameter("answer", answer)
                    .addMultipartParameter("ins_file", "s1")
                    .setTag("uploadTest")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener { bytesUploaded, totalBytes ->
                        // do anything with progress
                        // Toast.makeText(Submission.this, ""+ bytesUploaded + " " + totalBytes, Toast.LENGTH_SHORT).show();
                        val up_load_result = bytesUploaded / totalBytes * 100
                        val final_result = up_load_result.toInt()
                        dialog.show()
                    }
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) { // do anything with response
                            dialog.dismiss()
                            var result = response.getString("return");
                            if (result.equals("1")) {
                                MY_ALERT.SET_MY_ALERT_TO_CLOSE(
                                    context,
                                    "Successful",
                                    "New Animal now add to system",
                                    "OK"
                                )
                            }
                            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(error: ANError) {
                            dialog.dismiss()
                            Log.d("LABLA", "onError: $error.")
                            Log.d("LABLA", "onError: " + error.response)
                            Log.d("LABLA", "onError: " + error.errorDetail)
//                            Log.d("LABLA", "onError: " + error.errorDetail)
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

}
