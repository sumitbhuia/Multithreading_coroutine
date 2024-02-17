package com.example.multithreading_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.multithreading_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        callMainThread()
         callOtherThread()
    }

    private fun callMainThread() {
        CoroutineScope(Dispatchers.Main).launch{
            binding.textMain.text = "Hello from " + "${ Thread.currentThread().name }"
        }
    }

    private fun callOtherThread() {
        CoroutineScope(Dispatchers.IO).launch {
            binding.textThread.text = "Hello "+ "${Thread.currentThread().name} "
        }

    }


}