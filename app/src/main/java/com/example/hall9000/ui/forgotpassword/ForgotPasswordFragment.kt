package com.example.hall9000.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hall9000.R
import com.example.hall9000.network.LoginResponse
import com.example.hall9000.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var buscarSenhaButton: Button
    private lateinit var senhaTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        // Inicializar os elementos de interface
        emailEditText = view.findViewById(R.id.etEmail)
        buscarSenhaButton = view.findViewById(R.id.btnBuscarSenha)
        senhaTextView = view.findViewById(R.id.tvSenha)

        // Configurar o botão para buscar a senha
        buscarSenhaButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                buscarSenha(email)
            } else {
                Toast.makeText(requireContext(), "Por favor, digite um e-mail", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun buscarSenha(email: String) {
        // Chamada à API para buscar os logins
        val call = RetrofitClient.apiService.getLogins()

        call.enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(call: Call<List<LoginResponse>>, response: Response<List<LoginResponse>>) {
                if (response.isSuccessful) {
                    val logins = response.body() ?: return

                    // Filtrar o login com base no email fornecido
                    val login = logins.find { it.email == email }

                    if (login != null) {
                        // Exibir a senha correspondente
                        senhaTextView.text = "Sua senha é: ${login.senha}"
                    } else {
                        Toast.makeText(requireContext(), "E-mail não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro ao buscar a senha", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
