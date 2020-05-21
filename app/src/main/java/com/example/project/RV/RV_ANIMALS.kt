package com.dublin.weather_kotlin.RV
//fatima Almufti S00038508
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.dublin.weather_kotlin.MODEL.ANIMAL_MODEL

import com.example.project.R
import com.example.project.VolleySingleton
import com.example.project.important_value
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class RV_ANIMALS(var c: Context,var list: ArrayList<ANIMAL_MODEL>) : RecyclerView.Adapter<RV_ANIMALS.RVW>()
{
//    var list: ArrayList<ANIMAL_MODEL>
val url = important_value.ip +"filess/";
val url1 = important_value.ip +"active_deactive_animal.php";

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVW {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cv_animal, parent, false)
        return RVW(v)
    }

    override fun onBindViewHolder(h: RVW, position: Int) {
        h.name.text ="Animal Name: "+ list[position].name
        h.question.text = "Question: "+list[position].question
        h.asnwer.text = "Answer: "+list[position].answer
        h.name.text = list[position].name

        Picasso.get().load(url+list[position].img).resize(200,200).into(h.img)

        h.btn_del.setOnClickListener {
            doLogin(list[position].id,list[position].status,position)
        }



//        h.level.text = list[position].level
//        h.type.text = list[position].type

        // By click on item it move into the Add new warning page
        h.ll.setOnClickListener {
//            c.startActivity(
//                Intent(c, ADD_MY_WARNING::class.java)
//                    .putExtra("id", list[position].id)
//                    .putExtra("a", list[position].area)
//                    .putExtra("t", list[position].type)
//                    .putExtra("l", list[position].level)
//                    .putExtra("del", "yes")
//            )
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RVW(itemView: View) : ViewHolder(itemView) {
        var img: ImageView
        var name: TextView
        var question: TextView
        var asnwer: TextView
        var btn_del: Button
//        var img: ImageView

//        var type: TextView
        var ll: LinearLayout

        init {
            img = itemView.findViewById(R.id.cv_al_img)
            name = itemView.findViewById(R.id.cv_al_name)
            question = itemView.findViewById(R.id.cv_al_question)
            asnwer = itemView.findViewById(R.id.cv_al_asnwer)
            btn_del = itemView.findViewById(R.id.cv_ali_del_animal)
            ll = itemView.findViewById(R.id.cv_al_ll)
        }
    }

    //delete the animal from the list
    fun doLogin(id: String,
                status: String,
                postion: Int
    ){
        val stringRequest = object : StringRequest(
            Request.Method.POST, url1,
            Response.Listener<String> { response ->
                Toast.makeText(c, response.toString(), Toast.LENGTH_LONG).show()
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("return").equals("1"))
                    {
                        list.removeAt(postion)
                        notifyDataSetChanged()
                    }

//                    Toast.makeText(c, response.toString(), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(c, "please try again"+ response, Toast.LENGTH_LONG).show()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(c, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", id)
                params.put("status", status)
                return params
            }
        }
        VolleySingleton.getInstance(c).addToRequestQueue(stringRequest)
    }

}