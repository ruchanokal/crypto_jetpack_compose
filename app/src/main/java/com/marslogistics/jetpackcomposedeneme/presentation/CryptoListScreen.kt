package com.marslogistics.jetpackcomposedeneme.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Vertical
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.marslogistics.jetpackcomposedeneme.data.model.Data
import com.marslogistics.jetpackcomposedeneme.ui.theme.JetpackComposeDenemeTheme

private val TAG = "CryptoListScreen"

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoViewModel = hiltViewModel()
    ) {


    Surface (modifier = Modifier.fillMaxSize(),
            color = Color.LightGray) {
        
        Column {

            Spacer(modifier = Modifier.padding(vertical = 15.dp))

            Text("Crypto Crazy App", modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 15.dp, 5.dp, 5.dp, 5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            SearchBar(hint = "Bir crypto para arayın...", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ){

                Log.i(TAG,"search string: $it")
                viewModel.search(it)
            }

            Spacer(modifier = Modifier.padding(top = 5.dp))

            CryptoListView(navController = navController)
        }

    }
}

@Composable
fun SearchBar(hint : String = "",
              modifier: Modifier,
              onSearch: (String) -> Unit = {}) {

    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier.height(45.dp).fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp))
        ,contentAlignment = Alignment.Center
    ) {

        BasicTextField(value = text,
            onValueChange = {
            text = it
            onSearch(it)
        }, maxLines = 1,
           singleLine = true,
           textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 15.dp)
               .onFocusChanged {
                   isHintDisplayed = !it.isFocused && text.isEmpty()
               }
        )
        
        if (isHintDisplayed) {
            Text(text = hint,
                color =  Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.CenterStart))

        }
        
    }

    
}

@Composable
fun CryptoListView(
     navController : NavController,
     viewModel: CryptoViewModel = hiltViewModel()
) {

    val cryptoList by remember { viewModel.cryptoList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    //val state by remember { viewModel.state }
    //val cryptoList = state.cryptoList
    //val errorMessage = state.errorMessage
    //val loading = state.loading


    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptoList){ crypto ->
            CryptoRow(navController = navController,crypto = crypto)
        }
    }

    Box ( contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
    ) {

        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        if (errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.getCryptoDatas()
            }
        }

    }



}

@Composable
fun CryptoRow(navController: NavController, crypto : Data ) {

    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 7.dp)
        .background(color = Color.LightGray)
        .clickable {
            navController.navigate("crypto_detail_screen/${crypto.name}/${crypto.quote.USD.price}/${crypto.symbol}")
        }) {

        Text(text = crypto.name, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontStyle = FontStyle.Normal)


        Text(text = crypto.quote.USD.price.toString(), modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp),
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal)

    }


}

@Composable
fun RetryView(error : String, onRetry: () -> Unit) {

    Column {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeDenemeTheme {

    }
}