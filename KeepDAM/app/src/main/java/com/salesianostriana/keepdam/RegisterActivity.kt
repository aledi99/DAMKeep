package com.salesianostriana.keepdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.salesianostriana.keepdam.data.viewmodel.UserViewModel
import com.salesianostriana.keepdam.di.MyApp
import com.salesianostriana.keepdam.retrofit.response.Register
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    lateinit var etNick : EditText
    lateinit var etFullName : EditText
    lateinit var etPassword : EditText
    lateinit var etPassword2 : EditText
    lateinit var btRegister : Button
    lateinit var btLogin : Button
    lateinit var user : Register

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        etNick = findViewById(R.id.editTextNick)
        etFullName = findViewById(R.id.editTextFullName)
        etPassword = findViewById(R.id.editTextPassword)
        etPassword2 = findViewById(R.id.editTextPassword2)
        btLogin = findViewById(R.id.buttonLogin)
        btRegister = findViewById(R.id.buttonRegister)


        btLogin.setOnClickListener(View.OnClickListener {
            finish()
        })

        btRegister.setOnClickListener(View.OnClickListener {
            user = Register(etNick.text.toString(), etFullName.text.toString(), etPassword.text.toString(), etPassword2.text.toString())
            userViewModel.viewRegister(user).observe(this, Observer {
                if(it != null) {
                    finish()
                } else {
                    Toast.makeText(MyApp.instance, "Ha habido un error a la hora del registro.", Toast.LENGTH_SHORT).show()
                }
            })
        })


    }
}
