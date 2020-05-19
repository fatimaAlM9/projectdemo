package com.example.project

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.project.model.GAME_SAME_ANIMAL_MODEL
import com.mapzen.speakerbox.Speakerbox
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_bingo_page.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList


class bingoPage : AppCompatActivity() {
//            val context: Context = bingoPage.


    var lion = 1;
    var  clicked = 0;
    val context : Context = this;

//    lateinit var sd : android.app.AlertDialog;
    lateinit var dialog : Dialog;





    var game_start_player_1 = "0";
    var First_time_insert_id_in_request_table = "0";

    var play_game_image_chker_limit = 0;

    val url = important_value.ip + "get_randam_six_animal.php";
    val url_request = important_value.ip + "send_game_request.php";
    val url_same_animal = important_value.ip + "get_reqested_animal_from_same_aniaml.php";
    val url_request_chek_game_start = important_value.ip + "get_my_game_start.php";
    val url_file = important_value.ip +"filess/";

    lateinit var list : ArrayList<GAME_SAME_ANIMAL_MODEL>

    lateinit var sessionManagement: SessionManagement;

    lateinit var builder : AlertDialog.Builder;

    lateinit var speakerbox : Speakerbox;
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bingo_page)


            sessionManagement = SessionManagement(this);
//            builder = AlertDialog.Builder(context)
            dialog = Dialog(context)

            list = ArrayList()
