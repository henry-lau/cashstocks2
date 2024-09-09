package us.myapplication.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import us.myapplication.data.ApiService
import us.myapplication.data.DataViewModel
import us.myapplication.data.MainRepository
import us.myapplication.data.MyViewModelFactory
import us.myapplication.theme.HenryTheme
import us.myapplication.navigation.NavGraph

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitService = ApiService.getService()
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(DataViewModel::class.java)

        setContent {
            HenryTheme {
                val navController = rememberNavController()
                NavGraph(navController, viewModel)
            }
        }
    }
}
