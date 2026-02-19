package com.example.midterm_labact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.midterm_labact.ui.TodoScreen
import com.example.midterm_labact.vm.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TodoViewModel::class.java]

        setContent {
            TodoScreen(viewModel = vm)
        }
    }
}
