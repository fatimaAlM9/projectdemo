package com.example.project
//Haniah Al-Tabaa S00038878
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Login : AppCompatActivity() {

    val url = important_value.ip +"sing_in.php";

    lateinit var sessionManagement : SessionManagement;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        sessionManagement = SessionManagement(this);

// go to create user layout if pressed on dont have an account.

        tv_new_Account.setOnClickListener {

            val intent = Intent(this@Login, newUser::class.java)

            startActivity(intent)

        }

        loginBTN_back.setOnClickListener {

            val intent = Intent(this@Login, MainActivity::class.java)

            startActivity(intent)

        }

// if user is admin, got to admin layout, else do login

        loginBTN_next.setOnClickListener {

            if(loginET_username.text.toString().equals("admin") && loginET_password.text.toString().equals("admin"))

            {

                startActivity(Intent(this, ADMIN_PANEL::class.java))

            }

            else

            {

// we calll the method with the parameters entered by user

                doLogin(loginET_username.text.toString(),loginET_password.text.toString())

            }

        }

    }

// StringRequest. Specify a URL and receive a raw string in response

    fun doLogin(username: String, userPass: String){

        val stringRequest = object : StringRequest(

            Request.Method.POST, url,

            Response.Listener<String> { response ->

                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()

                try {

                    val obj = JSONObject(response)

                    var id = obj.getString("id")

                    sessionManagement.setid(id)

                    startActivity(Intent(this, gameSelection::class.java))

                    finish()

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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)

    }
}
