package com.example.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.dublin.weather_kotlin.MODEL.ANIMAL_MODEL
import com.dublin.weather_kotlin.RV.RV_ANIMALS
import kotlinx.android.synthetic.main.activity_admin__animals.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
//fatima Almufti S00038508
class ADMIN_ANIMALS : AppCompatActivity() {


    lateinit var list : ArrayList<ANIMAL_MODEL>

    val url = important_value.ip +"get_animal_for_admin.php";




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin__animals)

        list = ArrayList()
        
        get_animal()
//        list.add(ANIMAL_MODEL("1","DOG","0","0","10/10/10"))
//        list.add(ANIMAL_MODEL("1","CAT","0","1","10/10/10"))
//        list.add(ANIMAL_MODEL("1","COW","0","0","10/10/10"))
//        list.add(ANIMAL_MODEL("1","Snake","0","0","10/10/10"))
//        GET_WARN()


    }


    

    fun get_animal(){

        val RV: RecyclerView = findViewById(R.id.RV_ADMIN_ANIMAL)
        RV.setLayoutManager(LinearLayoutManager(this))
        val rv = RV_ANIMALS(this,list)
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                try {
                    val obj = JSONArray(response)
                    for (i in 0 until obj.length())
                    {
                        var obj_json = obj.getJSONObject(i)

                        var am = ANIMAL_MODEL(
                            obj_json.getString("0"),
                            obj_json.getString("1"),
                            obj_json.getString("2"),
                            obj_json.getString("3"),
                            obj_json.getString("4"),
                            obj_json.getString("5"),
                            obj_json.getString("7")
                        )
                        
                        list.add(am)
//                        Log.e("NUMBER", number[i].toString())
                    }
                    RV.setAdapter(rv)


                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
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
                params.put("GETTER", "")
//                params.put("password", userPass)
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
