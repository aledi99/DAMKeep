package com.salesianostriana.keepdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
