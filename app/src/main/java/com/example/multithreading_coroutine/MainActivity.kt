package com.example.multithreading_coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreading_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



//          IMPORTANT
// Only the original thread that created a view hierarchy can touch its views.
// So a background thread cannot modify a view that is defined n the main thread .
// Like the background counter could not update the text on the bgCounterTxt , TextView

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // This action is redundant -> could have been executed here
        callMainThread()
        // This is important
        callOtherThread()

        // Counter button -> for using the main thread we can directly code here
        var counter = 1
        binding.apply {
            buttonCount.setOnClickListener {
                mainCountTxt.text = counter++.toString()
            }
        }

        // Background heavy task counter
        binding.buttonDownload.setOnClickListener {
           CoroutineScope(Dispatchers.IO).launch{ startDownload() }
        }

    }

    private suspend fun startDownload() {
        for (i in 1..100000) {
            //Log.v("WOW", "$i + ${Thread.currentThread().name}")

            // Switching the tas from background thread to main thread
            withContext(Dispatchers.Main) {
                binding.bgCountTxt.text = "$i in ${Thread.currentThread().name}" }
        }
    }



    private fun callMainThread() {
        CoroutineScope(Dispatchers.Main).launch{
            binding.textMain.text = "Hello from " + Thread.currentThread().name
        }
    }

    private fun callOtherThread() {
        CoroutineScope(Dispatchers.IO).launch {
            binding.textThread.text = "Hello " + "${Thread.currentThread().name} "
        }

    }


}