package com.salesianostriana.keepdam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import com.salesianostriana.keepdam.data.viewmodel.KeepDAMViewModel
import com.salesianostriana.keepdam.di.MyApp
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {
    lateinit var tvTitle : TextView
    lateinit var tvContent : TextView
    lateinit var tvNick : TextView
    lateinit var tvDate : TextView
    lateinit var ibEdit : ImageButton
    lateinit var id : String

    @Inject
    lateinit var keepDAMViewModel: KeepDAMViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        id = intent.getStringExtra("id")

        tvTitle = findViewById(R.id.textViewTitle)
        tvContent = findViewById(R.id.textViewContent)
        tvDate = findViewById(R.id.textViewDate)
        tvNick = findViewById(R.id.textViewNick)
        ibEdit = findViewById(R.id.imageButtonEdit)

        ibEdit.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(this, NewNotaActivity::class.java).apply {
                putExtra("id", id)
                putExtra("editar", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        })

        keepDAMViewModel.viewOneNota(id).observe(this, Observer {
            if (it != null) {
                tvTitle.text = it.titulo
                tvContent.text = it.contenido
                if(it.ultimaEdicion == null) {
                    tvDate.text = "Indeterminado"
                } else {
                    tvDate.text = it.ultimaEdicion
                }
                tvNick.text = it.autor.nick
            }
        })
    }
}
