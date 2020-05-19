package com.example.project

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

        tv_new_Account.setOnClickListener {
            val intent = Intent(this@Login, newUser::class.java)
            startActivity(intent)
        }

        loginBTN_next.setOnClickListener {
            if(loginET_username.text.toString().equals("admin") && loginET_password.text.toString().equals("admin"))
            {
                startActivity(Intent(this, ADMIN_PANEL::class.java))
            }
            else
            {
                doLogin(loginET_username.text.toString(),loginET_password.text.toString())

            }
        }

    }

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
//                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
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
