package com.salesianostriana.keepdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.salesianostriana.keepdam.data.viewmodel.KeepDAMViewModel
import com.salesianostriana.keepdam.di.MyApp
import javax.inject.Inject

class ConfirmActivity : AppCompatActivity() {
    lateinit var btYes : Button
    lateinit var btNo : Button
    var id : String = ""

    @Inject
    lateinit var keepDAMViewModel: KeepDAMViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        btYes = findViewById(R.id.buttonYes)
        btNo = findViewById(R.id.buttonNo)

        id = intent.getStringExtra("id")

        btYes.setOnClickListener(View.OnClickListener {
            keepDAMViewModel.viewDeleteNota(id)
            finish()
        })

        btNo.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}
