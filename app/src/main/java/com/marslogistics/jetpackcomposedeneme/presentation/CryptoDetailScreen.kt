package com.marslogistics.jetpackcomposedeneme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CryptoDetailScreen(name : String,
                       price : Float,
                       symbol : String,
                       navController: NavController) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray),
        contentAlignment = Alignment.Center) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Column içindeki bileşenleri yatayda ortala
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ){

            Text(name, modifier = Modifier
                .padding(horizontal = 20.dp),
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)

            Text(price.toString(), modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)

            Text(symbol, modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)

        }

    }

}