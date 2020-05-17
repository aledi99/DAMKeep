package com.salesianostriana.keepdam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.salesianostriana.keepdam.data.viewmodel.UserViewModel
import com.salesianostriana.keepdam.di.MyApp
import com.salesianostriana.keepdam.retrofit.response.Login
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    lateinit var etUsername : EditText
    lateinit var etPassword : EditText
    lateinit var btLogin : Button
    lateinit var btRegister : Button
    lateinit var user : Login

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (applicationContext as MyApp).getAppCompontent().inject(this)

        etUsername = findViewById(R.id.editTextNick)
        etPassword = findViewById(R.id.editTextPassword)
        btLogin = findViewById(R.id.buttonLogin)
        btRegister = findViewById(R.id.buttonRegister)

        btLogin.setOnClickListener(View.OnClickListener {
            user = Login(etUsername.text.toString(), etPassword.text.toString())

            userViewModel.viewLogin(user).observe(this, Observer {
                if(it != null) {
                    val home: Intent = Intent(MyApp.instance, MainActivity::class.java)
                    Toast.makeText(MyApp.instance, "Login correcto", Toast.LENGTH_SHORT).show()
                    startActivity(home)
                }
            })
        })

        btRegister.setOnClickListener(View.OnClickListener {
            val register: Intent = Intent(MyApp.instance, RegisterActivity::class.java)
            startActivity(register)
        })
    }
}
