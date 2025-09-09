package com.example.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactDetails(contact = Contact(
                "Drobitko",
                "Ivan",
                "Andreevich",
                R.drawable.image,
                true,
                "+7 982 207 56 26",
                "Тверская улица, д. 1, кв. 10, Москва",
                "drobitkoivan@yandex.ru"
            ))
        }
    }
}
