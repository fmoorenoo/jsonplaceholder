package org.iesharia.jsonplaceholder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.iesharia.jsonplaceholder.ui.theme.JsonplaceholderTheme
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val retrofit = RetrofitHelper.getInstance()
        setContent {
            JsonplaceholderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var posts by remember { mutableStateOf<List<PostResponse>>(emptyList()) }
                    LaunchedEffect(Unit) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val response: Response<List<PostResponse>> = retrofit.getPosts()
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    posts = response.body() ?: emptyList()
                                    Toast.makeText(this@MainActivity, "Datos cargados", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    PostList(posts = posts)
                }
            }
        }
    }
}

@Composable
fun PostList(posts: List<PostResponse>) {
    LazyColumn {
        items(posts) { post ->
            Text(text = post.title)
        }
    }
}