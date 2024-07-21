package com.marslogistics.jetpackcomposedeneme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marslogistics.jetpackcomposedeneme.presentation.CryptoDetailScreen
import com.marslogistics.jetpackcomposedeneme.presentation.CryptoListScreen
import com.marslogistics.jetpackcomposedeneme.ui.theme.JetpackComposeDenemeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "crypto_list_screen") {

                composable("crypto_list_screen"){
                    CryptoListScreen(navController = navController)
                }

                composable("crypto_detail_screen/{name}/{price}/{symbol}", arguments = listOf(
                    navArgument("name"){
                        type = NavType.StringType
                    },
                    navArgument("price"){
                        type = NavType.FloatType
                    },
                    navArgument("symbol"){
                        type = NavType.StringType
                    }
                )){

                    val cryptoName = remember {
                        it.arguments?.getString("name")
                    }

                    val cryptoPrice = remember {
                        it.arguments?.getFloat("price")
                    }

                    val cryptoSymbol = remember {
                        it.arguments?.getString("symbol")
                    }

                    CryptoDetailScreen(
                        name = cryptoName ?: "",
                        price = cryptoPrice ?: -1f,
                        symbol = cryptoSymbol ?: "",
                        navController = navController)

                }

            }


        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeDenemeTheme {

    }
}