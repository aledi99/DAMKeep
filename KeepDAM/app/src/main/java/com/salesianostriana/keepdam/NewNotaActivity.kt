package com.salesianostriana.keepdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.salesianostriana.keepdam.data.viewmodel.KeepDAMViewModel
import com.salesianostriana.keepdam.di.MyApp
import com.salesianostriana.keepdam.retrofit.response.NuevaNota
import javax.inject.Inject

class NewNotaActivity : AppCompatActivity() {
    lateinit var etTitulo : EditText
    lateinit var etContent : EditText
    lateinit var btConfirm : Button
    var id : String = ""
    var editar : Boolean = false
    lateinit var nuevaNota: NuevaNota

    @Inject
    lateinit var keepDAMViewModel: KeepDAMViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_nota)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        etTitulo = findViewById(R.id.editTextTitulo)
        etContent = findViewById(R.id.editTextContent)
        btConfirm = findViewById(R.id.buttonConfirm)

        editar = intent.extras!!.getBoolean("editar")

        if(editar) {
            id = intent.getStringExtra("id")
            keepDAMViewModel.viewOneNota(id).observe(this, Observer {
                etTitulo.setText(it.titulo)
                etContent.setText(it.contenido)

            })
            btConfirm.setOnClickListener(View.OnClickListener {
                nuevaNota = NuevaNota(etTitulo.text.toString(), etContent.text.toString())
                keepDAMViewModel.viewEditNota(id, nuevaNota).observe(this, Observer {
                    Toast.makeText(MyApp.instance, "La nota con el id $id ha sido modificada correctamente.", Toast.LENGTH_SHORT).show()
                })
            })
        } else {
            btConfirm.setOnClickListener(View.OnClickListener {
                nuevaNota = NuevaNota(etTitulo.text.toString(), etContent.text.toString())
                keepDAMViewModel.viewNewNota(nuevaNota).observe(this, Observer {
                    Toast.makeText(MyApp.instance, "La nota con el id ${it.id} ha sido creado correctamente.", Toast.LENGTH_SHORT).show()
                })
            })

        }
    }
}
