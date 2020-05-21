package com.example.project
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.example.project.important_value.ip
import kotlinx.android.synthetic.main.activity_new_user.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

//import javax.swing.UIManager.put

class newUser : AppCompatActivity() {

    val url = ip+"sign_up.php";

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_user)

        var nUserBTN_back = findViewById(R.id.nUserBTN_back) as Button

        var nUserET_name = findViewById(R.id.nUserET_name) as EditText

        var nUserET_password = findViewById(R.id.nUserET_password) as EditText

        nUserBTN_next.setOnClickListener {

            var name = nUserET_name.text.toString();

            var password = nUserET_password.text.toString();

// request_new(name+"",password+"");

            doLogin(name,password);

        }

        nUserBTN_back.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

        }

    }

    fun doLogin(username: String, userPass: String){

        val stringRequest = object : StringRequest(Request.Method.POST, url,

            Response.Listener<String> { response ->

                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()

                try {

                    val obj = JSONObject(response)

// startActivity(Intent(this, TabbedHome::class.java))

// Initialize a new instance of

                    val builder = AlertDialog.Builder(this@newUser)

// Set the alert dialog title

                    builder.setTitle("DONE")

// Display a message on alert dialog

                    builder.setMessage("NOW YOU ARE ABLE TO LOGIN")

// Set a positive button and its click listener on alert dialog

                    builder.setPositiveButton("YES"){dialog, which ->

// Do something when user press the positive button

// Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@newUser, Login::class.java)

                        finish()

                        startActivity(intent)

                    }

// Display a negative button on alert dialog

                    builder.setNegativeButton("No"){dialog,which ->

                        finish();

// Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()

                    }

// Finally, make the alert dialog using builder

                    val dialog: AlertDialog = builder.create()

// Display the alert dialog on app interface

                    dialog.show()

// Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()

                } catch (e: JSONException) {

                    e.printStackTrace()

                    Toast.makeText(applicationContext, "please try again"+ response, Toast.LENGTH_LONG).show()

                }

            },

            object : Response.ErrorListener {

                override fun onErrorResponse(volleyError: VolleyError) {

                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()

                }

            }) {

            @Throws(AuthFailureError::class)

            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()

                params.put("name", username)

                params.put("password", userPass)

                return params

            }

        }

        VolleySingleton.getInstance(this)?.addToRequestQueue(stringRequest)

    }

}
