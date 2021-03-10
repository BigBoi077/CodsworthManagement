package cegepst.example.codsworthmanagement.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.codsworthmanagement.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    fun onClick(view: View) {
        val vaultNumber = findViewById<EditText>(R.id.editTextVaultNumber)
        if (vaultNumber != null) {
            if (isValidVaultNumber(vaultNumber.text.toString())) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("vaultNumber", vaultNumber.text.toString())
                startActivity(intent)
                return
            }
        }
        Toast.makeText(this, R.string.alertEmptyField, Toast.LENGTH_SHORT).show()
    }

    private fun isValidVaultNumber(vaultNumber: String): Boolean {
        val vault: Int = try {
            vaultNumber.toInt()
        } catch (e: Exception) {
            0
        }
        return isBetween(1, 999, vault)
    }

    private fun isBetween(min: Int, max: Int, number: Int): Boolean {
        return number in min..max
    }
}