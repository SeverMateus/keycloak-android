package mz.co.smatech.keycloak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submit = findViewById<View>(R.id.submit) as MaterialButton

        submit.setOnClickListener{
            GlobalScope.launch {
                try {
                    val response = RemoteSession.login().getAccessToken(
                        "CLIENT_ID",
                        "password",
                        "CLIENT_SECRET",
                        "openid",
                        "test",
                        "test"
                    )

                    if (response.isSuccessful) {
                        if (response.body() != null)
                            println(response.body()?.accessToken)
                        startActivity(
                            Intent(
                                this@MainActivity,
                                MainActivity2::class.java
                            )
                        )
                    } else {
                        Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT).show()
                    }
                } catch (ex: Exception) {
                    println("Exception: "+ex.message)
                }
            }
        }
    }
}