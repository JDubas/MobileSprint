package com.example.hall9000.ui.registrar

import android.widget.Toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hall9000.R
import com.example.hall9000.databinding.FragmentRegistrarBinding
import com.example.hall9000.network.LoginRequest
import com.example.hall9000.network.RegistrarRequest
import com.example.hall9000.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarFragment : Fragment() {
    private var _binding: FragmentRegistrarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarBinding.inflate(inflater, container, false)

        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    registerUser(name, email, password)
                } else {
                    Toast.makeText(context, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun registerUser(name: String, email: String, password: String) {
        // Implementar lógica para registrar o usuário
        Toast.makeText(context, "Cadastrando espere...", Toast.LENGTH_SHORT).show()

        val registrarRequest = RegistrarRequest(name, password, email)
        RetrofitClient.apiService.registrarUser(registrarRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Cadastrado", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.navigation_login)
                } else {
                    Toast.makeText(context, "Ocorreu um erro: ${response.message()}}", Toast.LENGTH_SHORT).show()

                }


            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}