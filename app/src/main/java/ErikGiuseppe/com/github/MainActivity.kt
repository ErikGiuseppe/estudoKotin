 package ErikGiuseppe.com.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.EditText
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ErikGiuseppe.com.github.ui.theme.ListadecomprasTheme
import androidx.recyclerview.widget.RecyclerView

 class MainActivity : AppCompatActivity() {
     private lateinit var viewModel: ItemsViewModel
     override fun onCreate(savedInstanceState: Bundle?) {

         super.onCreate(savedInstanceState)

         setContentView(R.layout.activity_main)

         val toolbar: Toolbar = findViewById(R.id.toolbar)
         setSupportActionBar(toolbar)
         supportActionBar?.title = "Lista de Compras"
         val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         val itemsAdapter = ItemsAdapter { item ->
             viewModel.removeItem(item)
         }
         recyclerView.adapter = itemsAdapter

         val button = findViewById<Button>(R.id.button)
         val editText = findViewById<EditText>(R.id.editText)

         button.setOnClickListener {
             if (editText.text.isEmpty()) {
                 editText.error = "Preencha um valor"
                 return@setOnClickListener
             }

             viewModel.addItem(editText.text.toString())
             editText.text.clear()

         }

         /**
          * Observa as alterações na lista de itens na ViewModel.
          * Quando a lista de itens é alterada, atualiza o ItemsAdapter com a nova lista.
          */
         val viewModelFactory = ItemsViewModelFactory(application)
         viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

         viewModel.itemsLiveData.observe(this) { items ->
             itemsAdapter.updateItems(items)
         }

     }
 }