//            get_animal()


            speakerbox = Speakerbox(applicationContext as Application?)


            bingo_ib1.setOnClickListener{

                play_question_and_answer(0,bingo_ib1)
            }


        bingo_ib2.setOnClickListener{
            play_question_and_answer(1,bingo_ib2)


        }

        bingo_ib3.setOnClickListener{
            play_question_and_answer(2,bingo_ib3)

        }


            bingo_ib4.setOnClickListener{
                play_question_and_answer(3,bingo_ib4)

            }


            bingo_ib5.setOnClickListener{
                play_question_and_answer(4,bingo_ib5)

            }

            bingo_ib6.setOnClickListener{
                play_question_and_answer(5,bingo_ib6)

            }

            send_request(sessionManagement.getid())


    }




    fun set_same_animal(){

//        val RV: RecyclerView = findViewById(R.id.RV_ADMIN_ANIMAL)
//        RV.setLayoutManager(LinearLayoutManager(this))
//        val rv = RV_ANIMALS(this,list)
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
//                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("return").equals("1"))
                    {
//                        MY_ALERT.SET_MY_ALERT(context,"note","animal are ready ","OK");
                        get_same_animal();

                    }

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
                params.put("request_id", First_time_insert_id_in_request_table)
                params.put("second_player", sessionManagement.getid())
//                params.put("GETTER", "")
//                params.put("password", userPass)
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }




    fun get_same_animal(){

        val stringRequest = object : StringRequest(
            Request.Method.POST, url_same_animal,
            Response.Listener<String> { response ->
//                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                try {
                    val obj = JSONArray(response)
//                    if (obj.getString("return").equals("1"))
//                    {
//                        MY_ALERT.SET_MY_ALERT(context,"note","animal are ready ","OK");
//
//                    }
                    for (i in 0 until obj.length())
                    {
                        var obj_json = obj.getJSONObject(i)

                        var am = GAME_SAME_ANIMAL_MODEL(
                            obj_json.getString("0"),
                            obj_json.getString("1"),
                            obj_json.getString("3"),
                            obj_json.getString("4"),
                            "",
                            "",
                            obj_json.getString("9"),
                            obj_json.getString("6"),
                            obj_json.getString("7"),
                            obj_json.getString("5"),
                            obj_json.getString("8"),
                            i

                        )

                        list.add(am)
//                        Log.e("NUMBER", number[i].toString())
                    }
//                    RV.setAdapter(rv)

                    val rand = Random()

// Obtain a number between [0 - 49].

// Obtain a number between [0 - 49].
                    val n = rand.nextInt(4)

                    if (n==0)
                    {
                        Picasso.get().load(url_file+list[4].img).resize(200,200).into(bingo_ib1)
                        list[4].assign_game_postion = 0

                        Picasso.get().load(url_file+list[1].img).resize(200,200).into(bingo_ib2)
                        list[1].assign_game_postion = 1

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib3)
                        list[5].assign_game_postion = 2

                        Picasso.get().load(url_file+list[0].img).resize(200,200).into(bingo_ib4)
                        list[0].assign_game_postion = 3

                        Picasso.get().load(url_file+list[2].img).resize(200,200).into(bingo_ib5)
                        list[2].assign_game_postion = 4

                        Picasso.get().load(url_file+list[3].img).resize(200,200).into(bingo_ib6)
                        list[3].assign_game_postion = 5

                    } else if (n==1)
                    {
                        Picasso.get().load(url_file+list[3].img).resize(200,200).into(bingo_ib1)
                        list[3].assign_game_postion = 0

                        Picasso.get().load(url_file+list[0].img).resize(200,200).into(bingo_ib2)
                        list[0].assign_game_postion = 1

                        Picasso.get().load(url_file+list[4].img).resize(200,200).into(bingo_ib3)
                        list[4].assign_game_postion = 2

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib4)
                        list[5].assign_game_postion = 3

                        Picasso.get().load(url_file+list[1].img).resize(200,200).into(bingo_ib5)
                        list[1].assign_game_postion = 4

                        Picasso.get().load(url_file+list[2].img).resize(200,200).into(bingo_ib6)
                        list[2].assign_game_postion = 5

                    } else if (n == 2)
                    {
                        Picasso.get().load(url_file+list[2].img).resize(200,200).into(bingo_ib1)
                        list[2].assign_game_postion = 0

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib2)
                        list[5].assign_game_postion = 1

                        Picasso.get().load(url_file+list[3].img).resize(200,200).into(bingo_ib3)
                        list[3].assign_game_postion = 2

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib4)
                        list[5].assign_game_postion = 3

                        Picasso.get().load(url_file+list[0].img).resize(200,200).into(bingo_ib5)
                        list[0].assign_game_postion = 4

                        Picasso.get().load(url_file+list[1].img).resize(200,200).into(bingo_ib6)
                        list[1].assign_game_postion = 5
                    } else if (n==3)
                    {
                        Picasso.get().load(url_file+list[1].img).resize(200,200).into(bingo_ib1)
                        list[1].assign_game_postion = 0

                        Picasso.get().load(url_file+list[4].img).resize(200,200).into(bingo_ib2)
                        list[4].assign_game_postion = 1

                        Picasso.get().load(url_file+list[2].img).resize(200,200).into(bingo_ib3)
                        list[2].assign_game_postion = 2

                        Picasso.get().load(url_file+list[4].img).resize(200,200).into(bingo_ib4)
                        list[4].assign_game_postion = 3

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib5)
                        list[5].assign_game_postion = 4

                        Picasso.get().load(url_file+list[0].img).resize(200,200).into(bingo_ib6)
                        list[0].assign_game_postion = 5
                    }
                    else if (n==4)
                    {
                        Picasso.get().load(url_file+list[0].img).resize(200,200).into(bingo_ib1)
                        list[0].assign_game_postion = 0

                        Picasso.get().load(url_file+list[3].img).resize(200,200).into(bingo_ib2)
                        list[3].assign_game_postion = 1

                        Picasso.get().load(url_file+list[1].img).resize(200,200).into(bingo_ib3)
                        list[1].assign_game_postion = 2

                        Picasso.get().load(url_file+list[3].img).resize(200,200).into(bingo_ib4)
                        list[3].assign_game_postion = 3

                        Picasso.get().load(url_file+list[4].img).resize(200,200).into(bingo_ib5)
                        list[4].assign_game_postion = 4

                        Picasso.get().load(url_file+list[5].img).resize(200,200).into(bingo_ib6)
                        list[5].assign_game_postion = 5
                    }

                    main_play()
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
                params.put("GETTER", "")
                params.put("request_id", First_time_insert_id_in_request_table)
