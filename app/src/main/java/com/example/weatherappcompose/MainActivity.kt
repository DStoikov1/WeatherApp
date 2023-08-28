package com.example.weatherappcompose

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY ="46ab6777321e481897c122801232408"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                  //  modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Paris",this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String,context: Context) {
    val state = remember{
mutableStateOf("Unknown ")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()/*.background(Color.Red)*/,
        contentAlignment = Alignment.Center
            )

        {
            Text(text = "Temperature in $name = ${state.value}")
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()/*.background(Color.Green)*/
            .padding(60.dp),
        contentAlignment = Alignment.BottomCenter

            ){
         Button(onClick = {
             getResult(name, state,context)
         }) {
             Text(text = "Refresh")
         }
        }
    }
    
}

private fun getResult(city:String,state: MutableState<String>, context: Context){
    val url = "http://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"


val queue =Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        { response ->
            val temp = JSONObject(response)
    state.value = temp.getJSONObject("current").getString("temp_c")

        },
        {
                error ->
       Log.d("MyLog","$error")
        }




    )

    queue.add(stringRequest)

}