//                params.put("second_player", sessionManagement.getid())
//                params.put("GETTER", "")
//                params.put("password", userPass)
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }




    fun send_request(user_id: String){
        val stringRequest = object : StringRequest(
            Request.Method.POST, url_request,
            Response.Listener<String> { response ->

                try {
//                    find a player to play



//                    Toast.makeText(applicationContext, ""+last_value, Toast.LENGTH_LONG).show()

                    if (response.contains("id"))
                    {
                        val obj1 = JSONArray(response)

//                        Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                        for (i in 0 until obj1.length())
                        {
                            var obj = obj1.getJSONObject(i);
                            First_time_insert_id_in_request_table = obj.getString("0");
                            var user_id_ = obj.getString("1");
                            var date = obj.getString("2");
                            var time = obj.getString("3");
                            var status = obj.getString("4");
                            val builder1 =
                                AlertDialog.Builder(context)
                            builder1.setMessage("Player is now online ")
                            builder1.setTitle("NOTE")
                            builder1.setCancelable(true)
                            builder1.setPositiveButton(
                                "START"
                            )
                            {
                                    dialog, id -> dialog.cancel()
                                    set_same_animal()


                            }
                            builder1.setNegativeButton(
                                "STOP"
                            )
                            { dialog, id -> dialog.cancel() }
                            val alert11 = builder1.create()
                            alert11.show()
                        }




                    }
//                    not find a player for client
                    else if (response.contains("return"))
                    {
                        val obj = JSONObject(response)
                        var my_id = obj.getInt("return")
                        if (my_id>0)
                        {

                            First_time_insert_id_in_request_table =""+my_id;
//                            sd = MY_ALERT.progress_loading_show(context)!!;
                            showDialog()

                            main();
                        }
                        else
                        {

                        }

                    }

//                    startActivity(Intent(this, gameSelection::class.java))
//                    finish()
//                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, e.toString()+"please try again"+ response, Toast.LENGTH_LONG).show()
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
                params.put("user_id", user_id)
                params.put("GETTER", "sd")
//                params.put("password", userPass)
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }


    fun main() {
        val mylamda = Thread({

            for (x in 1..10){


                if(game_start_player_1.equals("0"))
                {
                    Thread.sleep((x*3000).toLong())
                    request_cheker_to_start_game(First_time_insert_id_in_request_table)
                }
                else
                {
//                    for player one inforamiton is here when a return is greater than 0
                    if (dialog.isShowing)
                    {
                        dialog.dismiss()
                        get_same_animal();

                    }
//                    MY_ALERT.progress_loading_close(sd)
                }

//                println("$x")
            }
        })
        startThread(mylamda)

    }

    fun startThread(mylamda: Thread) {
        mylamda.start()
    }



    fun request_cheker_to_start_game(id: String){
        val stringRequest = object : StringRequest(
            Request.Method.POST, url_request_chek_game_start,
            Response.Listener<String> { response ->
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_LONG).show()

                try {

                        val obj = JSONArray(response)

                    for (i in 0 until obj.length())
                    {

                        var obj_json = obj.getJSONObject(i)
                        game_start_player_1  =     obj_json.getString("0")
//                        if (dialog.isShowing)
//                        {
//                            dialog.dismiss()
//                        }
//                        MY_ALERT.progress_loading_close(sd);

                    }

                }
                catch (e: JSONException)
                {
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
                params.put("id", id)
                params.put("GETTER", "as")
//                params.put("password", userPass)
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }

    private fun showDialog() {
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.progres_dialog)

        dialog .show()

    }

    private fun get_randam(n: Int,index_value : Int) : Int
    {
        var n_plus = n+index_value;
        var modulis = n_plus%6;
        list[index_value].assign_game_postion = modulis
        Logger.getLogger(bingoPage::class.java.name).warning("MOON==="+modulis)
        return modulis
    }

    private fun play_game(image_play_index_for_image : Int)
    {

        if (image_play_index_for_image<6)
        {
            var  question_voice = list[image_play_index_for_image].animal_question
            speakerbox.play(question_voice)
//                    TODO FIRST TIME VOICE PLAY HERE
            bingo_tv_speaker.setText(question_voice)

        }



    }

    private fun play_question_and_answer(image_current_postion : Int , Img : ImageView)
    {
//        answer of clieck imge

        if (play_game_image_chker_limit <6)
        {
            if (image_current_postion.equals(list[play_game_image_chker_limit].assign_game_postion))
            {
//            here is the correct answer of game

                Img.setBackgroundColor(Color.GREEN);
//            Answer is correct chagen color
                var answer = list[play_game_image_chker_limit].animal_answer;
                bingo_tv_speaker.setText(answer)
                speakerbox.play("Answer : "+answer)
//            voice the answer reply
                play_game_image_chker_limit++;
                main_play()

                if (play_game_image_chker_limit==6)
                {
                    MY_ALERT.SET_MY_ALERT_TO_CLOSE(context,"NOTE","GAME COMPELTE","Finish")

                }


            }
            else
            {

                main_play_img(Img)
            }

        }
        else
        {
            MY_ALERT.SET_MY_ALERT(context,"NOTE","GAME COMPELTE","OK")
        }

    }

    fun main_play() {
        val mylamda = Thread({

            Thread.sleep((3000))

            play_game(play_game_image_chker_limit)

        })
        startThread_play(mylamda)

    }

    fun main_play_img(Img: ImageView) {
        Img.setBackgroundColor(Color.RED);

        val mylamda = Thread({


            Thread.sleep((3000))
            Img.setBackgroundColor(Color.BLUE);
            play_game(play_game_image_chker_limit)

        })
        startThread_play(mylamda)

    }

    fun startThread_play(mylamda: Thread) {
        mylamda.start()
    }
}
